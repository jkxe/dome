package cn.fintecher.pangolin.business.web;

import cn.fintecher.pangolin.business.model.InvalidContactCaseParam;
import cn.fintecher.pangolin.business.repository.*;
import cn.fintecher.pangolin.business.service.ContactFeedbackService;
import cn.fintecher.pangolin.business.service.ReminderService;
import cn.fintecher.pangolin.business.utils.ExcelExportHelper;
import cn.fintecher.pangolin.entity.*;
import cn.fintecher.pangolin.util.ZWDateUtil;
import cn.fintecher.pangolin.web.HeaderUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.ListUtils;
import org.apache.commons.io.FileUtils;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.drools.core.metadata.Lit;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import springfox.documentation.annotations.ApiIgnore;

import javax.inject.Inject;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by duchao on 2018/6/15.
 */

@RestController
@RequestMapping("/api/contactFeedbackController")
@Api(value = "ContactFeedbackController", description = "联系反馈")
public class ContactFeedbackController extends BaseController {

    private final Logger logger = LoggerFactory.getLogger(ContactFeedbackController.class);

    @Autowired
    private MissingConnectionInfoRepository missingConnectionInfoRepository;

    @Autowired
    private TaskBoxRepository taskBoxRepository;

    @Autowired
    private ContactFeedbackService contactFeedbackService;

    @Autowired
    private ReminderService reminderService;

    @Autowired
    private RestTemplate restTemplate;


