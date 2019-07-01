package cn.fintecher.pangolin.business.service;

import cn.fintecher.pangolin.business.model.OutsourceFollowUpRecordModel;
import cn.fintecher.pangolin.business.model.OutsourceUpRepairModel;
import cn.fintecher.pangolin.business.model.ProductImport;
import cn.fintecher.pangolin.business.repository.*;
import cn.fintecher.pangolin.entity.*;
import cn.fintecher.pangolin.entity.file.UploadFile;
import cn.fintecher.pangolin.entity.util.*;
import cn.fintecher.pangolin.enums.*;
import cn.fintecher.pangolin.util.ZWDateUtil;
import cn.fintecher.pangolin.util.ZWStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Sheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;


/**
 * Created by LQ on 2017/6/5.
 */
@Service("accFinanceEntryService")
public class AccFinanceEntryService {
    private final Logger logger = LoggerFactory.getLogger(AccFinanceEntryService.class);
    @Autowired
    AccFinanceEntryRepository accFinanceEntryRepository;
    @Autowired
    OutsourcePoolRepository outsourcePoolRepository;
    @Autowired
    CaseFollowupRecordRepository caseFollowupRecordRepository;
    @Autowired
    CaseInfoRepository caseInfoRepository;

   @Autowired
   private  CaseRepairRecordRepository  caseRepairRecordRepository;

 @Autowired
 private   CaseInfoRemarkRepository  caseInfoRemarkRepository;

    @Autowired
    private  CaseRepairApplyRepository  caseRepairApplyRepository;

    @Autowired
    private  CaseRepairRepository  caseRepairRepository;

    @Autowired
    private   ProductRepository  productRepository;


    private final static List<String> isNeedTitle = new ArrayList<>();
    private final static List<String> isNeedTitle_Outsource = new ArrayList<>();
    private final static List<String> isNeedTitle_Product = new ArrayList<>();
    private final static List<String> batch_repair = new ArrayList<>();

    static {
        isNeedTitle_Product.add("product_id");
        isNeedTitle_Product.add("product_no");
        isNeedTitle_Product.add("产品名称");
        isNeedTitle_Product.add("催收模式");
        isNeedTitle_Product.add("渠道类型");
        isNeedTitle_Product.add("显示名称");
    }

    static{
        batch_repair.add("借据号");
        batch_repair.add("客户姓名");
        batch_repair.add("身份证号");
        batch_repair.add("手机号");
        batch_repair.add("修复手机号");
        batch_repair.add("修复地址");
        batch_repair.add("备注");
    }

    static {
        isNeedTitle.add("序号");
        isNeedTitle.add("案件编号");
        isNeedTitle.add("客户姓名");
        isNeedTitle.add("身份证号");
        isNeedTitle.add("案件金额");
        isNeedTitle.add("已还款金额");
    }

    static {
        isNeedTitle_Outsource.add("借据号");
        isNeedTitle_Outsource.add("跟进时间");
//        isNeedTitle_Outsource.add("跟进方式");//无
        isNeedTitle_Outsource.add("催记来源");
        isNeedTitle_Outsource.add("催收对象");
        isNeedTitle_Outsource.add("催收类型");
        isNeedTitle_Outsource.add("姓名");
        //电话/地址
        isNeedTitle_Outsource.add("电话/地址");
//        isNeedTitle_Outsource.add("电话状态");
        isNeedTitle_Outsource.add("催收反馈");
        isNeedTitle_Outsource.add("跟进内容");
        isNeedTitle_Outsource.add("受托方");
    }

    @Autowired
    RestTemplate restTemplate;
    @Autowired
    ParseExcelService parseExcelService;
    @Autowired
    PersonalRepository personalRepository;

