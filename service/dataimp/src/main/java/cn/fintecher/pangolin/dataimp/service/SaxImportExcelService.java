package cn.fintecher.pangolin.dataimp.service;

import cn.fintecher.pangolin.dataimp.annotation.ExcelAnno;
import cn.fintecher.pangolin.dataimp.entity.DataImportRecord;
import cn.fintecher.pangolin.dataimp.entity.DataInfoExcel;
import cn.fintecher.pangolin.dataimp.entity.TemplateDataModel;
import cn.fintecher.pangolin.dataimp.entity.TemplateExcelInfo;
import cn.fintecher.pangolin.dataimp.repository.DataImportRecordRepository;
import cn.fintecher.pangolin.dataimp.repository.TemplateDataModelRepository;
import cn.fintecher.pangolin.entity.Company;
import cn.fintecher.pangolin.entity.ReminderType;
import cn.fintecher.pangolin.entity.User;
import cn.fintecher.pangolin.entity.file.UploadFile;
import cn.fintecher.pangolin.entity.message.SendReminderMessage;
import cn.fintecher.pangolin.entity.util.Constants;
import cn.fintecher.pangolin.util.ZWDateUtil;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StopWatch;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.CompletableFuture;

/**
 * @Author:peishouwen
 * @Desc: SAX方式导入Excel
 * @Date:Create in 17:21 2018/6/15
 */
@Service
public class SaxImportExcelService {

    Logger logger= LoggerFactory.getLogger(SaxImportExcelService.class);
    @Autowired
    RestTemplate restTemplate;

    @Autowired
    SaxParseExcelService saxParseExcelService;

    @Autowired
    MongoSequenceService mongoSequenceService;

    @Autowired
    DataImportRecordRepository dataImportRecordRepository;

    @Autowired
    ParseCellToObjTask parseCellToObjTask;

    @Autowired
    DataInfoExcelService dataInfoExcelService;

    @Autowired
    TemplateDataModelRepository templateDataModelRepository;

