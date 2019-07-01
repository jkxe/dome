package cn.fintecher.pangolin.business.service;

import cn.fintecher.pangolin.business.model.*;
import cn.fintecher.pangolin.business.repository.*;
import cn.fintecher.pangolin.entity.*;
import cn.fintecher.pangolin.entity.message.SendReminderMessage;
import cn.fintecher.pangolin.entity.strategy.CaseStrategy;
import cn.fintecher.pangolin.entity.util.Constants;
import cn.fintecher.pangolin.model.AccCaseInfoDisModel;
import cn.fintecher.pangolin.model.ManualParams;
import cn.fintecher.pangolin.util.ZWDateUtil;
import cn.fintecher.pangolin.util.ZWStringUtils;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.hsjry.lang.common.util.DateUtil;
import io.swagger.annotations.ApiModelProperty;
import org.kie.api.runtime.KieSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.temporal.ChronoUnit;
import java.util.*;

/**
 * @Author: PeiShouWen
 * @Description: 案件分配服务类
 * @Date 16:38 2017/8/7
 */
@Service("caseInfoDistributedService")
public class CaseInfoDistributedService {

    Logger logger = LoggerFactory.getLogger(CaseInfoDistributedService.class);

    @Inject
    private CaseInfoRepository caseInfoRepository;
    @Inject
    private CaseTurnRecordRepository caseTurnRecordRepository;
    @Inject
    private SysParamRepository sysParamRepository;
    @Inject
    private UserRepository userRepository;
    @Inject
    private RestTemplate restTemplate;
    @Autowired
    private CaseInfoDistributedRepository caseInfoDistributedRepository;
    @Autowired
    private CaseRepairRepository caseRepairRepository;
    @Inject
    private DepartmentRepository departmentRepository;
    @Inject
    private OutsourcePoolRepository outsourcePoolRepository;
    @Inject
    private CaseInfoService caseInfoService;
    @Inject
    private RunCaseStrategyService runCaseStrategyService;
    @Inject
    private EntityManager em;