    @GetMapping("/invalidContactCase")
    @ApiOperation(value = "获取定义为失联案件的相关信息", notes = "获取定义为失联案件的相关信息")
    public ResponseEntity<Page<MissingConnectionInfo>> invalidContactCase(@ApiIgnore Pageable pageable,
                                                                          @RequestParam(required = false) String caseNumber,
                                                                          @RequestParam(required = false) String personalName,
                                                                          @RequestParam(required = false) String mobileNo,
                                                                          @RequestParam(required = false) String idCard,
                                                                          @RequestParam(required = false) String contractNumber,
                                                                          @RequestHeader(value = "X-UserToken") String token) {
        InvalidContactCaseParam param = new InvalidContactCaseParam();
        param.setCaseNumber(caseNumber);
        param.setPersonalName(personalName);
        param.setIdCard(idCard);
        param.setMobileNo(mobileNo);
        param.setContractNumber(contractNumber);
        try {
            User user = getUserByToken(token);
            List<MissingConnectionInfo> list = contactFeedbackService.getAllMissingConnectionInfo(user.getCompanyCode(), param);
            // 合并共债案件
            List<MissingConnectionInfo> list1 = new ArrayList<>();
            List<String> caseNums = new ArrayList<>();
            for  ( int  i  =   0 ; i  <  list.size() ; i ++ )  {
                if (caseNums.contains(list.get(i).getCaseNumber())){
                    continue;
                }
                caseNums.add(list.get(i).getCaseNumber());
                MissingConnectionInfo missingConnectionInfo = list.get(i);
                list1.add(missingConnectionInfo);
            }
            List<MissingConnectionInfo> collect = list1.stream().skip(pageable.getPageNumber() * pageable.getPageSize()).limit(pageable.getPageSize()).collect(Collectors.toList());
            Page<MissingConnectionInfo> page = new PageImpl<>(collect, pageable, list1.size());
            return ResponseEntity.ok().body(page);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(
                    "invalidContactCase", "invalidContactCase", e.getMessage())).body(null);
        }
    }

    @GetMapping("/invalidContactCaseExport")
    @ApiOperation(value = "失联案件导出", notes = "失联案件导出")
    public ResponseEntity invalidContactCaseExport(@RequestHeader(value = "X-UserToken") String token,
                                                   @RequestParam(required = false) String caseNumber,
                                                   @RequestParam(required = false) String personalName,
                                                   @RequestParam(required = false) String mobileNo,
                                                   @RequestParam(required = false) String idCard,
                                                   @RequestParam(required = false) String contractNumber) {
        InvalidContactCaseParam invalidContactCaseParam = new InvalidContactCaseParam();
        invalidContactCaseParam.setCaseNumber(caseNumber);
        invalidContactCaseParam.setPersonalName(personalName);
        invalidContactCaseParam.setIdCard(idCard);
        invalidContactCaseParam.setMobileNo(mobileNo);
        invalidContactCaseParam.setContractNumber(contractNumber);
        User user = null;
        try {
            user = getUserByToken(token);
        } catch (final Exception e) {
            logger.debug(e.getMessage());
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("", "", e.getMessage())).body(null);
        }

        List<Map<String, Object>> dataList = contactFeedbackService.createExcelData(user.getCompanyCode(), invalidContactCaseParam);
        if (!dataList.isEmpty()) {
            // 保存任务盒子
            TaskBox taskBox = new TaskBox();
            taskBox.setOperatorTime(ZWDateUtil.getNowDateTime());
            taskBox.setOperator(user.getId());
            taskBox.setTaskStatus(TaskBox.Status.UN_FINISH.getValue());
            taskBox.setTaskDescribe("失联案件导出");
            taskBox.setCompanyCode(user.getCompanyCode());
            taskBox.setBeginDate(ZWDateUtil.getNowDateTime());
            taskBox.setType(TaskBox.Type.EXPORT.getValue());
            TaskBox finalTaskBox = taskBoxRepository.save(taskBox);

            XSSFWorkbook workbook = null;
            File file = null;
            ByteArrayOutputStream out = null;
            FileOutputStream fileOutputStream = null;

            //存储数据信息
            try {
                Map<String, String> headMap = contactFeedbackService.createExcelHeader();

                workbook = new XSSFWorkbook();
                XSSFSheet sheet = workbook.createSheet("失联案件");
                ExcelExportHelper.createExcel(workbook, sheet, headMap, dataList, 0, 0);
                out = new ByteArrayOutputStream();
                workbook.write(out);
                String filePath = FileUtils.getTempDirectoryPath().concat(File.separator).concat(DateTime.now().toString("yyyyMMddhhmmss") + "失联案件表.xlsx");
                file = new File(filePath);
                fileOutputStream = new FileOutputStream(file);
                fileOutputStream.write(out.toByteArray());
                FileSystemResource resource = new FileSystemResource(file);
                MultiValueMap<String, Object> param = new LinkedMultiValueMap<>();
                param.add("file", resource);
                ResponseEntity<String> url = restTemplate.postForEntity("http://file-service/api/uploadFile/addUploadFileUrl", param, String.class);
                if (url != null && url.getBody() != null && !url.getBody().trim().isEmpty()) {
                    finalTaskBox.setTaskStatus(TaskBox.Status.FINISHED.getValue());
                    finalTaskBox.setRemark(url.getBody());
                } else {
                    finalTaskBox.setTaskStatus(TaskBox.Status.FAILURE.getValue());
                }
            } catch (Exception e) {
                finalTaskBox.setTaskStatus(TaskBox.Status.FAILURE.getValue());
                logger.error(e.getMessage(), e);
            } finally {
                finalTaskBox.setEndDate(ZWDateUtil.getNowDateTime());
                taskBoxRepository.save(finalTaskBox);
                try {
                    reminderService.sendTaskBoxMessage(finalTaskBox);
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                    logger.error("客户信息导出发送消息失败");
                }
                // 关闭流
                if (workbook != null) {
                    try {
                        workbook.close();
                    } catch (IOException e) {
                        logger.error(e.getMessage());
                    }
                }
                if (out != null) {
                    try {
                        out.close();
                    } catch (IOException e) {
                        logger.error(e.getMessage());
                    }
                }
                if (fileOutputStream != null) {
                    try {
                        fileOutputStream.close();
                    } catch (IOException e) {
                        logger.error(e.getMessage());
                    }
                }
                // 删除文件
                if (file != null) {
                    file.delete();
                }
            }
            return ResponseEntity.ok().body(finalTaskBox.getId());
        }
        return ResponseEntity.badRequest().headers(HeaderUtil.createAlert("案件列表为空", "")).body(null);
    }

}
