package cn.fintecher.pangolin.business.web;

import cn.fintecher.pangolin.business.model.LoadCaseModel;
import cn.fintecher.pangolin.business.model.MapModel;
import cn.fintecher.pangolin.business.model.PersonalInfoExportModel;
import cn.fintecher.pangolin.business.repository.*;
import cn.fintecher.pangolin.business.service.AccMapService;
import cn.fintecher.pangolin.business.service.PersonalInfoExportService;
import cn.fintecher.pangolin.business.service.ReminderService;
import cn.fintecher.pangolin.business.utils.ExcelExportHelper;
import cn.fintecher.pangolin.entity.*;
import cn.fintecher.pangolin.util.ZWDateUtil;
import cn.fintecher.pangolin.web.HeaderUtil;
import cn.fintecher.pangolin.web.PaginationUtil;
import cn.fintecher.pangolin.web.ResponseUtil;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import io.swagger.annotations.*;
import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.io.FileUtils;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import springfox.documentation.annotations.ApiIgnore;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.DecimalFormat;
import java.util.*;

/**
 * Created by ChenChang on 2017/5/23.
 */
@RestController
@RequestMapping("/api/personalController")
@Api(value = "PersonalController", description = "分案表操作")
public class PersonalController extends BaseController {

    private static final String ENTITY_NAME = "personal";
    private static final String ENTITY_CASE_TURN_RECORD = "CaseTurnRecord";
    private static final String ENTITY_CASE_FOLLOWUP_RECORD = "CaseFollowupRecord";
    private static final String PERSONAL_CONTACT = "PersonalContact";
    private final Logger log = LoggerFactory.getLogger(PersonalController.class);

    @Inject
    private PersonalRepository personalRepository;
    @Inject
    private CaseInfoRepository caseInfoRepository;
    @Inject
    private RestTemplate restTemplate;
    @Inject
    private PersonalInfoExportService personalInfoExportService;
    @Inject
    private CaseTurnRecordRepository caseTurnRecordRepository;
    @Inject
    private DepartmentRepository departmentRepository;
    @Inject
    EntityManager em;
    @Inject
    AccMapService accMapService;
    @Inject
    TaskBoxRepository taskBoxRepository;
    @Inject
    ReminderService reminderService;

    @PostMapping("/personalInfoExport")
    @ApiOperation(value = "分案表", notes = "导出分案表")
    public ResponseEntity personalInfoExport(@RequestBody @ApiParam("配置项") PersonalInfoExportModel model,
                                             @RequestHeader(value = "X-UserToken") String token) {
        Map<String, List<Object>> dataFilter = model.getDataFilter();
        Map<String, List<String>> dataInfo = model.getDataInfo(); //数据项

        User user = null;
        try {
            user = getUserByToken(token);
        } catch (final Exception e) {
            log.debug(e.getMessage());
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("", "", e.getMessage())).body(null);
        }

        // 保存任务盒子
        TaskBox taskBox = new TaskBox();
        taskBox.setOperatorTime(ZWDateUtil.getNowDateTime());
        taskBox.setOperator(user.getId());
        taskBox.setTaskStatus(TaskBox.Status.UN_FINISH.getValue());
        taskBox.setTaskDescribe("导出分案表");
        taskBox.setCompanyCode(user.getCompanyCode());
        taskBox.setBeginDate(ZWDateUtil.getNowDateTime());
        taskBox.setType(TaskBox.Type.EXPORT.getValue());
        TaskBox finalTaskBox = taskBoxRepository.save(taskBox);

        XSSFWorkbook workbook = null;
        File file = null;
        ByteArrayOutputStream out = null;
        FileOutputStream fileOutputStream = null;