    /**
     * 案件分配
     *
     * @param accCaseInfoDisModel
     * @param user
     * @throws Exception
     */
    @Transactional
    public void distributeCeaseInfo(AccCaseInfoDisModel accCaseInfoDisModel, User user) throws Exception {
        //案件列表
        List<CaseInfo> caseInfoObjList = new ArrayList<>();
        //流转记录列表
        List<CaseTurnRecord> caseTurnRecordList = new ArrayList<>();
        List<CaseRepair> caseRepairList = new ArrayList<>();
        //选择的案件ID列表
        List<String> caseInfoList = accCaseInfoDisModel.getCaseIdList();
        //被分配的案件ID列表
        List<String> caseInfoAlready = new ArrayList<>();
        //每个机构或人分配的数量
        List<Integer> disNumList = accCaseInfoDisModel.getCaseNumList();
        //已经分配的案件数量
        int alreadyCaseNum = 0;
        //接收案件列表信息
        List<String> deptOrUserList = null;
        //机构分配
        if (accCaseInfoDisModel.getDisType().equals(AccCaseInfoDisModel.DisType.DEPART_WAY.getValue())) {
            //所要分配 机构id
            deptOrUserList = accCaseInfoDisModel.getDepIdList();
        } else if (accCaseInfoDisModel.getDisType().equals(AccCaseInfoDisModel.DisType.USER_WAY.getValue())) {
            //得到所有用户ID
            deptOrUserList = accCaseInfoDisModel.getUserIdList();
        }
        for (int i = 0; i < (deptOrUserList != null ? deptOrUserList.size() : 0); i++) {
            //如果按机构分配则是机构的ID，如果是按用户分配则是用户ID
            String deptOrUserid = deptOrUserList.get(i);
            Department department = null;
            User targetUser = null;
            if (accCaseInfoDisModel.getDisType().equals(AccCaseInfoDisModel.DisType.DEPART_WAY.getValue())) {
                department = departmentRepository.findOne(deptOrUserid);
            } else if (accCaseInfoDisModel.getDisType().equals(AccCaseInfoDisModel.DisType.USER_WAY.getValue())) {
                targetUser = userRepository.findOne(deptOrUserid);
            }
            //需要分配的案件数据
            Integer disNum = disNumList.get(i);
            for (int j = 0; j < disNum; j++) {
                //检查输入的案件数量是否和选择的案件数量一致
                if (alreadyCaseNum > caseInfoList.size()) {
                    throw new Exception("选择的案件总量与实际输入的案件数量不匹配");
                }
                String caseId = caseInfoList.get(alreadyCaseNum);
                CaseInfoDistributed caseInfoDistributed = caseInfoDistributedRepository.findOne(caseId);
                if (Objects.nonNull(caseInfoDistributed)) {
                    CaseInfo caseInfo = new CaseInfo();
                    BeanUtils.copyProperties(caseInfoDistributed, caseInfo);
//                    caseInfo.setCaseType(CaseInfo.CaseType.DISTRIBUTE.getValue()); //案件类型-案件分配
                    if (Objects.nonNull(department)) {
                        caseInfo.setDepartment(department); //部门
                        try {
                            caseInfoService.setCollectionType(caseInfo, department, null);
                        } catch (Exception e) {
                            throw new RuntimeException(e.getMessage());
                        }
                        caseInfo.setCaseFollowInTime(ZWDateUtil.getNowDateTime()); //案件流入时间
                        caseInfo.setCollectionStatus(CaseInfo.CollectionStatus.WAIT_FOR_DIS.getValue()); //催收状态-待分配
                    }
                    if (targetUser != null) {
                        caseInfo.setDepartment(targetUser.getDepartment());
                        caseInfo.setCurrentCollector(targetUser);
                        try {
                            caseInfoService.setCollectionType(caseInfo, null, targetUser);
                        } catch (Exception e) {
                            throw new RuntimeException(e.getMessage());
                        }
                        caseInfo.setCaseFollowInTime(ZWDateUtil.getNowDateTime());
                        caseInfo.setCollectionStatus(CaseInfo.CollectionStatus.WAITCOLLECTION.getValue()); //催收状态-待催收
                    }
                    caseInfo.setLeaveCaseFlag(CaseInfo.leaveCaseFlagEnum.NO_LEAVE.getValue()); //留案标识默认-非留案
                    caseInfo.setAssistFlag(CaseInfo.AssistFlag.NO_ASSIST.getValue());
                    //案件剩余天数(结案日期-当前日期)
                    caseInfo.setLeftDays(ZWDateUtil.getBetween(ZWDateUtil.getNowDate(), caseInfo.getCloseDate(), ChronoUnit.DAYS));
                    //案件类型
//                    caseInfo.setCaseType(CaseInfo.CaseType.DISTRIBUTE.getValue());
                    caseInfo.setCaseMark(CaseInfo.Color.NO_COLOR.getValue());//打标标记
                    caseInfo.setFollowUpNum(0);//流转次数
                    caseInfo.setOperator(user);
                    caseInfo.setOperatorTime(ZWDateUtil.getNowDateTime());
                    //案件流转记录
                    CaseTurnRecord caseTurnRecord = new CaseTurnRecord();
                    BeanUtils.copyProperties(caseInfo, caseTurnRecord); //将案件信息复制到流转记录
                    caseTurnRecord.setId(null); //主键置空
                    caseTurnRecord.setCaseId(caseInfo.getId()); //案件ID
                    caseTurnRecord.setDepartId(caseInfo.getDepartment().getId()); //部门ID
                    if (Objects.nonNull(caseInfo.getCurrentCollector())) { //催收员不为空则是分给催收员
                        caseTurnRecord.setReceiveDeptName(caseInfo.getCurrentCollector().getDepartment().getName()); //接收部门名称
                        caseTurnRecord.setReceiveUserId(caseInfo.getCurrentCollector().getId()); //接收人ID
                        caseTurnRecord.setReceiveUserRealName(caseInfo.getCurrentCollector().getRealName()); //接受人名称
                    } else {
                        caseTurnRecord.setReceiveDeptName(caseInfo.getDepartment().getName());
                    }
                    caseTurnRecord.setCirculationType(3); //流转类型 3-正常流转
                    caseTurnRecord.setOperatorUserName(user.getUserName()); //操作员用户名
                    caseTurnRecord.setOperatorTime(ZWDateUtil.getNowDateTime()); //操作时间
                    caseTurnRecordList.add(caseTurnRecord);
                    //案件列表
                    caseInfoObjList.add(caseInfo);
                }
                if (caseInfoDistributed != null) caseInfoAlready.add(caseInfoDistributed.getId());
                alreadyCaseNum = alreadyCaseNum + 1;
            }
        }
        //保存案件信息
        caseInfoRepository.save(caseInfoObjList);
        //保存流转记录
        caseTurnRecordRepository.save(caseTurnRecordList);
        //保存修复信息
        caseRepairRepository.save(caseRepairList);
        //删除待分配案件
        for (String id : caseInfoAlready) {
            caseInfoDistributedRepository.delete(id);
        }
    }

