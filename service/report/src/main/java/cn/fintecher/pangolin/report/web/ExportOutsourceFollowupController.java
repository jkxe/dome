package cn.fintecher.pangolin.report.web;

import cn.fintecher.pangolin.report.service.ReminderService;
import cn.fintecher.pangolin.entity.TaskBox;
import cn.fintecher.pangolin.entity.User;
import cn.fintecher.pangolin.entity.util.Constants;
import cn.fintecher.pangolin.entity.util.ExcelExportUtil;
import cn.fintecher.pangolin.report.mapper.QueryOutsourceFollowupMapper;
import cn.fintecher.pangolin.report.model.ExcportOutsourceResultModel;
import cn.fintecher.pangolin.report.model.ExportOutsourceFollowRecordParams;
import cn.fintecher.pangolin.report.model.FollowupExportModel;
import cn.fintecher.pangolin.report.model.ItemsModel;
import cn.fintecher.pangolin.report.service.FollowRecordExportService;
import cn.fintecher.pangolin.report.service.OutsourceFollowRecordExportService;
import cn.fintecher.pangolin.util.ZWDateUtil;
import cn.fintecher.pangolin.web.HeaderUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.io.FileUtils;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
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
 * @Author : huaynmin
 * @Description : 导出委外跟进记录
 * @Date : 2017/9/27.
 */
@RestController
@RequestMapping("/api/exportOutsourceFollowupController")
@Api(value = "ExportOutsourceFollowupController", description = "导出委外跟进记录")
public class ExportOutsourceFollowupController extends BaseController {

    private final Logger log = LoggerFactory.getLogger(ExportOutsourceFollowupController.class);
    private static final String ENTITY_NAME = "ExportFollowupController";

    @Inject
    private OutsourceFollowRecordExportService outsourceFollowRecordExportService;
    @Inject
    private FollowRecordExportService followRecordExportService;
    @Inject
    private QueryOutsourceFollowupMapper queryOutsourceFollowupMapper;
    @Inject
    private RabbitTemplate rabbitTemplate;
    @Inject
    private ReminderService reminderService;

    @PostMapping(value = "/exportOutsourceFollowupRecord")
    @ApiOperation(notes = "导出委外跟进记录", value = "导出委外跟进记录")
    public ResponseEntity<String> exportOutsourceFollowupRecord(@RequestBody ExportOutsourceFollowRecordParams exportOutsourceFollowRecordParams,
                                                                @RequestHeader(value = "X-UserToken") @ApiParam("操作者的Token") String token) {
        User user = null;
        try {
            user = getUserByToken(token);
            if (Objects.nonNull(user.getCompanyCode())) {
                exportOutsourceFollowRecordParams.setCompanyCode(user.getCompanyCode());
            }
        } catch (final Exception e) {
            log.debug(e.getMessage());
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("CaseInfoController", "exportCaseInfoFollowRecord", e.getMessage())).body(null);
        }
        final String userId = user.getId();
        ResponseEntity<ItemsModel> entity = null;
        if (exportOutsourceFollowRecordParams.getType() == 0) {
            entity = restTemplate.getForEntity("http://business-service/api/exportItemResource/getOutsourceExportItems?token=" + token, ItemsModel.class);
        } else if (exportOutsourceFollowRecordParams.getType() == 1) {
            entity = restTemplate.getForEntity("http://business-service/api/exportItemResource/getOutsourceFollowUpExportItems?token=" + token, ItemsModel.class);
        } else {
            entity = restTemplate.getForEntity("http://business-service/api/exportItemResource/getOutsourceClosedFollowUpExportItems?token=" + token, ItemsModel.class);
        }
        ItemsModel itemsModel = entity.getBody();
        if (itemsModel.getPersonalItems().isEmpty() && itemsModel.getJobItems().isEmpty() && itemsModel.getConnectItems().isEmpty()
                && itemsModel.getCaseItems().isEmpty() && itemsModel.getBankItems().isEmpty() && itemsModel.getFollowItems().isEmpty()) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("", "", "请设置导出项")).body(null);
        }
        List<String> items = new ArrayList<>();
        items.addAll(itemsModel.getPersonalItems());
        items.addAll(itemsModel.getJobItems());
        items.addAll(followRecordExportService.parseConnect(itemsModel.getConnectItems()));
        items.addAll(itemsModel.getCaseItems());
        items.addAll(itemsModel.getBankItems());
        items.addAll(itemsModel.getFollowItems());
        exportOutsourceFollowRecordParams.setExportItemList(items);
        List<ExcportOutsourceResultModel> all = null;
        if (exportOutsourceFollowRecordParams.getType() == 0) {
            all = queryOutsourceFollowupMapper.findOutsourceRecord(exportOutsourceFollowRecordParams);
        } else {
            all = queryOutsourceFollowupMapper.findOutsourceFollowupRecord(exportOutsourceFollowRecordParams);
        }
        if (all.isEmpty()) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("", "", "要导出的数据为空")).body(null);
        }
        // 保存任务盒子
        TaskBox taskBox = new TaskBox();
        taskBox.setOperatorTime(ZWDateUtil.getNowDateTime());
        taskBox.setOperator(user.getId());
        taskBox.setTaskStatus(TaskBox.Status.UN_FINISH.getValue());
        if (exportOutsourceFollowRecordParams.getType() == 0) {
            taskBox.setTaskDescribe("导出委外案件");
        } else {
            taskBox.setTaskDescribe("导出委外跟进记录");
        }
        taskBox.setCompanyCode(user.getCompanyCode());
        taskBox.setBeginDate(ZWDateUtil.getNowDateTime());
        taskBox.setType(TaskBox.Type.EXPORT.getValue());
        ResponseEntity<TaskBox> responseEntity = restTemplate.postForEntity("http://business-service/api/taskBoxResource", taskBox, TaskBox.class);
        TaskBox finalTaskBox = responseEntity.getBody();
        String taskId = finalTaskBox.getId();
        //创建一个线程，执行导出任务
        List<ExcportOutsourceResultModel> finalAll = all;
        Thread t = new Thread(() -> {
            SXSSFWorkbook workbook = null;
            File file = null;
            ByteArrayOutputStream out = null;
            FileOutputStream fileOutputStream = null;
            try {
                ResponseEntity<String> url = null;
                int maxNum = outsourceFollowRecordExportService.getMaxNum(finalAll);
                List<FollowupExportModel> dataList = null;
                if (exportOutsourceFollowRecordParams.getType() == 0) {
                    dataList = outsourceFollowRecordExportService.getOutsourceRecordData(finalAll);
                } else {
                    dataList = outsourceFollowRecordExportService.getFollowupData(finalAll);
                }
                String[] title = followRecordExportService.getTitle(exportOutsourceFollowRecordParams.getExportItemList(), maxNum);
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
                url = restTemplate.postForEntity(Constants.UPLOAD_FILE_URL, param, String.class);
                log.debug(url.getBody());
                if (url != null && url.getBody() != null && !url.getBody().trim().isEmpty()) {
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
                    log.error("导出发送消息失败");
                }
                // 关闭流
                if (workbook != null) {
                    try {
                        workbook.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (out != null) {
                    try {
                        out.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (fileOutputStream != null) {
                    try {
                        fileOutputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                // 删除文件
                if (file != null) {
                    file.delete();
                }
            }
        });
        t.start();
        return ResponseEntity.ok().headers(HeaderUtil.createAlert("开始导出,完成后请前往消息列表查看下载。", "")).body(taskId);
    }

}