        try {
            Map<String, String> headMap; //存储头信息
            List<Map<String, Object>> dataList; //存储数据信息
            List<LoadCaseModel> LoadCaseModels = new ArrayList<>(); //数据
            List<List<String>> list = new ArrayList<>(); //选项
            Object[] allCaseModels = null;
            List<Integer> casePoolTypeList = new ArrayList<>();
            List<String> seriesIdList = new ArrayList<>();
            List<Integer> collectionStatusList = new ArrayList<>();

            if (Objects.nonNull(dataFilter.get("caseType"))) {
                for (Object o : dataFilter.get("caseType")) {
                    casePoolTypeList.add(Integer.valueOf(o.toString()));
                }
                allCaseModels = caseInfoRepository.getCaseInfoModelByCasePoolType(user.getCompanyCode(), casePoolTypeList);
            } else if (Objects.nonNull(dataFilter.get("seriesId"))) {
                for (Object o : dataFilter.get("seriesId")) {
                    seriesIdList.add(o.toString().trim());
                }
                allCaseModels = caseInfoRepository.getCaseInfoModelBySeriesId(user.getCompanyCode(), seriesIdList);
            } else if (Objects.nonNull(dataFilter.get("collectionState"))) {
                for (Object o : dataFilter.get("collectionState")) {
                    collectionStatusList.add(Integer.valueOf(o.toString()));
                }
                allCaseModels = caseInfoRepository.getCaseInfoModelByCollectionStatus(user.getCompanyCode(), collectionStatusList);
            } else {
                return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("PersonalController", "personalInfoExport", "数据筛选案件类型为空!")).body(null);
            }

            if (allCaseModels.length != 0) {
                DecimalFormat format = new DecimalFormat("0.00");
                for (int i = 0; i < allCaseModels.length; i++) {
                    Object[] object = (Object[]) allCaseModels[i];
                    if (object.length != 0) {
                        LoadCaseModel loadCaseModel = new LoadCaseModel();
                            loadCaseModel.setCaseNumber((String) object[0]);// 案件编号
                            loadCaseModel.setLoanInvoiceNumber((String) object[1]);//借据号
                            loadCaseModel.setCustName((String) object[2]);// 客户姓名
                            loadCaseModel.setIdCard((String) object[3]);//身份证
                            loadCaseModel.setPhone((String) object[4]);// 手机号
                            loadCaseModel.setPeriods((Integer) object[5]);//总期数
                            loadCaseModel.setOverduePeriods((Integer) object[6]);
                            loadCaseModel.setOverDays((Integer) object[7]);
                            loadCaseModel.setOverdueCapital(new BigDecimal(format.format((BigDecimal) object[8])));//  处理金额小数点
                            loadCaseModel.setOverAmt(new BigDecimal(format.format((BigDecimal) object[9])));
                            loadCaseModel.setAccountBalance(new BigDecimal(format.format((BigDecimal) object[10])));
                            loadCaseModel.setOverdueCount( (Integer) object[11]);
                            loadCaseModel.setLoanDate((Date) object[12]);
                            loadCaseModel.setPayStatus((String) object[13]);
                            loadCaseModel.setSeriesName((String) object[14]);
                            loadCaseModel.setProductName((String) object[15]);
                            loadCaseModel.setCity((String) object[16]);
                            loadCaseModel.setCaseStatus((Integer) object[17]);
                            loadCaseModel.setCaseType((Integer) object[18]);
                            loadCaseModel.setDeptName((String) object[19]);
                            loadCaseModel.setCurrentCollector((String) object[20]);
                            loadCaseModel.setCleanDate((Date) object[21]);
                            loadCaseModel.setSettleDate((Date) object[22]);
                            LoadCaseModels.add(loadCaseModel);
                    }
                }
            }

