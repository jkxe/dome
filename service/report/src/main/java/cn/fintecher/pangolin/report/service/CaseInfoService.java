package cn.fintecher.pangolin.report.service;

import cn.fintecher.pangolin.entity.Personal;
import cn.fintecher.pangolin.entity.User;
import cn.fintecher.pangolin.report.entity.CaseInfo;
import cn.fintecher.pangolin.report.mapper.CaseInfoMapper;
import cn.fintecher.pangolin.report.model.*;
import cn.fintecher.pangolin.report.util.ExcelExportHelper;
import cn.fintecher.pangolin.util.ZWDateUtil;
import com.github.pagehelper.PageHelper;
import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.inject.Inject;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author: PeiShouWen
 * @Description:
 * @Date 18:07 2017/8/1
 */
@Service("caseInfoService")
public class CaseInfoService {
    final Logger log = LoggerFactory.getLogger(CaseInfoService.class);

    @Inject
    RestTemplate restTemplate;

    @Autowired
    CaseInfoMapper caseInfoMapper;

    public List<CaseInfo> getAll(CaseInfo caseInfo) {
        if (caseInfo.getPage() != null && caseInfo.getRows() != null) {
            PageHelper.startPage(caseInfo.getPage(), caseInfo.getRows());
        }
        return caseInfoMapper.selectAll();
    }

    public List<CaseInfo> queryWaitCollectCase(CaseInfoParams caseInfoParams, int page, int size, User user) {
        List<CaseInfo> list = null;
        PageHelper.startPage(page + 1, size);
        if (Objects.equals(user.getManager(), User.MANAGER_TYPE.DATA_AUTH.getValue())) {
            list = caseInfoMapper.queryWaitCollectCase(caseInfoParams);
        } else {
            list = caseInfoMapper.queryWaitOwnCollectCase(caseInfoParams);
        }
        return list;
    }

    public void updateLngLat(Personal personal) {
        caseInfoMapper.updateLngLat(personal);
    }

    public List<CollectingCaseInfo> queryCollectingCase(CollectingCaseParams collectingCaseParams, int page, int size) {
        List<CollectingCaseInfo> list = null;
        PageHelper.startPage(page + 1, size);
        list = caseInfoMapper.queryCollectingCase(collectingCaseParams);
        return list;
    }


    //查询核销案件列表
    public List<QueryChargeOffResponse> queryChargeOffList(QueryChargeOffParams queryChargeOffParams){
        return caseInfoMapper.queryChargeOffList(queryChargeOffParams);
    }