    /**
     * 案件导入手工分案/待分配回收案件批量分配
     *
     * @param manualParams
     * @param user
     */
    public List<String>  manualAllocation(ManualParams manualParams, User user,boolean flag) {
        try {
            Iterable<CaseInfoDistributed> all = caseInfoDistributedRepository
                    .findAll(QCaseInfoDistributed.caseInfoDistributed.caseNumber.in(manualParams.getCaseNumberList()));
            Iterator<CaseInfoDistributed> iterator = all.iterator();
            List<CaseInfo> caseInfoList = new ArrayList<>();
            List<CaseRepair> caseRepairList = new ArrayList<>();
            List<OutsourcePool> outsourcePoolList = new ArrayList<>();
            List<CaseInfoRemark> caseInfoRemarkList = new ArrayList<>();
            List<CaseTurnRecord> caseTurnRecords = new ArrayList<>();
            Integer type = manualParams.getType();
            //内催
            if (Objects.equals(0, type)) {
                while (iterator.hasNext()) {
                    CaseInfoDistributed next = iterator.next();
                    next.setRecoverRemark(CaseInfo.RecoverRemark.NOT_RECOVERED.getValue());
                    CaseInfo caseInfo = new CaseInfo();
                    setCaseInfo(next, caseInfo, user, manualParams.getCloseDate());
                    caseInfo.setCasePoolType(CaseInfo.CasePoolType.INNER.getValue());
                    caseInfo.setExceptionFlag(0);// 修改异常状态
                    caseInfoList.add(caseInfo);
//                    addCaseRepair(caseRepairList, caseInfo, user);//修复池增加案件
                }
            }
            //委外
            if (Objects.equals(1, type)) {
                while (iterator.hasNext()) {
                    CaseInfoDistributed next = iterator.next();
                    next.setRecoverRemark(CaseInfo.RecoverRemark.NOT_RECOVERED.getValue());
                    CaseInfo caseInfo = new CaseInfo();
                    setCaseInfo(next, caseInfo, user, manualParams.getCloseDate());
                    caseInfo.setCasePoolType(CaseInfo.CasePoolType.OUTER.getValue());
                    caseInfo.setExceptionFlag(0);// 修改异常状态
                    caseInfoList.add(caseInfo);

                    OutsourcePool outsourcePool = new OutsourcePool();
                    outsourcePool.setCaseInfo(caseInfo);
                    outsourcePool.setCaseNumber(caseInfo.getCaseNumber());
                    outsourcePool.setCompanyCode(caseInfo.getCompanyCode());
                    outsourcePool.setOutStatus(OutsourcePool.OutStatus.TO_OUTSIDE.getCode());
                    //委外案件待分配时，系统根据对每个产品类型设置回收周期，默认生成案件的回收到期时间。
                    QSysParam qSysParam = QSysParam.sysParam;
                    SysParam sysParam = sysParamRepository.findOne(qSysParam.productSeriesId.eq(next.getProductType()));
                    int days = Integer.parseInt(sysParam.getValue());
                    Date after = ZWDateUtil.getAfter(new Date(), days, null);

                    outsourcePool.setOverOutsourceTime(after);
                    outsourcePool.setOverduePeriods(caseInfo.getPayStatus());
                    BigDecimal b2 = caseInfo.getRealPayAmount();//实际还款金额
                    if (Objects.isNull(b2)) {
                        b2 = BigDecimal.ZERO;
                    }
                    BigDecimal b1 = caseInfo.getOverdueAmount();//原案件金额
                    outsourcePool.setContractAmt(b1.subtract(b2));//委外案件金额=原案件金额-实际还款金额
                    outsourcePoolList.add(outsourcePool);
                }
            }
            List<CaseInfo> caseInfos = caseInfoRepository.save(caseInfoList);
            List<CaseInfo> infos = new ArrayList<>();
            List<String> caseNums = new ArrayList<>();
            for  ( int  i  =   0 ; i  <  caseInfos.size() ; i ++ ) {
                if (caseNums.contains(caseInfos.get(i).getCaseNumber())) {
                    continue;
                }
                caseNums.add(caseInfos.get(i).getCaseNumber());
                infos.add(caseInfos.get(i));
            }
            for (int i = 0; i < infos.size(); i++) {
                //分配完成新增流转记录
                CaseTurnRecord caseTurnRecord = new CaseTurnRecord();
                BeanUtils.copyProperties(infos.get(i), caseTurnRecord); //将案件信息复制到流转记录
                caseTurnRecord.setId(null); //主键置空
                caseTurnRecord.setCaseNumber(infos.get(i).getCaseNumber());
                caseTurnRecord.setCaseId(infos.get(i).getId());
                if (flag){
                    caseTurnRecord.setTriggerAction(CaseTurnRecord.triggerActionEnum.AUTO_DIVISION.getValue()); // 触发动作
                }else {
                    caseTurnRecord.setTriggerAction(CaseTurnRecord.triggerActionEnum.MANUAL_DIVISION.getValue()); // 触发动作
                }
                caseTurnRecord.setCirculationType(2); //流转类型 2-正常流转
                caseTurnRecord.setOperatorUserName(user.getUserName()); //操作员用户名
                caseTurnRecord.setOperatorTime(ZWDateUtil.getNowDateTime()); //操作时间
                caseTurnRecord.setTurnFromPool(CaseTurnRecord.TurnFromPool.CORE.getValue());
                caseTurnRecord.setTurnApprovalStatus(CaseTurnRecord.TurnApprovalStatus.PASS.getValue());
                if (infos.get(i).getCasePoolType().equals(CaseInfo.CasePoolType.INNER.getValue())){
                    caseTurnRecord.setTurnToPool(CaseTurnRecord.TurnToPool.INNER.getValue());
                }else {
                    caseTurnRecord.setTurnToPool(CaseTurnRecord.TurnToPool.OUTER.getValue());
                }
                caseTurnRecords.add(caseTurnRecord);
            }


            List<String> caseIds = new ArrayList<>();
            for (CaseInfo info : infos) {
                caseIds.add(info.getId());
            }
            caseTurnRecordRepository.save(caseTurnRecords);
//            caseRepairRepository.save(caseRepairList);
            outsourcePoolRepository.save(outsourcePoolList);
            caseInfoDistributedRepository.delete(all);
            return caseIds;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException("分配失败!");
        }
    }

