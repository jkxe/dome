package cn.fintecher.pangolin.business.web;

import cn.fintecher.pangolin.model.AccCaseInfoDisModel;
import cn.fintecher.pangolin.business.model.CaseRepairRequest;
import cn.fintecher.pangolin.business.repository.CaseInfoRemarkRepository;
import cn.fintecher.pangolin.business.repository.CaseRepairRecordRepository;
import cn.fintecher.pangolin.business.repository.CaseRepairRepository;
import cn.fintecher.pangolin.business.service.AccFinanceEntryService;
import cn.fintecher.pangolin.business.service.CaseInfoService;
import cn.fintecher.pangolin.business.service.ReminderService;
import cn.fintecher.pangolin.entity.*;
import cn.fintecher.pangolin.entity.file.UploadFile;
import cn.fintecher.pangolin.entity.message.SendReminderMessage;
import cn.fintecher.pangolin.entity.util.CellError;
import cn.fintecher.pangolin.entity.util.Constants;
import cn.fintecher.pangolin.util.ZWDateUtil;
import cn.fintecher.pangolin.util.ZWStringUtils;
import cn.fintecher.pangolin.web.HeaderUtil;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import springfox.documentation.annotations.ApiIgnore;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * Created by yuanyanting on 2017/8/8.
 * 案件修复的Controller
 */

@RestController
@RequestMapping("/api/caseRepairController")
@Api(value = "案件修复", description = "案件修复")
public class CaseRepairController extends BaseController {
    private final Logger log = LoggerFactory.getLogger(CaseRepairController.class);
    @Autowired
    private CaseRepairRepository caseRepairRepository;

    @Autowired
    private CaseRepairRecordRepository caseRepairRecordRepository;

    @Autowired
    private CaseInfoRemarkRepository caseInfoRemarkRepository;

    @Autowired
    private CaseInfoService caseInfoService;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ReminderService reminderService;

    @Autowired
    private AccFinanceEntryService  accFinanceEntryService;

