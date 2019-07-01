package cn.fintecher.pangolin.report.web;

import cn.fintecher.pangolin.entity.TaskBox;
import cn.fintecher.pangolin.entity.User;
import cn.fintecher.pangolin.entity.util.ExcelExportUtil;
import cn.fintecher.pangolin.report.mapper.QueryFollowupMapper;
import cn.fintecher.pangolin.report.model.*;
import cn.fintecher.pangolin.report.service.ExportFollowupService;
import cn.fintecher.pangolin.report.service.FollowRecordExportService;
import cn.fintecher.pangolin.report.service.ReminderService;
import cn.fintecher.pangolin.report.util.ExcelExportHelper;
import cn.fintecher.pangolin.util.ZWDateUtil;
import cn.fintecher.pangolin.web.HeaderUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;


/**
 * @Author : baizhangyu
 * @Description : 导出跟进记录
 * @Date : 2017/9/6.
 */
@RestController
@RequestMapping("/api/exportFollowupController")
@Api(value = "ExportFollowupController", description = "导出跟进记录")
public class ExportFollowupController extends BaseController {

    private final Logger log = LoggerFactory.getLogger(ExportFollowupController.class);
    private static final String ENTITY_NAME = "ExportFollowupController";

    @Autowired
    private ExportFollowupService exportFollowupService;
    @Inject
    private FollowRecordExportService followRecordExportService;
    @Inject
    private QueryFollowupMapper queryFollowupMapper;
    @Inject
    private RabbitTemplate rabbitTemplate;
    @Inject
    private ReminderService reminderService;