    private void addCaseInfoRemark(List<CaseInfoRemark> caseInfoRemarkList, CaseInfo caseInfo, User user) {
        CaseInfoRemark caseInfoRemark = new CaseInfoRemark();
        caseInfoRemark.setCaseId(caseInfo.getId());
        caseInfoRemark.setRemark(caseInfo.getMemo());
        caseInfoRemark.setCompanyCode(caseInfo.getCompanyCode());
        caseInfoRemark.setOperatorRealName(user.getRealName());
        caseInfoRemark.setOperatorUserName(user.getUserName());
        caseInfoRemark.setOperatorTime(new Date());
        caseInfoRemarkList.add(caseInfoRemark);
    }

    private void setCaseInfo(CaseInfoDistributed caseInfoDistributed, CaseInfo caseInfo, User user, Date closeDate) {
        BeanUtils.copyProperties(caseInfoDistributed, caseInfo);
        caseInfo.setId(null);
//        caseInfo.setCaseType(CaseInfo.CaseType.DISTRIBUTE.getValue()); //案件类型-案件分配
        caseInfo.setCaseFollowInTime(ZWDateUtil.getNowDateTime()); //案件流入时间
        caseInfo.setCollectionStatus(CaseInfo.CollectionStatus.WAIT_FOR_DIS.getValue()); //催收状态-待分配
        caseInfo.setLeaveCaseFlag(CaseInfo.leaveCaseFlagEnum.NO_LEAVE.getValue()); //留案标识默认-非留案
        caseInfo.setAssistFlag(CaseInfo.AssistFlag.NO_ASSIST.getValue());
//        caseInfo.setLeftDays(ZWDateUtil.getBetween(ZWDateUtil.getNowDate(), caseInfo.getCloseDate(), ChronoUnit.DAYS));//案件剩余天数(结案日期-当前日期)
        caseInfo.setCaseMark(CaseInfo.Color.NO_COLOR.getValue());//打标标记
        caseInfo.setFollowUpNum(0);//流转次数
        caseInfo.setOperator(user);
        caseInfo.setOperatorTime(ZWDateUtil.getNowDateTime());
        if (Objects.nonNull(closeDate)) {
            caseInfo.setCloseDate(closeDate);
        }
    }