    /**
     * @Description : 修改案件状态到修复完成
     */
    @PostMapping("/toRepair")
    @ApiOperation(value = "修改案件状态", notes = "修改案件状态")
    public ResponseEntity toRepair(@RequestBody CaseRepairRequest request,
                                   @RequestHeader(value = "X-UserToken") @ApiParam("操作者的Token") String token) {
        User userByToken;
        try {
            userByToken = getUserByToken(token);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(null, "User is not login", "用户未登录")).body(null);
        }
        try {
            // 获取文件的id集合
            List<String> fileIds = request.getFileIds();
            // 案件修复表的id
            CaseRepair caseRepair = caseRepairRepository.findOne(request.getId());
            // 待修复上传的文件集合
            List<CaseRepairRecord> caseRepairRecordList = caseRepair.getCaseRepairRecordList();
           if(  null != fileIds && fileIds.size() > 0){

               String fileId = "";
               for (String str : fileIds) {
                   fileId += str + ",";
               }
               ParameterizedTypeReference<List<UploadFile>> responseType = new ParameterizedTypeReference<List<UploadFile>>() {};
               ResponseEntity<List<UploadFile>> resp = restTemplate.exchange(Constants.FILEID_SERVICE_URL.concat("uploadFile/getAllUploadFileByIds/").concat(fileId.toString()),
                       HttpMethod.GET, null, responseType);
               List<UploadFile> uploadFiles = resp.getBody();
               for (int i = 0; i < uploadFiles.size(); i++) {
                   UploadFile uploadFile = uploadFiles.get(i);
                   CaseRepairRecord caseRepairRecord = new CaseRepairRecord();
                   caseRepairRecord.setFileId(fileIds.get(i));
                   caseRepairRecord.setCaseId(caseRepair.getCaseId().getId());
                   caseRepairRecord.setOperator(userByToken.getRealName());
                   caseRepairRecord.setOperatorTime(ZWDateUtil.getNowDateTime());
                   caseRepairRecord.setRepairMemo(request.getRepairMemo());
                   caseRepairRecord.setFileUrl(uploadFile.getUrl());
                   caseRepairRecord.setFileType(uploadFile.getType());
                   caseRepairRecord.setRepairFileId (caseRepair.getId());//  申请修复案件主键
                   caseRepairRecordList.add(caseRepairRecordRepository.saveAndFlush(caseRepairRecord));

               }
           }else{
               CaseRepairRecord caseRepairRecord = new CaseRepairRecord();
               caseRepairRecord.setFileId(null);
               caseRepairRecord.setCaseId(caseRepair.getCaseId().getId());
               caseRepairRecord.setOperator(userByToken.getRealName());
               caseRepairRecord.setOperatorTime(ZWDateUtil.getNowDateTime());
               caseRepairRecord.setRepairMemo(request.getRepairMemo());
               caseRepairRecord.setFileUrl(null);
               caseRepairRecord.setFileType(null);
               caseRepairRecord.setRepairFileId (caseRepair.getId());//  申请修复案件主键
               caseRepairRecordList.add(caseRepairRecordRepository.saveAndFlush(caseRepairRecord));
           }
            if (!Objects.equals(caseRepair.getRepairStatus(), CaseRepair.CaseRepairStatus.DISTRIBUTE.getValue())) {
                // 修改状态为已修复
                caseRepair.setRepairStatus(CaseRepair.CaseRepairStatus.REPAIRED.getValue());
            }
            // 设置操作时间为现在时间
            caseRepair.setOperatorTime(ZWDateUtil.getNowDateTime());
            caseRepair.setOperator(userByToken);
            caseRepair.setOperatorTime(new Date());
            caseRepair.setRepairContent(request.getRepairMemo());
            caseRepairRepository.save(caseRepair);
            //保存案件批注备注信息
           /* if (ZWStringUtils.isNotEmpty(request.getRepairMemo())) {
                CaseInfoRemark caseInfoRemark = new CaseInfoRemark();
                caseInfoRemark.setOperatorTime(ZWDateUtil.getNowDateTime());
                caseInfoRemark.setRemark(request.getRepairMemo());
                caseInfoRemark.setCaseId(caseRepair.getCaseId().getId());
                caseInfoRemark.setOperatorRealName(userByToken.getRealName());
                caseInfoRemark.setOperatorUserName(userByToken.getUserName());
                caseInfoRemark.setCompanyCode(userByToken.getCompanyCode());
                caseInfoRemarkRepository.save(caseInfoRemark);
            }*/
            //提醒消息
            SendReminderMessage sendReminderMessage = new SendReminderMessage();
            sendReminderMessage.setTitle("客户[" + caseRepair.getCaseId().getPersonalInfo().getName() + "]的信息已修复");
            sendReminderMessage.setType(ReminderType.REPAIRED);
            sendReminderMessage.setUserId(caseRepair.getOperator().getId());
            sendReminderMessage.setContent("修复说明:" + request.getRepairMemo());
            reminderService.sendReminder(sendReminderMessage);
            return ResponseEntity.ok().headers(HeaderUtil.createAlert("修改状态成功", "toRepair")).body(null);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("", "exception", "系统异常")).body(null);
        }
    }

    @RequestMapping(value = "/distributeRepairCase", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    @ApiOperation(value = "修复分配", notes = "修复分配")
    public ResponseEntity distributeRepairCase(@RequestBody AccCaseInfoDisModel accCaseInfoDisModel,
                                               @RequestHeader(value = "X-UserToken") @ApiParam("操作者的Token") String token) {
        try {
            User user = getUserByToken(token);
            caseInfoService.distributeRepairCase(accCaseInfoDisModel, user);
            return ResponseEntity.ok().headers(HeaderUtil.createEntityCreationAlert("操作成功", "RepairCaseDistributeController")).body(null);
        } catch (Exception e) {
            String msg = Objects.isNull(e.getMessage()) ? "系统异常" : e.getMessage();
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("RepairCaseDistributeController", "error", msg)).body(null);
        }
    }

    /**
     * @Description 待修复案件查询
     */
    @GetMapping("/getAllRepairingCase")
    @ApiOperation(value = "待修复案件查询", notes = "待修复案件查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query",
                    value = "页数 (0..N)"),
            @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query",
                    value = "每页大小."),
            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query",
                    value = "依据什么排序: 属性名(,asc|desc). ")
    })
    public ResponseEntity<Page<CaseRepair>> getAllRepairingCase(@QuerydslPredicate(root = CaseRepair.class) Predicate predicate,
                                                                @ApiIgnore Pageable pageable,
                                                                @RequestHeader(value = "X-UserToken") String token,
                                                                @RequestParam(required = false) @ApiParam(value = "公司code码") String companyCode) {
        User user;
        try {
            user = getUserByToken(token);
        } catch (Exception e) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(null, "Userexists", e.getMessage())).body(null);
        }
        BooleanBuilder builder = new BooleanBuilder(predicate);
        List<Integer> list = new ArrayList<>();
        list.add(CaseInfo.CollectionStatus.CASE_OVER.getValue());
        list.add(CaseInfo.CollectionStatus.CASE_OUT.getValue());
        builder.and(QCaseRepair.caseRepair.caseId.collectionStatus.notIn(list));
        List<Integer> list1 = new ArrayList<>();
        list1.add(CaseRepair.CaseRepairStatus.REPAIRING.getValue()); // 待修复
        list1.add(CaseRepair.CaseRepairStatus.REPAIRED.getValue()); // 已修复
        list1.add(CaseRepair.CaseRepairStatus.DISTRIBUTE.getValue()); // 已分配
        builder.and(QCaseRepair.caseRepair.repairStatus.in(list1));
        builder.and(QCaseRepair.caseRepair.caseId.recoverRemark.eq(CaseInfo.RecoverRemark.NOT_RECOVERED.getValue())); //只查未回收的案件
        if (Objects.isNull(user.getCompanyCode())) {
            if (Objects.nonNull(companyCode)) {
                builder.and(QCaseRepair.caseRepair.companyCode.eq(companyCode));
            }
        } else {
            builder.and(QCaseRepair.caseRepair.companyCode.eq(user.getCompanyCode()));
        }
        Page<CaseRepair> page = caseRepairRepository.findAll(builder, pageable);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityCreationAlert("操作成功", "RepairCaseDistributeController")).body(page);
    }

    /**
     * @Description 已修复案件查询
     */
    @GetMapping("/getAllRepairedCase")
    @ApiOperation(value = "已修复案件查询", notes = "已修复案件查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query",
                    value = "页数 (0..N)"),
            @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query",
                    value = "每页大小."),
            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query",
                    value = "依据什么排序: 属性名(,asc|desc). ")
    })
    public ResponseEntity<Page<CaseRepair>> getAllRepairedCase(@QuerydslPredicate(root = CaseRepair.class) Predicate predicate,
                                                               @ApiIgnore Pageable pageable,
                                                               @RequestHeader(value = "X-UserToken") String token,
                                                               @RequestParam(required = false) @ApiParam(value = "公司code码") String companyCode) {
        User user;
        try {
            user = getUserByToken(token);
        } catch (Exception e) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(null, "Userexists", e.getMessage())).body(null);
        }
        BooleanBuilder builder = new BooleanBuilder(predicate);
        if (Objects.isNull(user.getCompanyCode())) {
            if (Objects.nonNull(companyCode)) {
                builder.and(QCaseRepair.caseRepair.companyCode.eq(companyCode));
            }
        } else {
            builder.and(QCaseRepair.caseRepair.companyCode.eq(user.getCompanyCode()));
        }
        List<Integer> list = new ArrayList<>();
        list.add(CaseInfo.CollectionStatus.CASE_OVER.getValue());
        list.add(CaseInfo.CollectionStatus.CASE_OUT.getValue());
        builder.and(QCaseRepair.caseRepair.caseId.collectionStatus.notIn(list));
        builder.and(QCaseRepair.caseRepair.repairStatus.eq(CaseRepair.CaseRepairStatus.REPAIRED.getValue()));
        Page<CaseRepair> page = caseRepairRepository.findAll(builder, pageable);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityCreationAlert("操作成功", "RepairCaseDistributeController")).body(page);
    }

    /**
     * @Description 已分配案件查询
     */
    @GetMapping("/getAllDistributeCase")
    @ApiOperation(value = "已分配案件查询", notes = "已分配案件查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query",
                    value = "页数 (0..N)"),
            @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query",
                    value = "每页大小."),
            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query",
                    value = "依据什么排序: 属性名(,asc|desc). ")
    })
    public ResponseEntity<Page<CaseRepair>> getAllDistributeCase(@QuerydslPredicate(root = CaseRepair.class) Predicate predicate,
                                                                 @ApiIgnore Pageable pageable,
                                                                 @RequestHeader(value = "X-UserToken") String token,
                                                                 @RequestParam(required = false) @ApiParam(value = "公司code码") String companyCode) {
        User user = null;
        try {
            user = getUserByToken(token);
        } catch (Exception e) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(null, "Userexists", e.getMessage())).body(null);
        }
        BooleanBuilder builder = new BooleanBuilder(predicate);
        if (Objects.isNull(user.getCompanyCode())) {
            if (Objects.nonNull(companyCode)) {
                builder.and(QCaseRepair.caseRepair.companyCode.eq(companyCode));
            }
        } else {
            builder.and(QCaseRepair.caseRepair.companyCode.eq(user.getCompanyCode()));
        }
        builder.and(QCaseRepair.caseRepair.repairStatus.eq(CaseRepair.CaseRepairStatus.DISTRIBUTE.getValue()));
        Page<CaseRepair> page = caseRepairRepository.findAll(builder, pageable);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityCreationAlert("操作成功", "RepairCaseDistributeController")).body(page);
    }

    /**
     * @Description ：查看已修复案件信息
     */
    @GetMapping("/viewCaseRepair")
    @ApiOperation(value = "查看已修复案件信息", notes = "查看已修复案件信息")
    public ResponseEntity<List<CaseRepairRecord>> viewCaseRepair(String id) {
        try {
            CaseRepair caseRepair = caseRepairRepository.findOne(QCaseRepair.caseRepair.repairStatus.eq(188).and(QCaseRepair.caseRepair.id.eq(id)));
            List<CaseRepairRecord> caseRepairRecordList = new ArrayList<>();
            if (Objects.nonNull(caseRepair)) {
                caseRepairRecordList = caseRepair.getCaseRepairRecordList();
            }
            return ResponseEntity.ok().headers(HeaderUtil.createAlert("查看信息成功", "CaseRepairController")).body(caseRepairRecordList);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("", "exception", "系统异常")).body(null);
        }
    }

    /**
     * @Description ：修复附件查看
     */
    @GetMapping("/viewCaseRepairRecord")
    @ApiOperation(value = "修复附件查看", notes = "修复附件查看")
    public ResponseEntity<List<CaseRepairRecord>> viewCaseRepairRecord(String id) {
        try {
            Iterable<CaseRepairRecord> caseRepairRecordList = caseRepairRecordRepository.findAll(QCaseRepairRecord.caseRepairRecord.caseId.eq(id));
            List<CaseRepairRecord> list = new ArrayList<>();
            caseRepairRecordList.forEach(single -> list.add(single));
            return ResponseEntity.ok().headers(HeaderUtil.createAlert("查看信息成功", "CaseRepairController")).body(list);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("", "exception", "系统异常")).body(null);
        }
    }



    /**
     *
     * 批量修复案件
     *
     */

    @GetMapping("/batchImportRepairData")
    @ResponseBody
    @ApiOperation(value = "批量上传修复案件", notes = "批量上传修复案件")
    public ResponseEntity<List<CellError>> batchImportRepairData(@RequestHeader(value = "X-UserToken") @ApiParam("操作者的Token") String token,
                                                                 @RequestParam(required = false) @ApiParam(value = "文件ID") String fileId,
                                                                 @RequestParam(required = false) @ApiParam(value = "备注") String fienRemark
    ) {
        try {
            User user = getUserByToken(token);
            if (Objects.isNull(user)) {
                return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("获取不到登录人信息", "", "获取不到登录人信息")).body(null);
            }
            //  自定义类型为2-批量修复案件,为了共用service
            List<CellError> errorList = accFinanceEntryService.importAccFinanceData(fileId,2 , user,fienRemark);
            if (errorList.isEmpty()) {
                return ResponseEntity.ok().body(null);
            } else {
                String errorMsg =null;
                if(null != errorList&&errorList.size()>0)
                {
                    errorMsg = errorList.get(0).getErrorMsg();
                }
                return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("Excel数据有误", "",errorMsg)).body(null);

            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
                return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("导入失败", "", e.getMessage())).body(null);
        }

    }



}