    @PostMapping(value = "/getExcelData")
    @ApiOperation(value = "导出跟进记录", notes = "导出跟进记录")
    public ResponseEntity getExcelData(@RequestBody ExportFollowupModel exportFollowupModel,
                                       @RequestHeader(value = "X-UserToken") @ApiParam("操作者的Token") String token) {
        XSSFWorkbook workbook = null;
        File file = null;
        ByteArrayOutputStream out = null;
        FileOutputStream fileOutputStream = null;

        User user = null;
        try {
            user = getUserByToken(token);
        } catch (final Exception e) {
            log.debug(e.getMessage());
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("CaseInfoController", "exportCaseInfoFollowRecord", e.getMessage())).body(null);
        }
        try {
            String companyCode = user.getCompanyCode();
            if (Objects.isNull(user.getCompanyCode())) {
                if (StringUtils.isNotBlank(exportFollowupModel.getCompanyCode())) {
                    companyCode = exportFollowupModel.getCompanyCode();
                }
            }
            List<String> caseNumberList = exportFollowupModel.getList();
            if (caseNumberList.isEmpty()) {
                return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("", "", "请选择案件!")).body(null);
            }
            List<ExportFollowupParams> caseFollowupRecords = exportFollowupService.getExcelData(caseNumberList, companyCode);
            if (caseFollowupRecords.isEmpty()) {
                return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("CaseInfoController", "", "要导出的跟进记录数据为空!")).body(null);
            }
            if (caseFollowupRecords.size() > 10000) {
                return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("CaseInfoController", "", "不支持导出数据超过10000条!")).body(null);
            }
            workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet("sheet1");
            out = new ByteArrayOutputStream();
            Map<String, String> head = followRecordExportService.createHead();
            List<Map<String, Object>> data = followRecordExportService.createData(caseFollowupRecords);
            ExcelExportHelper.createExcel(workbook, sheet, head, data, 0, 0);
            workbook.write(out);
            String filePath = FileUtils.getTempDirectoryPath().concat(File.separator).concat(DateTime.now().toString("yyyyMMddhhmmss") + "跟进记录.xlsx");
            file = new File(filePath);
            fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(out.toByteArray());
            FileSystemResource resource = new FileSystemResource(file);
            MultiValueMap<String, Object> param = new LinkedMultiValueMap<>();
            param.add("file", resource);
            ResponseEntity<String> url = restTemplate.postForEntity("http://file-service/api/uploadFile/addUploadFileUrl", param, String.class);
            if (url == null) {
                return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("CaseInfoController", "exportCaseInfoFollowRecord", "系统错误!")).body(null);
            } else {
                return ResponseEntity.ok().body(url.getBody());
            }
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("CaseInfoController", "exportCaseInfoFollowRecord", "上传文件服务器失败")).body(null);
        } finally {
            // 关闭流
            if (workbook != null) {
                try {
                    workbook.close();
                } catch (IOException e) {
                    log.error(e.getMessage(), e);
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    log.error(e.getMessage(), e);
                }
            }
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    log.error(e.getMessage(), e);
                }
            }
            // 删除文件
            if (file != null) {
                file.deleteOnExit();
            }
        }
    }

    @PostMapping(value = "/exportFollowupRecord")
    @ApiOperation(notes = "导出跟进记录", value = "导出跟进记录")
    public ResponseEntity exportFollowupRecord(@RequestBody(required = false) ExportFollowRecordParams exportFollowupParams,
                                               @RequestHeader(value = "X-UserToken") @ApiParam("操作者的Token") String token) {

        User user = null;
        try {
            user = getUserByToken(token);
        } catch (final Exception e) {
            log.debug(e.getMessage());
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("CaseInfoController", "exportCaseInfoFollowRecord", e.getMessage())).body(null);
        }
        exportFollowupParams.setCompanyCode(user.getCompanyCode());
        ResponseEntity<ItemsModel> entity = null;
        if (exportFollowupParams.getType() == 0) {
            entity = restTemplate.getForEntity("http://business-service/api/exportItemResource/getExportItems?token=" + token, ItemsModel.class);
        } else if ((exportFollowupParams.getType() == 1)) {
            entity = restTemplate.getForEntity("http://business-service/api/exportItemResource/getExportItemsClosed?token=" + token, ItemsModel.class);
        } else {
            entity = restTemplate.getForEntity("http://business-service/api/exportItemResource/getExportItemsEliminate?token=" + token, ItemsModel.class);
        }

        ItemsModel itemsModel = entity.getBody();
        if (itemsModel.getPersonalItems().isEmpty() && itemsModel.getJobItems().isEmpty() && itemsModel.getConnectItems().isEmpty()
                && itemsModel.getCaseItems().isEmpty() && itemsModel.getBankItems().isEmpty() && itemsModel.getFollowItems().isEmpty()) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("", "", "请设置导出项")).body(null);
        }
        List<ExcportResultModel> all = new ArrayList<>();
        if (Objects.equals(exportFollowupParams.getType(), 1)) {
            all = queryFollowupMapper.findFollowupRecord(exportFollowupParams);
        } else {
            all = queryFollowupMapper.findCollingFollowupRecord(exportFollowupParams);
        }
        List<FollowupExportModel> dataList = new ArrayList<>();
        if (!all.isEmpty()) {
            dataList = followRecordExportService.getFollowupData(all);
            if (dataList.isEmpty()) {
                return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "", "要导出的数据为空")).body(null);
            }
        } else {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "", "要导出的数据为空")).body(null);
        }
        List<String> items = new ArrayList<>();
        items.addAll(itemsModel.getPersonalItems());
        items.addAll(itemsModel.getJobItems());
        items.addAll(itemsModel.getCaseItems());
        items.addAll(followRecordExportService.parseConnect(itemsModel.getConnectItems()));
        items.addAll(itemsModel.getBankItems());
        items.addAll(itemsModel.getFollowItems());
        exportFollowupParams.setExportItemList(items);

        TaskBox taskBox = new TaskBox();
        taskBox.setBeginDate(ZWDateUtil.getNowDateTime());
        taskBox.setOperatorTime(ZWDateUtil.getNowDateTime());
        taskBox.setCompanyCode(user.getCompanyCode());
        taskBox.setType(TaskBox.Type.EXPORT.getValue());
        taskBox.setTaskStatus(TaskBox.Status.UN_FINISH.getValue());
        taskBox.setOperator(user.getId());
        taskBox.setTaskDescribe("跟进记录导出");
        ResponseEntity<TaskBox> responseEntity = restTemplate.postForEntity("http://business-service/api/taskBoxResource", taskBox, TaskBox.class);

        TaskBox finalTaskBox = responseEntity.getBody();
        String taskId = finalTaskBox.getId();
        ResponseEntity<String> url;
        SXSSFWorkbook workbook = null;
        File file = null;
        ByteArrayOutputStream out = null;
        FileOutputStream fileOutputStream = null;
        try {
            String[] title = exportFollowupParams.getExportItemList().toArray(new String[exportFollowupParams.getExportItemList().size()]);
            Map<String, String> headMap = ExcelExportUtil.createHeadMap(title, FollowupExportModel.class);
            workbook = new SXSSFWorkbook(5000);
            ExcelExportUtil.createExcelData(workbook, headMap, dataList, 1048575);
            out = new ByteArrayOutputStream();
            workbook.write(out);
            String filePath = FileUtils.getTempDirectoryPath().concat(File.separator).concat(DateTime.now().toString("yyyyMMddhhmmss") + "跟进记录.xlsx");
            file = new File(filePath);
            fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(out.toByteArray());
            FileSystemResource resource = new FileSystemResource(file);
            MultiValueMap<String, Object> param = new LinkedMultiValueMap<>();
            param.add("file", resource);
            url = restTemplate.postForEntity("http://file-service/api/uploadFile/addUploadFileUrl", param, String.class);
            if (Objects.nonNull(url) && Objects.nonNull(url.getBody()) && !url.getBody().isEmpty()) {
                finalTaskBox.setTaskStatus(TaskBox.Status.FINISHED.getValue());
                finalTaskBox.setRemark(url.getBody());
            } else {
                finalTaskBox.setTaskStatus(TaskBox.Status.FAILURE.getValue());
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            finalTaskBox.setTaskStatus(TaskBox.Status.FAILURE.getValue());
        } finally {
            finalTaskBox.setEndDate(ZWDateUtil.getNowDateTime());
            restTemplate.postForEntity("http://business-service/api/taskBoxResource", finalTaskBox, TaskBox.class);
            try {
                reminderService.sendTaskBoxMessage(finalTaskBox);
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                log.error("客户信息导出发送消息失败");
            }
            // 关闭流
            if (workbook != null) {
                try {
                    workbook.close();
                } catch (IOException e) {
                    log.error(e.getMessage(), e);
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    log.error(e.getMessage(), e);
                }
            }
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    log.error(e.getMessage(), e);
                }
            }
            // 删除文件
            if (file != null) {
                file.deleteOnExit();
            }
        }
        return ResponseEntity.ok().headers(HeaderUtil.createAlert("开始导出,完成后请前往消息列表查看下载。", "")).body(taskId);
    }


   /* private void sendReminder(String title, String content, String userId) {
        SendReminderMessage sendReminderMessage = new SendReminderMessage();
        sendReminderMessage.setTitle(title);
        sendReminderMessage.setContent(content);
        sendReminderMessage.setType(ReminderType.FOLLOWUP_EXPORT);
        sendReminderMessage.setMode(ReminderMode.POPUP);
        sendReminderMessage.setCreateTime(new Date());
        sendReminderMessage.setUserId(userId);
        //发送消息
        rabbitTemplate.convertAndSend(Constants.FOLLOWUP_EXPORT_QE, sendReminderMessage);
    }*/
}