    private void addCaseRepair(List<CaseRepair> caseRepairList, CaseInfo caseInfo, User user) {
        CaseRepair caseRepair = new CaseRepair();
        caseRepair.setCaseId(caseInfo);
        caseRepair.setRepairStatus(CaseRepair.CaseRepairStatus.REPAIRING.getValue());
        caseRepair.setOperatorTime(ZWDateUtil.getNowDateTime());
        caseRepair.setOperator(user);
        caseRepair.setCompanyCode(caseInfo.getCompanyCode());
        caseRepairList.add(caseRepair);
    }

    /**
     * 消息失败提醒
     */
    public void reminderMessage(String userId, String title, String content) {
        //消息提醒
        try {
            SendReminderMessage sendReminderMessage = new SendReminderMessage();
            sendReminderMessage.setUserId(userId);
            sendReminderMessage.setTitle(title);
            sendReminderMessage.setContent(content);
            sendReminderMessage.setType(ReminderType.STRATEGY);
            sendReminder(sendReminderMessage);
        } catch (Exception e) {
            logger.error("策略消息提醒错误...");
        }
    }

    public void sendReminder(SendReminderMessage sendReminderMessage) {
        restTemplate.postForLocation("http://reminder-service/api/reminderMessages/receiveMessage", sendReminderMessage);
    }

    /**
     * 案件导入手工分案统计
     *
     * @param manualParams
     * @return
     */
    public AllocationCountModel allocationCount(ManualParams manualParams) {
        if (Objects.isNull(manualParams.getCaseNumberList()) || manualParams.getCaseNumberList().isEmpty()) {
            throw new RuntimeException("请先选择要分配的案件");
        }
        if (Objects.isNull(manualParams.getType())) {
            throw new RuntimeException("请选择要分给委外/内催");
        }
        try {
            List<Object[]> obj = caseInfoDistributedRepository.allocationCount(manualParams.getCaseNumberList());
            Object[] objects = obj.get(0);
            BigInteger caseTotal = (BigInteger) objects[0];
            BigDecimal caseAmount = (objects[1] == null) ? new BigDecimal(0) : (BigDecimal) objects[1];
            AllocationCountModel model = new AllocationCountModel();
            model.setCaseTotal(caseTotal);
            model.setCaseAmount(caseAmount);
            return model;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException("统计案件信息错误!");
        }
    }

    /**
     * 案件导入策略分案
     *
     * @param model 选择的案件
     * @param user  用户
     * @return
     */
    public void strategyAllocation(CountStrategyAllocationModel model, User user) {
        try {
            List<CountAllocationModel> modelList = model.getModelList();

            // 策略分配
            List<CaseInfoDistributed> caseInfoDistributedList = new ArrayList<>();
            // 分配到CaseInfo的案件
            List<CaseInfo> caseInfoList = new ArrayList<>();
            // 分配到内催时添加修复池也新增
            List<CaseRepair> caseRepairList = new ArrayList<>();
            // 分到委外时需要OutsourcePool新增
            List<OutsourcePool> outsourcePoolList = new ArrayList<>();
            for (CountAllocationModel aModel : modelList) {
                List<CaseInfoDistributed> all = caseInfoDistributedRepository.findAll(aModel.getIds());
                // 内催
                if (Objects.equals(aModel.getType(), 0)) {
                    for (CaseInfoDistributed caseInfoDistributed : all) {
                        CaseInfo caseInfo = new CaseInfo();
                        setCaseInfo(caseInfoDistributed, caseInfo, user, null);
                        caseInfo.setCasePoolType(CaseInfo.CasePoolType.INNER.getValue());
                        caseInfoList.add(caseInfo);
                        //修复池增加案件
                        addCaseRepair(caseRepairList, caseInfo, user);
                        caseInfoDistributedList.add(caseInfoDistributed);
                    }
                }
                // 委外
                if (Objects.equals(aModel.getType(), 1)) {
                    for (CaseInfoDistributed caseInfoDistributed : all) {
                        CaseInfo caseInfo = new CaseInfo();
                        setCaseInfo(caseInfoDistributed, caseInfo, user, null);
                        caseInfo.setCasePoolType(CaseInfo.CasePoolType.OUTER.getValue());
                        caseInfoList.add(caseInfo);
                        OutsourcePool outsourcePool = new OutsourcePool();
                        outsourcePool.setCaseInfo(caseInfo);
                        outsourcePool.setOutStatus(OutsourcePool.OutStatus.TO_OUTSIDE.getCode());
                        outsourcePool.setOverOutsourceTime(caseInfoDistributed.getCloseDate());
                        outsourcePool.setOverduePeriods(caseInfo.getPayStatus());
                        outsourcePool.setCompanyCode(caseInfo.getCompanyCode());
                        //实际还款金额
                        BigDecimal b2 = caseInfo.getRealPayAmount();
                        if (Objects.isNull(b2)) {
                            b2 = BigDecimal.ZERO;
                        }
                        //原案件金额
                        BigDecimal b1 = caseInfo.getOverdueAmount();
                        //委外案件金额=原案件金额-实际还款金额
                        outsourcePool.setContractAmt(b1.subtract(b2));
                        outsourcePoolList.add(outsourcePool);
                        caseInfoDistributedList.add(caseInfoDistributed);
                    }
                }
            }
            caseInfoRepository.save(caseInfoList);
            caseRepairRepository.save(caseRepairList);
            outsourcePoolRepository.save(outsourcePoolList);
            caseInfoDistributedRepository.delete(caseInfoDistributedList);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException("分配失败");
        }
    }