    /**
     * 获取导出案件跟踪数据
     *
     * @param caseInfo
     * @return
     */
    public String queryExportCaseInfo(CaseInfoParams caseInfo) throws ParseException{

        //  处理逾期开始时间
        if(null != caseInfo.getStartOverDueDate() && null != caseInfo.getEndOverDueDate() && caseInfo.getStartOverDueDate() != "" && caseInfo.getEndOverDueDate() != ""){
            String startOverDueDate = caseInfo.getStartOverDueDate().toString();
            String endOverDueDate = caseInfo.getEndOverDueDate();
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            Date date1 = df.parse(startOverDueDate);
            Date date2 = df.parse(endOverDueDate);
            Date startDate =  ZWDateUtil.getLastDate(date1, 1, "yyyy-MM-dd");
            Date endDate =  ZWDateUtil.getLastDate(date2, 1, "yyyy-MM-dd");
            caseInfo.setStartOverDueDate(df.format(startDate));
            caseInfo.setEndOverDueDate(df.format(endDate));
        }
        List<CaseInfoModel> list = caseInfoMapper.getExportCaseInfoBycaseInfo(caseInfo);
        if (list == null || list.size() == 0) {
            throw new RuntimeException("没有数据");
        }
        // 合并共债案件

        List<CaseInfoModel> list1 = new ArrayList<>();
        List<String> caseNums = new ArrayList<>();
        list.forEach(single ->{list1.add(single);});
        List<CaseInfoModel> list2 = new ArrayList<>();
        for  ( int  i  =   0 ; i  <  list1.size() ; i ++ )  {
            if (caseNums.contains(list.get(i).getCaseNumber())){
                continue;
            }
            caseNums.add(list.get(i).getCaseNumber());
            CaseInfoModel caseInfoModel = list.get(i);

            for (int  j  =   i+1 ; j  <  list1.size() ; j ++ ){
                if  (caseInfoModel.getCaseNumber().equals(list.get(j).getCaseNumber()))  {
                    if (Objects.nonNull(list.get(j).getOverduePeriods()) && Objects.nonNull(caseInfoModel.getOverduePeriods())){
                        // 判断逾期期数大小
                        if (new BigDecimal(caseInfoModel.getOverduePeriods()).compareTo(new BigDecimal(list.get(j).getOverduePeriods())) < 1){
                            caseInfoModel.setOverduePeriods(list.get(j).getOverduePeriods());
                        }
                    }
                    // 合并逾期总金额
                    String overdueAmounts = new BigDecimal(caseInfoModel.getOverdueAmount()).add(new BigDecimal(list.get(j).getOverdueAmount())).toString();
                    caseInfoModel.setOverdueAmount(overdueAmounts);
                    // 合并到账金额
                    caseInfoModel.setAccountBalance(caseInfoModel.getAccountBalance().add(list.get(j).getAccountBalance()));
                    // 催收反馈多个借据时，其中有为空的，需要取不为空的
                    if (Objects.isNull(caseInfoModel.getFollowupBack())) {
                        Integer followupBack = list.get(j).getFollowupBack();
                        if (Objects.nonNull(followupBack)) {
                            caseInfoModel.setFollowupBack(followupBack);
                        }
                    }
                }
            }
            list2.add(caseInfoModel);
        }



        HSSFWorkbook workbook = null;
        File file = null;
        ByteArrayOutputStream out = null;
        FileOutputStream fileOutputStream = null;

        Map<String, String> headMap = new LinkedHashMap<>(); //excel头
        List<Map<String, Object>> dataList = new ArrayList<>();
        headMap.put("caseNumber", "案件编号");
        headMap.put("personalName", "客户姓名");
        headMap.put("mobileNo", "手机号");
        headMap.put("idCard", "身份证号");
        headMap.put("cityName", "地区");
        headMap.put("merchantName", "经销商");
        headMap.put("overDueDate", "逾期开始时间");
        headMap.put("overduePeriods","逾期期数");
        headMap.put("overdueDays", "逾期天数");
        headMap.put("seriesName", "产品类型");
        headMap.put("productName", "产品名称");
        headMap.put("sourceChannel", "来源渠道");
        headMap.put("collectionMethod", "催收模式");
        headMap.put("settleDate", "出催时间");
        headMap.put("accountBalance", "贷款余额");
        headMap.put("overdueAmount", "逾期金额");
        headMap.put("deptName", "机构");
        headMap.put("collectorName", "催收员");
        headMap.put("followupBack", "催收反馈");
        try {
            Map<String, Object> map;
            DecimalFormat format = new DecimalFormat("0.00");
            for (CaseInfoModel caseInfoModel : list2) {
                map = new LinkedHashMap<>();
                map.put("caseNumber", (Objects.isNull(caseInfoModel.getCaseNumber()) ? "" : caseInfoModel.getCaseNumber()));
                map.put("personalName", (Objects.isNull(caseInfoModel.getPersonalName()) ? "" : caseInfoModel.getPersonalName()));
                map.put("mobileNo", (Objects.isNull(caseInfoModel.getMobileNo()) ? "" : caseInfoModel.getMobileNo()));
                map.put("idCard", (Objects.isNull(caseInfoModel.getIdCard()) ? "" : caseInfoModel.getIdCard()));
                map.put("cityName", (Objects.isNull(caseInfoModel.getCityName()) ? "" : caseInfoModel.getCityName()));
                map.put("merchantName", (Objects.isNull(caseInfoModel.getMerchantName()) ? "" : caseInfoModel.getMerchantName()));
                map.put("overDueDate", (Objects.isNull(caseInfoModel.getOverDueDate()) ? "" : ZWDateUtil.getAfter(caseInfoModel.getOverDueDate(),1,null)));
                map.put("overduePeriods",(Objects.isNull(caseInfoModel.getPayStatus()) ? "" : caseInfoModel.getPayStatus()));
                map.put("overdueDays", (Objects.isNull(caseInfoModel.getOverdueDays()) ? "" : caseInfoModel.getOverdueDays()));
                map.put("seriesName", (Objects.isNull(caseInfoModel.getSeriesName()) ? "" : caseInfoModel.getSeriesName()));
                map.put("productName", (Objects.isNull(caseInfoModel.getProductName()) ? "" : caseInfoModel.getProductName()));
                map.put("sourceChannel", (Objects.isNull(caseInfoModel.getSourceChannel()) ? "" : caseInfoModel.getSourceChannel()));
                map.put("collectionMethod", (Objects.isNull(caseInfoModel.getCollectionMethod()) ? "" : caseInfoModel.getCollectionMethod()));
                map.put("settleDate", (Objects.isNull(caseInfoModel.getSettleDate()) ? "" : caseInfoModel.getSettleDate()));
                map.put("overdueAmount", (Objects.isNull(caseInfoModel.getOverdueAmount()) ? "" : format.format(new BigDecimal(caseInfoModel.getOverdueAmount()))));
                map.put("accountBalance", (Objects.isNull(caseInfoModel.getAccountBalance()) ? "" : format.format(caseInfoModel.getAccountBalance())));
                map.put("deptName", (Objects.isNull(caseInfoModel.getDeptName()) ? "" : caseInfoModel.getDeptName()));
                map.put("collectorName", (Objects.isNull(caseInfoModel.getCollectorName()) ? "" : caseInfoModel.getCollectorName()));
                if(Objects.nonNull(caseInfoModel.getFollowupBack())){
                    String followBack = caseInfoMapper.getDataDict(caseInfoModel.getFollowupBack());
                    map.put("followupBack" , followBack);
                }else{
                    map.put("followupBack" , "");
                }
                dataList.add(map);
            }

            workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet("案件跟踪");
            ExcelExportHelper.createExcel(workbook, sheet, headMap, dataList, 0, 0);
            out = new ByteArrayOutputStream();
            workbook.write(out);
            String filePath = FileUtils.getTempDirectoryPath().concat(File.separator).concat(DateTime.now().toString("yyyyMMddhhmmss") + "案件跟踪.xls");
            file = new File(filePath);
            fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(out.toByteArray());
            FileSystemResource resource = new FileSystemResource(file);
            MultiValueMap<String, Object> param = new LinkedMultiValueMap<>();
            param.add("file", resource);
            return restTemplate.postForEntity("http://file-service/api/uploadFile/addUploadFileUrl", param, String.class).getBody();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        } finally {
            // 关闭流
            if (workbook != null) {
                try {
                    workbook.close();
                } catch (IOException e) {
                    log.error(e.getMessage());
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    log.error(e.getMessage());
                }
            }
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    log.error(e.getMessage());
                }
            }
        }
    }
}