            if (LoadCaseModels.isEmpty()) {
                return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("PersonalController", "personalInfoExport", "要导出的数据为空!")).body(null);
            }
            List<String> baseInfo = dataInfo.get("baseInfo"); // 案件状态为维度数据选选项
            if (Objects.isNull(baseInfo) || baseInfo.isEmpty()) {
                return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("PersonalController", "personalInfoExport", "选项为空!")).body(null);
            }
            list.add(baseInfo);
            //}
            headMap = personalInfoExportService.createHeadMap(list);
            dataList = personalInfoExportService.createDataList(LoadCaseModels);
            workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet("分案表");
            ExcelExportHelper.createExcel(workbook, sheet, headMap, dataList, 0, 0);
            out = new ByteArrayOutputStream();
            workbook.write(out);
            String filePath = FileUtils.getTempDirectoryPath().concat(File.separator).concat(DateTime.now().toString("yyyyMMddhhmmss") + "分案表.xlsx");
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
            log.error(e.getMessage(), e);
        } finally {
            finalTaskBox.setEndDate(ZWDateUtil.getNowDateTime());
            taskBoxRepository.save(finalTaskBox);
            try {
                reminderService.sendTaskBoxMessage(finalTaskBox);
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                log.error("分案表导出发送消息失败");
            }
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
            // 删除文件
            if (file != null) {
                file.delete();
            }
        }
        return ResponseEntity.ok().body(finalTaskBox.getId());
    }

    @PostMapping("/personal")
    public ResponseEntity<Personal> createPersonal(@RequestBody Personal personal) throws URISyntaxException {
        log.debug("REST request to save personal : {}", personal);
        if (personal.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "新增案件不应该含有ID")).body(null);
        }
        Personal result = personalRepository.save(personal);
        return ResponseEntity.created(new URI("/api/personal/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
                .body(result);
    }

    @PutMapping("/personal")
    public ResponseEntity<Personal> updatePersonal(@RequestBody Personal personal) throws URISyntaxException {
        log.debug("REST request to update Personal : {}", personal);
        if (personal.getId() == null) {
            return createPersonal(personal);
        }
        Personal result = personalRepository.save(personal);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, personal.getId().toString()))
                .body(result);
    }

    @GetMapping("/personal")
    public List<Personal> getAllPersonal() {
        log.debug("REST request to get all Personal");
        List<Personal> personalList = personalRepository.findAll();
        return personalList;
    }

    @GetMapping("/queryPersonal")
    public ResponseEntity<Page<Personal>> queryPersonal(@QuerydslPredicate(root = Personal.class) Predicate predicate, @ApiIgnore Pageable pageable) throws URISyntaxException {
        log.debug("REST request to get all Personal");

        Page<Personal> page = personalRepository.findAll(predicate, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/queryPersonal");
        return new ResponseEntity<>(page, headers, HttpStatus.OK);
    }

    @GetMapping("/personal/{id}")
    public ResponseEntity<Personal> getPersonal(@PathVariable String id) {
        log.debug("REST request to get personal : {}", id);
        Personal personal = personalRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(personal));
    }

    @DeleteMapping("/personal/{id}")
    public ResponseEntity<Void> deletePersonal(@PathVariable String id) {
        log.debug("REST request to delete personal : {}", id);
        personalRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }

    /**
     * @Description 客户查询
     */
    @GetMapping("/getPersonalCaseInfo")
    @ApiOperation(value = "客户查询", notes = "客户查询（分页、条件）")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query", value = "页数 (0..N)"),
            @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query", value = "每页大小."),
            @ApiImplicitParam(name = "sort", dataType = "string", paramType = "query", value = "依据什么排序: 属性名(,asc|desc). ", allowMultiple = true)
    })
    public ResponseEntity<Page<CaseInfo>> getPersonalCaseInfo(@QuerydslPredicate(root = CaseInfo.class) Predicate predicate,
                                                              @ApiIgnore Pageable pageable,
                                                              @RequestHeader(value = "X-UserToken") String token,
                                                              @RequestParam(required = false) @ApiParam(value = "公司code码") String companyCode) {
        try {
            User tokenUser = getUserByToken(token);
            BooleanBuilder builder = new BooleanBuilder(predicate);
            if (Objects.isNull(tokenUser.getCompanyCode())) {
                //超级管理员
                if (Objects.nonNull(companyCode)) {
                    builder.and(QCaseInfo.caseInfo.companyCode.eq(companyCode));
                }
            } else {
                //不是超级管理员
                builder.and(QCaseInfo.caseInfo.companyCode.eq(tokenUser.getCompanyCode())); //限制公司code码
            }
            Page<CaseInfo> page = caseInfoRepository.findAll(builder, pageable);
            HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/PersonalController/getPersonalCaseInfo");
            return new ResponseEntity<>(page, headers, HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("", ENTITY_NAME, "查询失败")).body(null);
        }
    }

    /**
     * @Description 查询案件流转记录
     */
    @GetMapping("/getCaseTurnRecord")
    @ApiOperation(value = "查询案件流转记录", notes = "查询案件流转记录")
    public ResponseEntity<List<CaseTurnRecord>> getCaseTurnRecord(@RequestParam("caseNumber") @ApiParam(value = "案件编号", required = true) String caseNumber,
                                                                  @RequestHeader(value = "X-UserToken") String token) {
        log.debug("REST request to get case turn record by {caseNumber}", caseNumber);
        try {
            User tokenUser = getUserByToken(token);
            OrderSpecifier<Integer> sortOrder = QCaseTurnRecord.caseTurnRecord.id.asc();
            QCaseTurnRecord qCaseTurnRecord = QCaseTurnRecord.caseTurnRecord;
            BooleanBuilder booleanBuilder = new BooleanBuilder();
            booleanBuilder.and(qCaseTurnRecord.caseNumber.eq(caseNumber));
            booleanBuilder.and(qCaseTurnRecord.operatorUserName.ne("administrator"));
            Iterable<CaseTurnRecord> caseTurnRecords = caseTurnRecordRepository.findAll(booleanBuilder, sortOrder);
            List<CaseTurnRecord> caseTurnRecord = IterableUtils.toList(caseTurnRecords);
            List<CaseTurnRecord> caseTurnRecordList = new ArrayList<>();
            //过滤掉接收部门为为空的数据
            for (int i = 0; i < caseTurnRecord.size(); i++) {
                if (Objects.nonNull(caseTurnRecord.get(i).getReceiveDeptName()) && Objects.nonNull(caseTurnRecord.get(i).getReceiveUserRealName())){
                    caseTurnRecordList.add(caseTurnRecord.get(i));
                }
            }
//            caseTurnRecordList.forEach(e -> {
//                if (Objects.isNull(e.getReceiveDeptName()) && Objects.isNull(e.getReceiveUserRealName())) {
//                    e.setReceiveDeptName(e.getTurnToPool().toString());
//                    e.setReceiveUserRealName("无");
//                } else {
//                    if (Objects.isNull(e.getReceiveDeptName())) {
//                        e.setReceiveDeptName("未知");
//                    }
//                    if (Objects.isNull(e.getReceiveUserRealName())) {
//                        e.setReceiveUserRealName("未知");
//                    }
//                    caseTurnRecordList.remove(e);
//                }
//            });
            if (caseTurnRecordList.isEmpty()) {
                return ResponseEntity.ok().headers(HeaderUtil.createAlert("该案件跟进记录为空", ENTITY_CASE_TURN_RECORD)).body(new ArrayList<>());
            } else {
                return ResponseEntity.ok().headers(HeaderUtil.createAlert("查询成功", ENTITY_CASE_TURN_RECORD)).body(caseTurnRecordList);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_CASE_TURN_RECORD, "caseTurnRecord", "查询失败")).body(null);
        }
    }

    @GetMapping("/getMapInfo")
    @ApiOperation(value = "查询客户地图", notes = "查询客户地图")
    public ResponseEntity<MapModel> getMapInfo(@RequestParam @ApiParam(value = "客户地址", required = true) String address) {
        try {
            MapModel model = accMapService.getAddLngLat(address);
            return ResponseEntity.ok().headers(HeaderUtil.createAlert("查询成功", null)).body(model);
        } catch (Exception e) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("RepairCaseDistributeController", "error", e.getMessage())).body(null);
        }

    }

}