    /**
     * 统计策略分配情况
     *
     * @param all
     * @param user
     * @return
     */
    @Async
    public void countStrategyAllocation(List<CaseInfoDistributedModel> all, User user) {

        logger.info("异步分案结果开始{}", Thread.currentThread());
        ResponseEntity<List<CaseStrategy>> forEntity = null;
        try {
            ParameterizedTypeReference<List<CaseStrategy>> responseType = new ParameterizedTypeReference<List<CaseStrategy>>() {
            };
            forEntity = restTemplate.exchange(Constants.CASE_STRATEGY_URL
                    .concat("companyCode=").concat(user.getCompanyCode())
                    .concat("&strategyType=").concat(CaseStrategy.StrategyType.IMPORT.getValue().toString()), HttpMethod.GET, null, responseType);
        } catch (RestClientException e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException("获取策略错误");
        }
        List<CaseStrategy> caseStrategies = forEntity.getBody();
        if (caseStrategies.isEmpty()) {
            throw new RuntimeException("未找到需要执行的策略");
        }
        // 策略分配
        try {
            CountStrategyAllocationModel model = new CountStrategyAllocationModel();
            CountAllocationModel modelInner = new CountAllocationModel();
            modelInner.setType(0);
            CountAllocationModel modelOuter = new CountAllocationModel();
            modelOuter.setType(1);
            for (CaseStrategy caseStrategy : caseStrategies) {
                // 策略匹配到的案件
                List<CaseInfoDistributedModel> checkedList = new ArrayList<>();
                KieSession kieSession = null;
                try {
                    kieSession = runCaseStrategyService.runCaseRule(checkedList, caseStrategy, Constants.CASE_INFO_DISTRIBUTE_RULE);
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                    throw new RuntimeException(e.getMessage());
                }
                for (CaseInfoDistributedModel caseInfoDistributed : all) {
                    //插入
                    kieSession.insert(caseInfoDistributed);
                    //执行规则
                    kieSession.fireAllRules();
                }
                kieSession.dispose();
                if (checkedList.isEmpty()) {
                    continue;
                }
                // 内催
                if (Objects.equals(caseStrategy.getAssignType(), 2)) {
                    for (CaseInfoDistributedModel caseInfoDistributed : checkedList) {
                        countAllocation(caseInfoDistributed, modelInner);
                    }
                }
                // 委外
                if (Objects.equals(caseStrategy.getAssignType(), 3)) {
                    for (CaseInfoDistributedModel caseInfoDistributed : checkedList) {
                        countAllocation(caseInfoDistributed, modelOuter);
                    }
                }
                all.removeAll(checkedList);
            }
            List<CountAllocationModel> modelList = model.getModelList();
            modelList.add(modelInner);
            modelList.add(modelOuter);
            model.setModelList(modelList);
            //直接对分配的结果进行分案
            strategyAllocation(model, user);
        } catch (RuntimeException e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException("策略分案错误!");
        }
    }

    private void countAllocation(CaseInfoDistributedModel caseInfoDistributed, CountAllocationModel model) {
        Integer total = model.getTotal();
        model.setTotal(++total);
        BigDecimal amount = model.getAmount();
        model.setAmount(amount.add(caseInfoDistributed.getOverdueAmount()));
        List<String> ids = model.getIds();
        ids.add(caseInfoDistributed.getCaseId());
        model.setIds(ids);
    }