    @Async
    public void importExcelData(DataImportRecord dataImportRecord, User user) throws Exception {
        StopWatch watch = new StopWatch();
        watch.start();
        int startRow = 0;
        int startCol = 0;
        //获取上传的文件
        ResponseEntity<UploadFile> fileResponseEntity = null;
        InputStream in=null;
        List<Map<String,String>> dataListMap=new ArrayList<>();
        List<String> resultDataList=new ArrayList<>();
        try {
            ResponseEntity<Company> companyentity = restTemplate.getForEntity(Constants.COMPANY_URL.concat(user.getCompanyCode()), Company.class);
            Company company = companyentity.getBody();

            //获取模板数据
            TemplateDataModel templateDataModel = null;
            //通过模板配置解析Excel数据
            List<TemplateExcelInfo> templateExcelInfoList = null;
            if (StringUtils.isNotBlank(dataImportRecord.getTemplateId())) {
                templateDataModel = templateDataModelRepository.findOne(dataImportRecord.getTemplateId());
                if (Objects.nonNull(templateDataModel)) {
                    startRow = Integer.parseInt(templateDataModel.getDataRowNum())-1;
                    startCol = Integer.parseInt(templateDataModel.getDataColNum())-1;
                    templateExcelInfoList = templateDataModel.getTemplateExcelInfoList();
                } else {
                    throw new Exception("导入模板配置信息缺失");
                }
            }

            //批次号
            String batchNumber = mongoSequenceService.getNextSeq(Constants.ORDER_SEQ, user.getCompanyCode(), Constants.ORDER_SEQ_LENGTH);
            batchNumber = company.getSequence().concat(batchNumber);
            dataImportRecord.setBatchNumber(batchNumber);
            dataImportRecord.setOperator(user.getId());
            dataImportRecord.setOperatorName(user.getRealName());
            dataImportRecord.setOperatorTime(ZWDateUtil.getNowDateTime());
            dataImportRecord.setCompanyCode(user.getCompanyCode());
            dataImportRecord.setCompanySequence(company.getSequence());
            dataImportRecordRepository.save(dataImportRecord);

            fileResponseEntity = restTemplate.getForEntity(Constants.FILEID_SERVICE_URL.concat("uploadFile/getUploadFile/").concat(dataImportRecord.getFileId()), UploadFile.class);
            UploadFile file = fileResponseEntity.getBody();
            if(Constants.EXCEL_TYPE_XLS.equals(file.getType())){
                throw new Exception("Excel版本过低请转化为高版本xlsx");
            }
            //判断文件类型是否为Excel
            if (!Constants.EXCEL_TYPE_XLSX.equals(file.getType())) {
                throw new Exception("数据文件为非Excel数据");
            }
            in= fileStream(file);
            //解析返回所有的数据
            logger.info("Excel解析开始.......");
            saxParseExcelService.parseExcel(in,new SaxSheetContentsHandler(dataListMap,startRow,startCol));
            logger.info("Excel解析完成.......");
            //将返回的数据转为对象
            logger.info("组装数据对象开始.......");
            Map<String,String> titleMap=valueToKey(dataListMap,templateExcelInfoList,DataInfoExcel.class);
            dataListMap.remove(0);
            //分页解析Excel中的数据对象
            int pageSize = 5000;
            int pageCount=0;
            if((dataListMap.size()%pageSize)>0){
                pageCount=dataListMap.size()/pageSize+1;
            }else {
                pageCount=dataListMap.size()/pageSize;
            }
            // 创建任务集合
            List<CompletableFuture<List<String>>> taskList = new ArrayList<>();
            for(int pageNo=1;pageNo<=pageCount;pageNo++){
                int startIndex = pageSize * (pageNo - 1);
                int endIndex = pageSize * pageNo;
                if (endIndex > dataListMap.size()) {
                    endIndex = dataListMap.size();
                }
                List<Map<String,String>> perDataListMap=new ArrayList<>();
                perDataListMap.addAll( dataListMap.subList(startIndex,endIndex));
                CompletableFuture<List<String>> subTask =parseCellToObjTask.parseCell(perDataListMap,DataInfoExcel.class,titleMap,pageNo-1,pageSize,dataImportRecord);
                taskList.add(subTask);
            }
            //收集子线程返回结果
            for(CompletableFuture<List<String>> resultData:taskList){
                try {
                    resultDataList.addAll(resultData.get());
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                    throw e;
                }
            }
            logger.info("组装数据对象完成.......");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new Exception(Objects.isNull(e.getMessage()) ? "导入失败请联系管理员" : e.getMessage());
        }finally {
            if(Objects.nonNull(in)){
                in.close();
            }
            dataListMap.clear();
            if(!resultDataList.isEmpty()){
                //根据批次号删除本次导入记录
                dataInfoExcelService.deleteCasesByBatchNum(dataImportRecord.getBatchNumber(),user);

                //生成错误信息txt
                String filePath = FileUtils.getTempDirectoryPath().concat(File.separator).concat(
                        ZWDateUtil.fomratterDate(ZWDateUtil.getNowDateTime(),"yyyyMMddHHmmss")).concat("数据导入错误信息.txt");
                File file=new File(filePath);
                if(file.exists()){
                    file.deleteOnExit();
                }else {
                    boolean newFile = file.createNewFile();
                    if (!newFile) {
                        logger.error("生成错误信息文件失败");
                    }
                }
                try (BufferedWriter out =new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filePath), "UTF-8"))) {
                    for(String str:resultDataList){
                        out.write(str);
                        out.newLine();
                    }
                    out.flush();
                } catch (IOException e) {
                    logger.error(e.getMessage(), e);
                }
                //文件上传
                FileSystemResource resource = new FileSystemResource(filePath);
                MultiValueMap<String, Object> param = new LinkedMultiValueMap<>();
                param.add("file", resource);
                String url = restTemplate.postForEntity("http://file-service/api/uploadFile/addUploadFileUrl", param, String.class).getBody();
                reminderMessageFail(user.getId(),null,url);
                if(file.exists()){
                    file.deleteOnExit();
                }
                resultDataList.clear();
            }else{
                reminderMessage(user.getId());
            }
        }
        watch.stop();
        logger.info("导入完成耗时:{}",watch.getTotalTimeMillis());
    }

    /**
     * 将MAP中的key和value互换
     * @param dataListMap
     * @return
     */
    private  Map<String,String> valueToKey(List<Map<String, String>> dataListMap,List<TemplateExcelInfo> templateExcelInfoList,Class<?> dataClass) {
        Map<String,String> titleHead=new HashMap<>();
        if(Objects.nonNull(templateExcelInfoList)){
            //模板格式
            for (TemplateExcelInfo templateExcelInfo : templateExcelInfoList) {
                if (StringUtils.isNotBlank(templateExcelInfo.getRelateName())) {
                    //获取类中所有的字段
                    Field[] fields = dataClass.getDeclaredFields();
                    for (Field field : fields) {
                        //实体中的属性名称
                        String proName = field.getName();
                        //匹配到实体中相应的字段
                        if (proName.equals(templateExcelInfo.getRelateName())) {
                            ExcelAnno f = field.getAnnotation(ExcelAnno.class);
                            //实体中注解的属性名称
                            String cellName = f.cellName();
                            titleHead.put(cellName,templateExcelInfo.getColNum());
                            break;
                        }
                    }
                }
            }
        }else{
            //非模板
            Map<String,String> excelHead=dataListMap.get(0);
            for(Map.Entry<String,String> entry:excelHead.entrySet()){
                titleHead.put(entry.getValue().trim(),entry.getKey().trim());
            }
        }
        return titleHead;
    }

    /**
     * 获取文件流对象
     * @param file
     * @return
     */
    private InputStream fileStream(UploadFile file) {
            HttpHeaders headers = new HttpHeaders();
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<byte[]> response = restTemplate.exchange(file.getLocalUrl(), HttpMethod.GET, new HttpEntity<byte[]>(headers), byte[].class);
            byte[] result = response.getBody();
            return new ByteArrayInputStream(result);
    }
    /**
     * 案件导入消息成功提醒
     */
    public void reminderMessage(String userId) {
        //消息提醒
        try {
            SendReminderMessage sendReminderMessage = new SendReminderMessage();
            sendReminderMessage.setUserId(userId);
            sendReminderMessage.setTitle("案件导入成功");
            sendReminderMessage.setContent("案件导入成功提醒");
            sendReminderMessage.setType(ReminderType.CASE_IMPORT);
            sendReminder(sendReminderMessage);
        } catch (Exception e) {
            logger.error("案件导入消息提醒错误...");
        }
    }

    public void sendReminder(SendReminderMessage sendReminderMessage) {
        restTemplate.postForLocation("http://reminder-service/api/reminderMessages/receiveMessage", sendReminderMessage);
    }

    /**
     * 案件导入消息失败提醒
     */
    public void reminderMessageFail(String userId,List forceErrorList,String fileUrl) {
        //消息提醒
        try {
            SendReminderMessage sendReminderMessage = new SendReminderMessage();
            sendReminderMessage.setUserId(userId);
            sendReminderMessage.setTitle("案件导入失败");
            sendReminderMessage.setContent("案件导入失败提醒,错误信息如下：".concat(fileUrl));
            sendReminderMessage.setType(ReminderType.CASE_IMPORT);
            sendReminderMessage.setForceErrorList(forceErrorList);
            sendReminder(sendReminderMessage);
        } catch (Exception e) {
            logger.error("案件导入提醒错误...");
        }
    }
}