    public List<CellError> importAccFinanceData(String fileId, Integer type, User user,String fienRemark) throws Exception {
        List<CellError> errorList = null;
        //获取上传的文件
        ResponseEntity<UploadFile> fileResponseEntity = null;
        try {
            try {
                fileResponseEntity = restTemplate.getForEntity(Constants.FILEID_SERVICE_URL.concat("uploadFile/getUploadFile/").concat(fileId), UploadFile.class);
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                throw new Exception("获取上传文件失败");
            }
            UploadFile file = fileResponseEntity.getBody();
            if (Objects.isNull(file)) {
                throw new Exception("获取Excel数据失败");
            }
            //判断文件类型是否为Excel
            if (!Constants.EXCEL_TYPE_XLS.equals(file.getType()) && !Constants.EXCEL_TYPE_XLSX.equals(file.getType())) {
                throw new Exception("数据文件为非Excel数据");
            }
            Sheet excelSheet = null;
            try {
                excelSheet = parseExcelService.getExcelSheets(file);
                if (Objects.isNull(excelSheet)) {
                    throw new RuntimeException("获取Excel对象错误");
                }
            } catch (Exception e) {
                throw new RuntimeException("获取Excel对象错误");
            }

            try {
                if (type == 0) {
                    parseExcelService.checkExcelHeader(excelSheet, 0, 0, isNeedTitle);
                } else if (type == 1){
                    parseExcelService.checkExcelHeader(excelSheet, 0, 0, isNeedTitle_Outsource);
                }else if(type == 2){
                    parseExcelService.checkExcelHeader(excelSheet, 0, 0, batch_repair);
                } else if (type == 769882154){
                    parseExcelService.checkExcelHeader(excelSheet, 0, 0, isNeedTitle_Product);
                }
            } catch (Exception e) {
                logger.error(e.getMessage());
                throw new RuntimeException(e.getMessage());
            }

            int[] startRow = {0};
            int[] startCol = {0};
            ExcelSheetObj excelSheetObj = null;
            if (type == 0) {
                Class<?>[] dataClass = {AccFinanceDataExcel.class};
                //从文件服务器上获取Excel文件并解析：
                excelSheetObj = ExcelUtil.parseExcelSingle(file.getUrl(), file.getType(), dataClass, startRow, startCol);
            } else if (type == 1) {
                Class<?>[] dataClass = {OutsourceFollowUpRecordModel.class};
                //解析Excel并保存到临时表中
                excelSheetObj = ExcelUtil.parseExcelSingle(file.getUrl(), file.getType(), dataClass, startRow, startCol);
            } else if (type == 769882154) {
                Class<?>[] dataClass = {ProductImport.class};
                //解析Excel并保存到临时表中
                excelSheetObj = ExcelUtil.parseExcelSingle(file.getUrl(), file.getType(), dataClass, startRow, startCol);
            }else if(type == 2){
                Class<?>[] dataClass = {OutsourceUpRepairModel.class};
                excelSheetObj = ExcelUtil.parseExcelSingle(file.getUrl(), file.getType(), dataClass, startRow, startCol);
            }

            List dataList = null;
            if(excelSheetObj != null){
                dataList = excelSheetObj.getDatasList();
            }else{
                throw new RuntimeException("数据不能为空!");
            }
            AccFinanceEntry accFinanceEntry = new AccFinanceEntry();
            CaseFollowupRecord outsourceFollowRecord = new CaseFollowupRecord();
            CaseRepairRecord repairRecord = new CaseRepairRecord();
            accFinanceEntry.setFileId(fileId);
            repairRecord.setFileId(fileId);
            //repairRecord.setRepairMemo(fienRemark);
            accFinanceEntry.setFienRemark(fienRemark);
            if (Objects.nonNull(user.getCompanyCode())) {
                accFinanceEntry.setCompanyCode(user.getCompanyCode());
                outsourceFollowRecord.setCompanyCode(user.getCompanyCode());
            }
            //导入错误信息
            errorList = excelSheetObj.getCellErrorList();
            if (errorList.isEmpty()) {
                if (type == 0) {
                    errorList = processFinanceData(dataList, accFinanceEntry, errorList, user);
                } else if (type == 1){
                    errorList = processFinanceDataFollowup(dataList, outsourceFollowRecord, errorList, user);
                }else if(type == 2){
                    errorList = processBatchRepair(dataList, repairRecord, errorList, user,file);
                }else if (type == 769882154){
                    for (Object o : dataList) {
                        ProductImport productImport = (ProductImport) o;
                        Product product = new Product();
                        product.setHyProductId(productImport.getId());
                        product.setProductCode(productImport.getProductCode());
                        product.setProductName(productImport.getProductName());
                        product.setOperator("402883d46300c836016300e57d6c002e");
                        product.setOperatorTime(new Date());
                        product.setCompanyCode(productImport.getProductSeriesName());
                        Product save = productRepository.save(product);
                    }
                }


            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException("上传失败!");

        }
        return errorList;
    }

    /**
     * 解析委外对账
     */
    private List<CellError> processFinanceData(List datalist, AccFinanceEntry accFinanceEntry, List<CellError> errorList, User user) {

        List<AccFinanceEntry> list = new ArrayList<>();
        for (int i =0; i<datalist.size();i++) {
            AccFinanceEntry afe = new AccFinanceEntry();
            AccFinanceDataExcel accFinanceDataExcel = (AccFinanceDataExcel) datalist.get(i);
            afe.setFileId(accFinanceEntry.getFileId());

            QOutsourcePool qOutsourcePool = QOutsourcePool.outsourcePool;
            CaseInfo caseInfo = null;
            Personal personal = null;
            //判断案件编号是否为空
            if (Objects.nonNull(accFinanceDataExcel.getCaseNum())) {
                caseInfo = caseInfoRepository.findOne(QCaseInfo.caseInfo.caseNumber.eq(accFinanceDataExcel.getCaseNum()));
            } else {
                errorList.add(new CellError("", i + 1, 1, "", "", "案件编号不能为空", null));
                return errorList;
            }
            //验证必要数据的合法性
            validityFinance(i, errorList, accFinanceDataExcel);
            if(errorList.size()!=0){
                return errorList;
            }
            //判断案件编号是否存在
            if (Objects.nonNull(caseInfo)) {
                if (caseInfo.getRecoverRemark() == 1) {
                    errorList.add(new CellError("", i + 1, 0, "", "", "回收案件不允许导入委外账目!", null));
                    return errorList;
                }
                personal = personalRepository.findOne(caseInfo.getPersonalInfo().getId());
                OutsourcePool outsourcePool = outsourcePoolRepository.findOne(qOutsourcePool.caseInfo.eq(caseInfo));
                if (Objects.nonNull(outsourcePool)) {
                    afe.setFienCasenum(accFinanceDataExcel.getCaseNum());
                    afe.setFienBatchnum(outsourcePool.getOutBatch());
                    if (Objects.nonNull(outsourcePool.getOutsource())) {
                        afe.setFienFgname(outsourcePool.getOutsource().getOutsName());
                    }
                    if(outsourcePool.getOutStatus().equals(OutsourcePool.OutStatus.TO_OUTSIDE.getCode())){
                        errorList.add(new CellError("", i + 1, 0, "", "", "委外分配案件(".concat(accFinanceDataExcel.getCaseNum()).concat(")无法导入委外账目！"), null));
                        return errorList;
                    }
                    //验证导入数据是否匹配
                    validityFinanceDate(errorList, accFinanceDataExcel, personal, outsourcePool);
                    if(errorList.size()!=0){
                        return errorList;
                    }
                } else {
                    errorList.add(new CellError("", i + 1, 0, "", "", "案件编号(".concat(accFinanceDataExcel.getCaseNum()).concat(")非委外案件编号！"), null));
                    return errorList;
                }

            } else {
                errorList.add(new CellError("",  i+1, 1, "", "", "案件编号不存在", null));
                return errorList;
            }

            afe.setFienCustname(accFinanceDataExcel.getCustName());
            afe.setFienIdcard(accFinanceDataExcel.getIdCardNumber());
            afe.setFienCount(BigDecimal.valueOf(accFinanceDataExcel.getCaseAmount()));
            afe.setFienPayback(BigDecimal.valueOf(accFinanceDataExcel.getPayAmount()));
            afe.setFienStatus(Status.Enable.getValue());
            afe.setFienRemark(accFinanceEntry.getFienRemark());
            afe.setCreator(user.getUserName());
            afe.setCreateTime(ZWDateUtil.getNowDateTime());
            afe.setCompanyCode(accFinanceEntry.getCompanyCode());
            list.add(afe);
        }
        if (errorList.size() == 0) {
            accFinanceEntryRepository.save(list);
        }
        return errorList;
    }
    /**
     *
     * 验证数据是否与原案件相同
     *
     * */
    private void validityFinanceDate(List<CellError> errorList, AccFinanceDataExcel accFinanceDataExcel, Personal personal, OutsourcePool outsourcePool) {

        if (!personal.getName().equals(accFinanceDataExcel.getCustName())) {
            CellError cellError = new CellError();
            cellError.setErrorMsg("客户姓名(".concat(accFinanceDataExcel.getCustName()).concat(")与原案件不匹配"));
            errorList.add(cellError);
        }

        if (!personal.getIdCard().equals(accFinanceDataExcel.getIdCardNumber())) {
            CellError cellError = new CellError();
            cellError.setErrorMsg("客户身份证号(".concat(accFinanceDataExcel.getIdCardNumber()).concat(")与原案件不匹配"));
            errorList.add(cellError);
        }

        if (Objects.nonNull(outsourcePool.getContractAmt())) {
            if (outsourcePool.getContractAmt().compareTo(BigDecimal.valueOf(accFinanceDataExcel.getCaseAmount())) != 0) {
                CellError cellError = new CellError();
                cellError.setErrorMsg("金额(".concat(accFinanceDataExcel.getCaseAmount().toString()).concat(")与原案件不匹配"));
                errorList.add(cellError);
            }
        }

    }

    /**
     * 验证数据是否为空
     */
    private void validityFinance(Integer i, List<CellError> errorList, AccFinanceDataExcel afe) {
        if (ZWStringUtils.isEmpty(afe.getCustName())) {
            CellError cellError = new CellError();
            cellError.setErrorMsg("第" + (i + 1) + "行客户姓名为空");
            errorList.add(cellError);
        }
        if (StringUtils.isBlank(afe.getIdCardNumber())) {
            CellError cellError = new CellError();
            cellError.setErrorMsg("第" + (i + 1) + "行客户的身份证号为空");
            errorList.add(cellError);
        } else {
            if (!IdcardUtils.validateCard(afe.getIdCardNumber())) {
                CellError cellError = new CellError();
                cellError.setErrorMsg(("第" + (i + 1) + "行客户的身份证号[").concat(afe.getIdCardNumber()).concat("]不合法"));
                errorList.add(cellError);
            }
        }
        if (StringUtils.isBlank(afe.getCaseAmount().toString())) {
            CellError cellError = new CellError();
            cellError.setErrorMsg("第" + (i + 1) + "行客户的金额为空");
            errorList.add(cellError);
        } else {
            if (("-").equals(afe.getCaseAmount().toString().substring(0, 1))) {
                CellError cellError = new CellError();
                cellError.setErrorMsg("第" + (i + 1) + "行客户的金额不能为负数");
                errorList.add(cellError);
            }
        }
        if (StringUtils.isBlank(afe.getPayAmount().toString())) {
            CellError cellError = new CellError();
            cellError.setErrorMsg("第" + (i + 1) + "行客户的已还款为空");
            errorList.add(cellError);
        } else {
            if (("-").equals(afe.getPayAmount().toString().substring(0, 1))) {
                CellError cellError = new CellError();
                cellError.setErrorMsg("第" + (i + 1) + "行客户的已还款金额不能为负数");
                errorList.add(cellError);
            }
        }
    }

    /**
     * Created by huyanmin 2017/9/26
     * 将Excel中的数据存入数据库中
     */
    public List<CellError> processFinanceDataFollowup(List datalist, CaseFollowupRecord outsourceFollowRecord, List<CellError> errorList, User user) {

        List<CaseFollowupRecord> outList = new ArrayList<>();
        for (int m = 0; m < datalist.size(); m++) {
            CaseFollowupRecord out = new CaseFollowupRecord();
            OutsourceFollowUpRecordModel followUpRecordModel = (OutsourceFollowUpRecordModel) datalist.get(m);
            CaseInfo caseInfo = null;
            OutsourcePool outsourcePool = null;
            if (Objects.nonNull(followUpRecordModel.getCaseNum())) {
               // out.setCaseNumber(followUpRecordModel.getCaseNum());  //  原来的code
                caseInfo = caseInfoRepository.findOne(QCaseInfo.caseInfo.loanInvoiceNumber.eq(followUpRecordModel.getCaseNum()));
                out.setCaseNumber(caseInfo.getCaseNumber());//  我修改后
                if (Objects.nonNull(caseInfo)) {
                    if (caseInfo.getRecoverRemark() == 1) {
                        errorList.add(new CellError("", 1, 0, "", "", "回收案件不允许导入委外账目", null));
                        return errorList;
                    }
                    outsourcePool = outsourcePoolRepository.findOne(QOutsourcePool.outsourcePool.caseInfo.eq(caseInfo));
                    if (Objects.nonNull(outsourcePool)) {
                        out.setCaseId(caseInfo.getId());
                        out.setOutFollowupBack(followUpRecordModel.getFeedback());
                    } else {
                        errorList.add(new CellError("", m + 1, 1, "", "", "借据号(".concat(followUpRecordModel.getCaseNum()).concat(")非委外案件编号！"), null));
                        return errorList;
                    }
                }else {
                    errorList.add(new CellError("",  1, 1, "", "", "借据号(".concat(followUpRecordModel.getCaseNum()).concat(")未找到相应的案件"), null));
                    return errorList;
                }
                //案件是否是委外案件
            } else {
                errorList.add(new CellError("", m + 1, 1, "", "", "借据号不能为空!", null));
                return errorList;
            }
            out.setPersonalId(caseInfo.getPersonalInfo().getId());
            CaseFollowupRecord.Type[] followTypes = CaseFollowupRecord.Type.values();//跟进方式
            Integer followtype = null;
            for (int i = 0; i < followTypes.length; i++) {
                if (Objects.nonNull(followUpRecordModel.getFollowType())) {
                    if (followTypes[i].getRemark().equals(followUpRecordModel.getFollowType())) {
                        followtype = followTypes[i].getValue();
                    }
                }
            }
            out.setType(followtype);
            if (Objects.nonNull(outsourceFollowRecord.getCompanyCode())) {
                out.setCompanyCode(outsourceFollowRecord.getCompanyCode());
            }
            out.setFollowTime(followUpRecordModel.getFollowTime());
            out.setSource(followUpRecordModel.getSource());//  催收来源
            out.setFollowPerson(followUpRecordModel.getFollowPerson());
            CaseFollowupRecord.Target target = CaseFollowupRecord.Target.getEnumByRemark(followUpRecordModel.getObjectName());
            if (target != null){
                out.setTarget(target.getValue());
            }
            out.setTargetName(followUpRecordModel.getUserName());
            EffectiveCollection[] feedBacks = EffectiveCollection.values();//有效催收反馈
            Integer feedBack = 0;
            for (int i = 0; i < feedBacks.length; i++) {
                if (Objects.nonNull(followUpRecordModel.getFeedback())) {
                    if (feedBacks[i].getRemark().equals(followUpRecordModel.getFeedback())) {
                        feedBack = feedBacks[i].getValue();
                    }
                }
            }
            EInvalidCollection[] InvalidFeedBacks = EInvalidCollection.values();//无效催收反馈
            for (int i = 0; i < InvalidFeedBacks.length; i++) {
                if (Objects.nonNull(followUpRecordModel.getFeedback())) {
                    if (InvalidFeedBacks[i].getRemark().equals(followUpRecordModel.getFeedback())) {
                        feedBack = InvalidFeedBacks[i].getValue();
                    }
                }
            }
            EffectiveCollection effectiveCollection = EffectiveCollection.getEnumByRemark(followUpRecordModel.getFeedback());
            if (effectiveCollection != null){
                feedBack = effectiveCollection.getValue();
            }else {
                EffectiveCollectionTel effectiveCollectionTel = EffectiveCollectionTel.getEnumByRemark(followUpRecordModel.getFeedback());
                if (effectiveCollectionTel != null){
                    feedBack = effectiveCollectionTel.getValue();
                }else {
                    EInvalidCollection eInvalidCollection = EInvalidCollection.getEnumByRemark(followUpRecordModel.getFeedback());
                    if (eInvalidCollection != null){
                        feedBack = eInvalidCollection.getValue();
                    }else {
                        EInvalidCollectionTel eInvalidCollectionTel = EInvalidCollectionTel.getEnumByRemark(followUpRecordModel.getFeedback());
                        if (eInvalidCollectionTel != null){
                            feedBack = eInvalidCollectionTel.getValue();
                        }
                    }
                }
            }
            out.setCollectionFeedback(feedBack);
            out.setContent(followUpRecordModel.getFollowRecord());
            CaseFollowupRecord.ContactState[] telStatusList = CaseFollowupRecord.ContactState.values();//电话状态
            Integer telStatus = null;
            for (int i = 0; i < telStatusList.length; i++) {
                if (Objects.nonNull(followUpRecordModel.getTelStatus())) {
                    if (telStatusList[i].getRemark().equals(followUpRecordModel.getTelStatus())) {
                        telStatus = telStatusList[i].getValue();
                    }
                }
            }
            if (telStatus != null){
                out.setContactState(telStatus);
            }
            out.setSource(followUpRecordModel.getSource());
            out.setCollectionType(followUpRecordModel.getCollectionType());
            if (out.getCollectionType() == null){
                errorList.add(new CellError("", m + 1, 1, "", "", "催收类型不能为空!", null));
                return errorList;
            }
            if (out.getCollectionType().equals(ECollectionType.VISIT.getValue())){
                out.setDetail(followUpRecordModel.getContactPhoneOrAddress());
            }else if(out.getCollectionType().equals(ECollectionType.TEL.getValue())){
                out.setContactPhone(followUpRecordModel.getContactPhoneOrAddress());
            }
            followUpRecordModel.getContactPhoneOrAddress();
            out.setPrincipalName(followUpRecordModel.getPrincipalName());
            out.setOperatorName(user.getRealName());
            out.setOperator(user.getUserName());
            out.setOperatorTime(ZWDateUtil.getNowDateTime());
            out.setCompanyCode(outsourceFollowRecord.getCompanyCode());
            out.setCaseFollowupType(CaseFollowupRecord.CaseFollowupType.OUTER.getValue());
            outList.add(out);
        }
        if (errorList.size() == 0) {
            caseFollowupRecordRepository.save(outList);
        }
        return errorList;
    }


    /**
     * 批量案件修复业务
     * @param datalist
     * @param repairRecord
     * @param errorList
     * @param user
     * @param uploadFile
     * @return
     */
    public List<CellError> processBatchRepair(List datalist, CaseRepairRecord repairRecord, List<CellError> errorList, User user,UploadFile  uploadFile) {
        List<CaseRepairRecord> outList = new ArrayList<>();
        CaseInfo  caseInfo =null;
        CaseRepair caseRepair =null;
        CaseRepairApply repairApply =null;
        for (int j = 0; j < datalist.size(); j++) {
            OutsourceUpRepairModel repairModel = (OutsourceUpRepairModel) datalist.get(j);
            //判断案件编号是否为空
            if (Objects.nonNull(repairModel.getLoanInvoiceNumber())) {
                 caseInfo = caseInfoRepository.findOne(QCaseInfo.caseInfo.loanInvoiceNumber.eq(repairModel.getLoanInvoiceNumber()));
                if(Objects.isNull(caseInfo)){
                    errorList.add(new CellError("", j + 1, 1, "", "", "上传借据号存在有误.请核查后上传!", null));
                    return errorList;
                }
                 caseRepair = caseRepairRepository.findOne(QCaseRepair.caseRepair.loanInvoiceNumber.eq(repairModel.getLoanInvoiceNumber()).and(QCaseRepair.caseRepair.repairStatus.eq(187)));
                if( null == caseRepair){
                    errorList.add(new CellError("", j + 1, 1, "", "", "存在已修复案件,请核查后上传!", null));
                    continue;
                }
                 repairApply = caseRepairApplyRepository.findOne(caseRepair.getRepairApplyId());

            } else {
                errorList.add(new CellError("", j + 1, 1, "", "", "借据号不能为空或不存在此借据号", null));
                return errorList;
            }

            if(Objects.isNull(repairModel.getRepairAddress())){  // 验证上传的时候修复地址  或修复手机号必须有一个
                if (Objects.isNull( repairModel.getRepairNumber())){
                    errorList.add(new CellError("", j + 1, 1, "", "", "上传失败!修复手机号或修复地址需至少填写一项!", null));
                    return errorList;
                }
            }
            CaseRepairRecord caseRepairRecord = new CaseRepairRecord();
            caseRepairRecord.setFileId(repairRecord.getFileId());
            caseRepairRecord.setRepairMemo(repairRecord.getRepairMemo());
            caseRepairRecord.setLoanInvoiceNumber(repairModel.getLoanInvoiceNumber());//  借据号
            caseRepairRecord.setFileType(uploadFile.getType());
            caseRepairRecord.setFileUrl(uploadFile.getUrl());
            caseRepairRecord.setOperator(user.getRealName());
            caseRepairRecord.setOperatorTime(new Date());
            caseRepairRecord.setCaseId(caseRepair.getCaseId().getId());//  案件编号
            caseRepairRecord.setRepairFileId (caseRepair.getId());//  申请修复案件主键
         //   Personal personal = personalRepository.findOne(caseInfo.getPersonalInfo().getId());
         //   personal.setMobileNo(repairModel.getPhoneNumber());
         //   personal.setLocalHomeAddress(repairModel.getRepairAddress());
            caseRepair.setRepairStatus(188);
            caseRepair.setOperator(user);
            caseRepair.setOperatorTime(new Date());
            //  拼接数据
            String    phone  =   repairModel.getRepairNumber()==null?"":repairModel.getRepairNumber();// 信修电话
            String  address  =   repairModel.getRepairAddress()==null?"":repairModel.getRepairAddress();// 地址
            if(ZWStringUtils.isNotEmpty(phone) || ZWStringUtils.isNotEmpty(address))
            {
                String phones ="";
                String addresss ="";
                if("" != phone ){
                    phones= "手机号更新:" + phone;
                }
                if("" != address){
                    addresss="地址更新:" + address;
                }

                caseRepair.setRepairContent(phones + addresss);
                //  保存备注信息
              /*  CaseInfoRemark caseInfoRemark = new CaseInfoRemark();
                caseInfoRemark.setOperatorTime(ZWDateUtil.getNowDateTime());
                caseInfoRemark.setRemark(re);
                caseInfoRemark.setCaseId(caseRepair.getCaseId().getId());
                caseInfoRemark.setOperatorRealName(user.getRealName());
                caseInfoRemark.setOperatorUserName(user.getUserName());
                caseInfoRemark.setCompanyCode(user.getCompanyCode());
                caseInfoRemarkRepository.save(caseInfoRemark);*/
            }
            List<CaseRepairRecord> list = caseRepair.getCaseRepairRecordList();
           list.add(caseRepairRecordRepository.saveAndFlush(caseRepairRecord));
         //   personalRepository.save(personal);
            caseRepairRepository.save(caseRepair);
        }
        return  errorList;
    }
}