    public CaseStrategy previewResult(String jsonString) {
        StringBuilder sb = new StringBuilder();
        try {
            String jsonText = runCaseStrategyService.analysisRule(jsonString, sb);
            CaseStrategy caseStrategy = new CaseStrategy();
            caseStrategy.setId(UUID.randomUUID().toString());
            caseStrategy.setStrategyText(jsonText);
            return caseStrategy;
        } catch (Exception e) {
            throw new RuntimeException("策略解析失败!");
        }
    }

    /**
     * 获取实体所有字段及描述，制作Excel表头
     *
     * @return
     */
    public HashMap<String, String> createExcelHeader() {
        LinkedHashMap<String, String> headerMap = new LinkedHashMap<>(16);
        List<Field> fields = Lists.newArrayList(CaseInfoDistributed.class.getDeclaredFields());
        for (Field field : fields) {
            ApiModelProperty annotation = field.getAnnotation(ApiModelProperty.class);
//            if (Objects.isNull(annotation.notes()) || StringUtils.isEmpty(annotation.notes())) {
//                headerMap.put(field.getName(), annotation.name());
//            }
            if (Objects.nonNull(annotation) && Objects.nonNull(annotation.name()) && !StringUtils.isEmpty(annotation.name())) {
                headerMap.put(field.getName(), annotation.name());
            }
        }
        headerMap.put("city","申请城市");
        headerMap.put("personalName","客户姓名");
        headerMap.put("mobile","手机号");
        return headerMap;
    }

    /**
     * 创建excel表头
     * @return
     */
     public Map<String,String> creatHeader(){
         Map<String, String> headMap = new LinkedHashMap<>(); // Excel头
         headMap.put("caseNumber","案件编号"); //案件编号
         headMap.put("loanInvoiceNumber","借据号"); //借据号
         headMap.put("personalName","客户姓名"); //客户姓名;
         headMap.put("mobileNo","手机号"); //手机号;
         headMap.put("principalName","经销商"); //经销商
         headMap.put("overdueDays","逾期天数"); //逾期天数
         headMap.put("payStatus","逾期期数"); //逾期期数
         headMap.put("overDueAmount","逾期金额");//逾期金额
         headMap.put("overDueDate","逾期开始时间"); //逾期开始时间
         headMap.put("sourceChannel","来源渠道"); //来源渠道
         headMap.put("collectionMethod","催收模式"); //催收模式
         return headMap;
     }
    /**
     * 获取并返回失联数据，导出Excel
     *
     * @param distributedExportParams
     * @return
     */
    public List<Map<String, Object>> createExcelData(DistributedExportParams distributedExportParams) throws ParseException {
        List<Map<String, Object>> hashMapList = new ArrayList<>();
        List<Object[]> dataInfo = getDataInfo(distributedExportParams);
        DecimalFormat format = new DecimalFormat("0.00");
        dataInfo.get(0);
        for (Object[] objects1 : dataInfo){
            Map<String, Object> map = new HashMap<>();
            map.put("caseNumber",objects1[0]); //案件编号
            map.put("loanInvoiceNumber",objects1[1]); //借据号
            map.put("personalName",objects1[2]); //客户姓名
            map.put("mobileNo",objects1[3]); //手机号
            map.put("principalName",objects1[4]); //经销商
            map.put("overdueDays",objects1[5]); //逾期天数
            map.put("payStatus",objects1[6]); //逾期期数
            map.put("overDueAmount",format.format(objects1[7])); //逾期金额
            if (Objects.nonNull(objects1[8])){
                Date date = DateUtil.addDate(new SimpleDateFormat("yyyy-MM-dd").parse(objects1[8].toString()), 1);
                map.put("overDueDate",date); //逾期开始时间
            }
            map.put("sourceChannel",objects1[9]); //来源渠道
            map.put("collectionMethod",objects1[10]); //催收模式
            hashMapList.add(map);
        }
        return hashMapList;
    }
    public List<Object[]> getDataInfo(DistributedExportParams distributedExportParams) throws ParseException {
        if (!Strings.isNullOrEmpty(distributedExportParams.getStartOverDueDate()) && !Strings.isNullOrEmpty(distributedExportParams.getEndOverDueDate())){
            distributedExportParams.setStartOverDueDate(new SimpleDateFormat("yyyy-MM-dd 00:00:00").format(DateUtil.deleteDate(new SimpleDateFormat("yyyy-MM-dd").parse(distributedExportParams.getStartOverDueDate()),1)));
            distributedExportParams.setEndOverDueDate(new SimpleDateFormat("yyyy-MM-dd 23:59:59").format(DateUtil.deleteDate(new SimpleDateFormat("yyyy-MM-dd").parse(distributedExportParams.getEndOverDueDate()),1)));
        }
        StringBuilder stringBuilder = new StringBuilder("SELECT a.case_number,a.loan_invoice_number,b.`name` as personalName,b.mobile_no,c.`name`,a.overdue_days as overdue_days,a.pay_status,a.overdue_amount as overdue_amount," +
                "a.repay_date,a.source_channel,a.collection_method FROM case_info_distributed a \n" +
                "LEFT JOIN personal b ON a.personal_id = b.id\n" +
                "LEFT JOIN principal c ON a.principal_id = c.id where a.recover_remark = 0 ");
        if(ZWStringUtils.isNotEmpty(distributedExportParams.getCaseNumber())){
              stringBuilder.append(" and a.case_number =").append("'").append(distributedExportParams.getCaseNumber()).append("'");
        }
        if(ZWStringUtils.isNotEmpty(distributedExportParams.getCompanyCode())){
            stringBuilder.append(" and a.company_code =").append("'").append(distributedExportParams.getCompanyCode()).append("'");
        }
        if(ZWStringUtils.isNotEmpty(distributedExportParams.getPersonalName())){
            stringBuilder.append(" and b.name like ").append("'%").append(distributedExportParams.getPersonalName()).append("%'");
        }
        if(ZWStringUtils.isNotEmpty(distributedExportParams.getMobileNo())){
            stringBuilder.append(" and b.mobile_no =").append("'").append(distributedExportParams.getMobileNo()).append("'");
        }
        if(ZWStringUtils.isNotEmpty(distributedExportParams.getPrincipalId())){
            stringBuilder.append(" and c.id =").append("'").append(distributedExportParams.getPrincipalId()).append("'");
        }
        if(ZWStringUtils.isNotEmpty(distributedExportParams.getStartOverDueDate())){
            stringBuilder.append(" and DATE_FORMAT(a.repay_date,'%Y-%m-%d') >= DATE_FORMAT(").append("'").append(distributedExportParams.getStartOverDueDate()).append("'").append(",'%Y-%m-%d')");
        }
        if(ZWStringUtils.isNotEmpty(distributedExportParams.getEndOverDueDate())){
            stringBuilder.append(" and DATE_FORMAT(a.repay_date,'%Y-%m-%d') <= DATE_FORMAT(").append("'").append(distributedExportParams.getEndOverDueDate()).append("'").append(",'%Y-%m-%d')");
        }
        if(ZWStringUtils.isNotEmpty(distributedExportParams.getSourceChannel())){
            stringBuilder.append(" and a.source_channel =").append("'").append(distributedExportParams.getSourceChannel()).append("'");
        }
        if(ZWStringUtils.isNotEmpty(distributedExportParams.getCollectionMethod())){
            stringBuilder.append(" and a.collection_method =").append("'").append(distributedExportParams.getCollectionMethod()).append("'");
        }
///        stringBuilder.append(" group by a.case_number having 1=1");
        if(ZWStringUtils.isNotEmpty(distributedExportParams.getOverdueDaysStart())){
            stringBuilder.append(" and overdue_days >=").append("'").append(distributedExportParams.getOverdueDaysStart()).append("'");
        }
        if(ZWStringUtils.isNotEmpty(distributedExportParams.getOverdueDaysEnd())){
            stringBuilder.append(" and overdue_days <=").append("'").append(distributedExportParams.getOverdueDaysEnd()).append("'");
        }
        if(ZWStringUtils.isNotEmpty(distributedExportParams.getOverDueAmountStart())){
            stringBuilder.append(" and overdue_amount >=").append("'").append(distributedExportParams.getOverDueAmountStart()).append("'");
        }
        if(ZWStringUtils.isNotEmpty(distributedExportParams.getOverDueAmountEnd())){
            stringBuilder.append(" and overdue_amount <=").append("'").append(distributedExportParams.getOverDueAmountEnd()).append("'");
        }
        if(ZWStringUtils.isNotEmpty(distributedExportParams.getPayStatus())){
            if(!distributedExportParams.getPayStatus().equals("M6")){
                stringBuilder.append(" and a.pay_status =").append("'").append(distributedExportParams.getPayStatus()).append("'");
            }else {
                stringBuilder.append(" and a.pay_status NOT IN ('M1','M2','M3','M4','M5','M999')");
            }
        }
        logger.debug(stringBuilder.toString());
        List<Object[]> resultList = em.createNativeQuery(stringBuilder.toString()).getResultList();
        return resultList;
    }
}
