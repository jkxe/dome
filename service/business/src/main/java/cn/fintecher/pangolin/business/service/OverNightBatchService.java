package cn.fintecher.pangolin.business.service;

import cn.fintecher.pangolin.business.repository.*;
import cn.fintecher.pangolin.entity.*;
import cn.fintecher.pangolin.entity.util.Constants;
import cn.fintecher.pangolin.util.ZWDateUtil;
import com.google.common.collect.Lists;
import com.querydsl.jpa.impl.JPAQuery;
import org.apache.commons.lang3.StringUtils;
import org.quartz.JobDataMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;
import org.springframework.web.client.RestTemplate;

import javax.persistence.EntityManager;
import java.time.temporal.ChronoUnit;
import java.util.*;

/**
 * @Author: PeiShouWen
 * @Description: 晚间批量
 * @Date 15:46 2017/8/11
 */
@Service("overNightBatchService")
public class OverNightBatchService {
    Logger logger = LoggerFactory.getLogger(OverNightBatchService.class);

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    JobTaskService jobTaskService;

    @Autowired
    CaseInfoRepository caseInfoRepository;

    @Autowired
    EntityManager entityManager;

    @Autowired
    CaseAssistRepository caseAssistRepository;

    @Autowired
    CaseTurnRecordRepository caseTurnRecordRepository;

    @Autowired
    BatchManageRepository batchManageRepository;

    @Autowired
    CasePayApplyRepository casePayApplyRepository;

    @Autowired
    CaseAssistApplyRepository caseAssistApplyRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    SysParamRepository sysParamRepository;

    /**
     * 晚间批量任务
     *
     * @param jobDataMap
     */
    public void doOverNightTask(JobDataMap jobDataMap) {
        StopWatch watch = new StopWatch();
        watch.start();
        try {
            logger.info("开始晚间批量_{} ", jobDataMap.get("companyCode"));
            QSysParam qSysParam = QSysParam.sysParam;
            SysParam sysParam1 = sysParamRepository.findOne(qSysParam.companyCode.eq(jobDataMap.get("companyCode").toString())
                    .and(qSysParam.code.eq(Constants.OVERNIGHTBATCH_STATUS_CODE))
                    .and(qSysParam.type.eq(Constants.OVERNIGHTBATCH_STATUS_TYPE)));
            if (Objects.equals(StringUtils.trim(sysParam1.getValue()), "1")) {
                logger.info("晚间批量任务为停用状态");
                return;
            }
            if (jobTaskService.checkJobIsRunning(jobDataMap.getString("companyCode"), jobDataMap.getString("sysParamCode"))) {
                logger.info("晚间批量正在执行_{}", jobDataMap.get("companyCode"));
            } else {
                //获取超级管理员信息
                User user = userRepository.findOne(QUser.user.userName.eq(Constants.ADMIN_USER_NAME));
                //批量状态修改为正在执行
                jobTaskService.updateSysparam(jobDataMap.getString("companyCode"), jobDataMap.getString("sysParamCode"), Constants.BatchStatus.RUNING.getValue());
                //批量步骤
                SysParam sysParam = jobTaskService.getSysparam(jobDataMap.getString("companyCode"), Constants.SYSPARAM_OVERNIGHT_STEP);
                String step = sysParam.getValue();
                switch (step) {
                    case "6":
                        step = "0";
                        break;
                    case "0":
                        step = "1";
                        //重置序列号
                        doOverNightOne(jobDataMap, step);
                        break;
                    case "4":
                        step = "5";
                        //更新持案天数
                        doOverNightFive(jobDataMap, step);
                        break;
                    case "5":
                        step = "6";
                        //电催审批失效处理
                        doOverNightSix(jobDataMap, step);
                        break;
                    default:
                        break;
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            //批量状态修改为未执行
            try {
                jobTaskService.updateSysparam(jobDataMap.getString("companyCode"), jobDataMap.getString("sysParamCode"), Constants.BatchStatus.STOP.getValue());
            } catch (Exception e) {
                logger.info("结束晚间批量 {} ,修改批量执行状态失败 {}", jobDataMap.get("companyCode"), jobDataMap.getString("sysParamCode"), e);
            }
            watch.stop();
            logger.info("结束晚间批量 {} ,耗时: {} 毫秒", jobDataMap.get("companyCode"), watch.getTotalTimeMillis());
        }
    }

    /**
     * 重置批次号和案件编号
     *
     * @param jobDataMap
     * @param step
     */
    public void doOverNightOne(JobDataMap jobDataMap, String step) throws Exception {
        logger.info("开始重置序列_{}", jobDataMap.getString("companyCode"));
        ResponseEntity responseEntity = null;
        try {
            responseEntity = restTemplate.getForEntity("http://dataimp-service/api/sequenceController/restSequence?id1=".concat(Constants.ORDER_SEQ)
                    .concat("&id2=").concat(Constants.CASE_SEQ).
                            concat("&companyCode=").concat(jobDataMap.getString("companyCode")), String.class);
            if (Objects.isNull(responseEntity) || Objects.isNull(responseEntity.getBody())) {
                throw new Exception(jobDataMap.getString("companyCode").concat("重置序列失败"));
            }
            //更新批量步骤
            jobTaskService.updateSysparam(jobDataMap.getString("companyCode"), Constants.SYSPARAM_OVERNIGHT_STEP, step);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new Exception(jobDataMap.getString("companyCode").concat("重置序列失败"));
        }
        logger.info("结束重置序列_{}", jobDataMap.getString("companyCode"));
    }

    /**
     * 案件强制流转
     *
     * @param jobDataMap
     * @param step
     * @throws Exception
     */
    /*@Transactional
    public void doOverNightTwo(JobDataMap jobDataMap, String step, User user) throws Exception {
        logger.info("开始案件强制流转_{}", jobDataMap.getString("companyCode"));
        QCaseInfo qCaseInfo = QCaseInfo.caseInfo;
        try {
            //获取电催留案天数
            SysParam sysParam = jobTaskService.getSysparam(jobDataMap.getString("companyCode"), Constants.SYS_PHNOEFLOW_BIGDAYS);
            List<CaseInfo> caseInfoList = forceTurnCase(sysParam, qCaseInfo, CaseInfo.CollectionType.TEL.getValue());
            //电催更新
            updateCaseInfo(user, caseInfoList, CaseInfo.CaseType.PHNONEFORCETURN.getValue(), Constants.SYS_PHNOETURN_BIGDEPTNAME);
            //获取外访留案天数
            SysParam sysParam2 = jobTaskService.getSysparam(jobDataMap.getString("companyCode"), Constants.SYS_OUTBOUNDFLOW_BIGDAYS);
            List<CaseInfo> caseInfoList2 = forceTurnCase(sysParam2, qCaseInfo, CaseInfo.CollectionType.VISIT.getValue());
            //外访更新
            updateCaseInfo(user, caseInfoList2, CaseInfo.CaseType.OUTFORCETURN.getValue(), Constants.SYS_OUTTURN_BIGDEPTNAME);
            //全程协催案件强制流转 全程协催 在电催强制流转的时候处理 这里不需要单独处理
//            SysParam sysParam3 = jobTaskService.getSysparam(jobDataMap.getString("companyCode"), Constants.SYS_ASSIST_BIGDAYS);
//            List<CaseAssist> caseAssistList = caseAssistRepository.queryAssitForce(CaseAssist.AssistWay.WHOLE_ASSIST.getValue(), Integer.parseInt(sysParam3.getValue()),
//                    jobDataMap.getString("companyCode"), CaseInfo.leaveCaseFlagEnum.NO_LEAVE.getValue());
//            assitForce(user, caseAssistList);
            //更新批量步骤
            jobTaskService.updateSysparam(jobDataMap.getString("companyCode"), Constants.SYSPARAM_OVERNIGHT_STEP, step);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new Exception(jobDataMap.getString("companyCode").concat("案件强制流转批量失败"));
        }
        logger.info("结束案件强制流转_{}", jobDataMap.getString("companyCode"));
    }*/


    /**
     * 案件小流转
     *
     * @param jobDataMap
     * @param step
     */
   /* @Transactional
    public void doOverNightThree(JobDataMap jobDataMap, String step, User user) throws Exception {
        logger.info("开始案件小流转_{}", jobDataMap.getString("companyCode"));
        QCaseInfo qCaseInfo = QCaseInfo.caseInfo;
        //查找非留案、非结案、配置天数类无任何催收反馈的电催案件
        try {
            //电催小流转配置参数
            SysParam sysParam1 = jobTaskService.getSysparam(jobDataMap.getString("companyCode"), Constants.SYS_PHNOEFLOW_SMALLDAYS);
            List<CaseInfo> caseInfoList1 = smallTurnCase(sysParam1, qCaseInfo, CaseInfo.CollectionType.TEL.getValue());
            //电催更新
            updateCaseInfo(user, caseInfoList1, CaseInfo.CaseType.PHNONESMALLTURN.getValue(), Constants.SYS_PHNOETURN_SMALLDEPTNAME);

            //外访小流转
            SysParam sysParam2 = jobTaskService.getSysparam(jobDataMap.getString("companyCode"), Constants.SYS_OUTBOUNDFLOW_SMALLDAYS);
            List<CaseInfo> caseInfoList2 = smallTurnCase(sysParam2, qCaseInfo, CaseInfo.CollectionType.VISIT.getValue());
            //外访更新
            updateCaseInfo(user, caseInfoList2, CaseInfo.CaseType.OUTSMALLTURN.getValue(), Constants.SYS_OUTTURN_SMALLDEPTNAME);
            //单次协催案件结束 单次协催不直接处理，根据原电催案件进行处理
//            SysParam sysParam3 = jobTaskService.getSysparam(jobDataMap.getString("companyCode"), Constants.SYS_ASSIST_SMALLDAYS);
//            List<CaseAssist> caseAssistList = caseAssistRepository.queryAssitForce(CaseAssist.AssistWay.ONCE_ASSIST.getValue(), Integer.parseInt(sysParam3.getValue()),
//                    jobDataMap.getString("companyCode"), CaseInfo.leaveCaseFlagEnum.NO_LEAVE.getValue());
//            assitForce(user, caseAssistList);

            //更新批量步骤
            jobTaskService.updateSysparam(jobDataMap.getString("companyCode"), Constants.SYSPARAM_OVERNIGHT_STEP, step);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new Exception(jobDataMap.getString("companyCode").concat("案件小流转批量失败"));
        }
        logger.info("结束案件小流转_{}", jobDataMap.getString("companyCode"));
    }*/

    /**
     * 留案案件流转
     *
     * @param jobDataMap
     * @param step
     */
   /* @Transactional
    public void doOverNightFour(JobDataMap jobDataMap, String step, User user) throws Exception {
        logger.info("开始留案案件流转_{}", jobDataMap.getString("companyCode"));
        QCaseInfo qCaseInfo = QCaseInfo.caseInfo;
        try {
            //电催留案配置参数
            SysParam sysParam1 = jobTaskService.getSysparam(jobDataMap.getString("companyCode"), Constants.SYS_PHNOEFLOW_LEAVEDAYS);
            List<CaseInfo> caseInfoList1 = leaveTurnCase(qCaseInfo, sysParam1, CaseInfo.CollectionType.TEL.getValue());
            //电催更新
            updateCaseInfo(user, caseInfoList1, CaseInfo.CaseType.PHNONELEAVETURN.getValue(), Constants.SYS_PHNOETURN_LEAVEDEPTNAME);

            //外访留案配置参数
            SysParam sysParam2 = jobTaskService.getSysparam(jobDataMap.getString("companyCode"), Constants.SYS_OUTBOUNDFLOW_LEAVEDAYS);
            List<CaseInfo> caseInfoList2 = leaveTurnCase(qCaseInfo, sysParam2, CaseInfo.CollectionType.VISIT.getValue());
            //外访更新
            updateCaseInfo(user, caseInfoList2, CaseInfo.CaseType.OUTLEAVETURN.getValue(), Constants.SYS_OUTTURN_LEAVEDEPTNAME);
            //协催留案 协催案件不涉及留案
//            SysParam sysParam3 = jobTaskService.getSysparam(jobDataMap.getString("companyCode"), Constants.SYS_ASSIST_LEAVEDAYS);
//            Iterable<CaseAssist> caseAssistList = caseAssistRepository.findAll(QCaseAssist.caseAssist.leaveCaseFlag.eq(CaseInfo.leaveCaseFlagEnum.YES_LEAVE.getValue())
//                    .and(QCaseAssist.caseAssist.assistStatus.in(28, 117, 118))
//                    .and(QCaseAssist.caseAssist.assistCollector.isNotNull())
//                    .and(QCaseAssist.caseAssist.hasLeaveDays.goe(Integer.parseInt(sysParam3.getValue())))
//                    .and(QCaseAssist.caseAssist.companyCode.eq(jobDataMap.getString("companyCode"))));
//            assitForce(user, Lists.newArrayList(caseAssistList));
            //更新批量步骤
            jobTaskService.updateSysparam(jobDataMap.getString("companyCode"), Constants.SYSPARAM_OVERNIGHT_STEP, step);

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new Exception(jobDataMap.getString("companyCode").concat("案件留案流转批量失败"));
        }
        logger.info("结束留案案件流转_{}", jobDataMap.getString("companyCode"));
    }*/


    /**
     * 持案天数/剩余天数/留案天数更新
     *
     * @param jobDataMap
     * @param step
     */
    public void doOverNightFive(JobDataMap jobDataMap, String step) throws Exception {
        logger.info("开始更新案件相关天数更新_{}", jobDataMap.getString("companyCode"));
        try {
            QCaseInfo qCaseInfo = QCaseInfo.caseInfo;
            JPAQuery<CaseInfo> jpaQuery = new JPAQuery<>(entityManager);
            jpaQuery.select(qCaseInfo)
                    .from(QCaseInfo.caseInfo);
            jpaQuery.where(qCaseInfo.collectionStatus.ne(CaseInfo.CollectionStatus.CASE_OVER.getValue())//未结案
                    .and(qCaseInfo.currentCollector.isNotNull())
                    .and(qCaseInfo.companyCode.eq(jobDataMap.getString("companyCode"))));//催收员不为空
            List<CaseInfo> caseInfoList = jpaQuery.fetch();
            //更新相关天数
            for (CaseInfo caseInfo : caseInfoList) {
                caseInfo.setHoldDays(Objects.isNull(caseInfo.getHoldDays()) ? 1 : (caseInfo.getHoldDays() + 1));//持案天数
                if (Objects.nonNull(caseInfo.getLeaveCaseFlag()) && caseInfo.getLeaveCaseFlag().equals(CaseInfo.leaveCaseFlagEnum.YES_LEAVE.getValue())) {
                    //留案天数
                    caseInfo.setHasLeaveDays(Objects.isNull(caseInfo.getHasLeaveDays()) ? 1 : (caseInfo.getHasLeaveDays() + 1));
                }
                //案件剩余天数
                Integer leftDays = ZWDateUtil.getBetween(ZWDateUtil.getNowDate(), caseInfo.getCloseDate(), ChronoUnit.DAYS);
                caseInfo.setLeftDays(leftDays);
            }
            caseInfoRepository.save(caseInfoList);
            //协催的相关天数
            JPAQuery<CaseAssist> caseAssistJPAQuery = new JPAQuery<>(entityManager);
            QCaseAssist qCaseAssist = QCaseAssist.caseAssist;
            caseAssistJPAQuery.select(qCaseAssist)
                    .from(qCaseAssist);
            caseAssistJPAQuery.where(qCaseAssist.assistStatus.in(28, 117, 118)//协催未结束
                    .and(qCaseAssist.currentCollector.isNotNull())//协催已分配
                    .and(qCaseAssist.companyCode.eq(jobDataMap.getString("companyCode"))));//公司CODE
            List<CaseAssist> caseAssistList = caseAssistJPAQuery.fetch();
            for (CaseAssist caseAssist : caseAssistList) {
                caseAssist.setHoldDays(Objects.isNull(caseAssist.getHoldDays()) ? 1 : (caseAssist.getHoldDays() + 1));
                if (caseAssist.getLeaveCaseFlag().equals(CaseInfo.leaveCaseFlagEnum.YES_LEAVE.getValue())) {
                    caseAssist.setHasLeaveDays(Objects.isNull(caseAssist.getHasLeaveDays()) ? 1 : (caseAssist.getHasLeaveDays() + 1));
                }
            }
            caseAssistRepository.save(caseAssistList);
            //更新批量步骤
            jobTaskService.updateSysparam(jobDataMap.getString("companyCode"), Constants.SYSPARAM_OVERNIGHT_STEP, step);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new Exception(jobDataMap.getString("companyCode").concat("案件相关天数更新批量失败"));
        }
        logger.info("结束更新案件相关天数更新_{}", jobDataMap.getString("companyCode"));
    }

    /**
     * 协催审批失效
     */
    public void doOverNightSix(JobDataMap jobDataMap, String step) throws Exception {
        logger.info("开始协催审批失效_{}", jobDataMap.getString("companyCode"));
        QCaseAssistApply qCaseAssistApply = QCaseAssistApply.caseAssistApply;
        Date nowDate = ZWDateUtil.getNowDate();
        Set<String> idset = new HashSet<>();
        try {
            //电催审批失效配置参数
            SysParam sysParam1 = jobTaskService.getSysparam(jobDataMap.getString("companyCode"), Constants.ASSIST_APPLY_CODE);
            List<CaseAssistApply> caseInfoList1 = approvePhoneCase(qCaseAssistApply, sysParam1, CaseAssistApply.ApproveStatus.TEL_APPROVAL.getValue());
            for (CaseAssistApply caseAssistApply : caseInfoList1) {
                idset.add(caseAssistApply.getCaseId());
            }
            //电催审批失效更新
            updatePhoneCase(caseInfoList1, nowDate);

            //外访审批失效配置参数
            SysParam sysParam2 = jobTaskService.getSysparam(jobDataMap.getString("companyCode"), Constants.ASSIST_APPLY_CODE);
            List<CaseAssistApply> caseInfoList2 = approvePhoneCase(qCaseAssistApply, sysParam2, CaseAssistApply.ApproveStatus.VISIT_APPROVAL.getValue());
            for (CaseAssistApply caseAssistApply : caseInfoList2) {
                idset.add(caseAssistApply.getCaseId());
            }
            //外访审批失效更新
            updateOutCase(caseInfoList2, nowDate);
            //修改案件协催状态
            if (!idset.isEmpty()) {
                List<CaseInfo> caseInfoList = caseInfoRepository.findAll(idset);
                for (CaseInfo caseInfo : caseInfoList) {
                    caseInfo.setAssistFlag(CaseInfo.AssistFlag.NO_ASSIST.getValue());
                    caseInfo.setAssistWay(null);
                    caseInfo.setAssistStatus(null);
                    caseInfo.setAssistCollector(null);
                }
                caseInfoRepository.save(caseInfoList);
            }
            //更新批量步骤
            jobTaskService.updateSysparam(jobDataMap.getString("companyCode"), Constants.SYSPARAM_OVERNIGHT_STEP, step);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new Exception(jobDataMap.getString("companyCode").concat("协催审批更新批量失败"));
        }
        logger.info("结束协催审批失效_{}", jobDataMap.getString("companyCode"));
    }

    /**
     * 留案案件查询
     *
     * @param qCaseInfo
     * @param sysParam
     * @param collectionType
     * @return
     */
    private List<CaseInfo> leaveTurnCase(QCaseInfo qCaseInfo, SysParam sysParam, Integer collectionType) {

        Iterable<CaseInfo> caseInfoIterable = caseInfoRepository.findAll(qCaseInfo.leaveCaseFlag.eq(CaseInfo.leaveCaseFlagEnum.YES_LEAVE.getValue())//留案
                .and(qCaseInfo.collectionStatus.ne(CaseInfo.CollectionStatus.CASE_OVER.getValue()))//未结案
                .and(qCaseInfo.currentCollector.isNotNull())//催收员不为空
                .and(qCaseInfo.collectionType.eq(collectionType))//催收类型
                .and(qCaseInfo.hasLeaveDays.goe(Integer.parseInt(sysParam.getValue())))//留案天数大于等于配置参数
                .and(qCaseInfo.companyCode.eq(sysParam.getCompanyCode()))); //公司码
        return Lists.newArrayList(caseInfoIterable);
    }

    /**
     * 电催小流转案件查询
     *
     * @param sysParam
     * @param qCaseInfo
     * @param collectionType
     */
    private List<CaseInfo> smallTurnCase(SysParam sysParam, QCaseInfo qCaseInfo, Integer collectionType) {
        Iterable<CaseInfo> caseInfoIterable = caseInfoRepository.findAll(qCaseInfo.leaveCaseFlag.eq(CaseInfo.leaveCaseFlagEnum.NO_LEAVE.getValue())//未留案
                .and(qCaseInfo.collectionStatus.ne(CaseInfo.CollectionStatus.CASE_OVER.getValue()))//未结案
                .and(qCaseInfo.currentCollector.isNotNull())//催收员不为空
                .and(qCaseInfo.collectionType.eq(collectionType))//催收类型
                .and(qCaseInfo.holdDays.goe(Integer.parseInt(sysParam.getValue())))//持有天数大于等于配置参数
                .and(qCaseInfo.followupTime.isNull().or(qCaseInfo.followupTime.lt(qCaseInfo.caseFollowInTime)))//最近跟进日期
                .and(qCaseInfo.companyCode.eq(sysParam.getCompanyCode())));//公司码
        return Lists.newArrayList(caseInfoIterable);
    }

    /**
     * 强制流转案件查询
     *
     * @param sysParam
     * @param qCaseInfo
     * @param collectionType
     * @return
     */
    private List<CaseInfo> forceTurnCase(SysParam sysParam, QCaseInfo qCaseInfo, Integer collectionType) {
        Iterable<CaseInfo> caseInfoIterable = caseInfoRepository.findAll(qCaseInfo.leaveCaseFlag.eq(CaseInfo.leaveCaseFlagEnum.NO_LEAVE.getValue())//未留案
                .and(qCaseInfo.collectionStatus.ne(CaseInfo.CollectionStatus.CASE_OVER.getValue()))//未结案
                .and(qCaseInfo.currentCollector.isNotNull())//催收员不为空
                .and(qCaseInfo.collectionType.eq(collectionType))//催收类型
                .and(qCaseInfo.holdDays.goe(Integer.parseInt(sysParam.getValue())))//持案天数
//                .and(qCaseInfo.assistFlag.eq(CaseInfo.AssistFlag.NO_ASSIST.getValue()))//协催标志（未协催的）
                .and(qCaseInfo.assistStatus.eq(CaseInfo.AssistStatus.ASSIST_APPROVEING.getValue()).or(qCaseInfo.assistStatus.isNull())) //协催状态为待审批或为空
                .and(qCaseInfo.companyCode.eq(sysParam.getCompanyCode())));//公司码
        return Lists.newArrayList(caseInfoIterable);
    }

    /**
     * 更新跟案件流转相关的信息
     *
     * @param user
     * @param caseInfoList
     * @param caseType     案件类型
     * @param trunDeptName 流转部门
     */
    private void updateCaseInfo(User user, List<CaseInfo> caseInfoList, Integer caseType, String trunDeptName) {
        List<CaseTurnRecord> caseTurnRecordList = new ArrayList<>();
        Set<String> idSets = new HashSet<>();//记录有协催案件
        Set<String> caseIdSets = new HashSet<>();//重置案件协催标识
        //更新案件属性
        for (CaseInfo caseInfo : caseInfoList) {
            idSets.add(caseInfo.getId());
            Department department = caseInfo.getDepartment();
            //部门ID置空
            caseInfo.setDepartment(null);
            caseInfo.setHoldDays(0);//持案天数
            caseInfo.setCaseType(caseType);//案件类型
            caseInfo.setCaseMark(CaseInfo.Color.NO_COLOR.getValue());//打标标记
            caseInfo.setCaseFollowInTime(ZWDateUtil.getNowDateTime());//流入时间
            caseInfo.setFollowUpNum(caseInfo.getFollowUpNum() + 1);//流转次数
            caseInfo.setLatelyCollector(caseInfo.getCurrentCollector());//上一个催员
            caseInfo.setCurrentCollector(null);//当前催员
            caseInfo.setFollowupTime(null);//跟进时间
            caseInfo.setFollowupBack(null);//催收反馈
            caseInfo.setPromiseAmt(null);//承诺还款金额
            caseInfo.setPromiseTime(null);//承诺还款日期
            caseInfo.setCirculationStatus(null);//小流转状态
            caseInfo.setAssistFlag(CaseInfo.AssistFlag.NO_ASSIST.getValue());//未协催
            caseInfo.setAssistStatus(null);//协催状态
            caseInfo.setAssistWay(null); //协催方式
            caseInfo.setAssistCollector(null);//协催员
            caseInfo.setLeaveCaseFlag(CaseInfo.leaveCaseFlagEnum.NO_LEAVE.getValue());//留案标志
            caseInfo.setLeaveDate(null);//留案操作日期
            caseInfo.setHasLeaveDays(0);//留案天数
            caseInfo.setHandUpFlag(CaseInfo.HandUpFlag.NO_HANG.getValue());//是否挂起
            caseInfo.setOperator(user);
            caseInfo.setOperatorTime(ZWDateUtil.getNowDateTime());

            //案件流转记录
            CaseTurnRecord caseTurnRecord = new CaseTurnRecord();
            BeanUtils.copyProperties(caseInfo, caseTurnRecord); //将案件信息复制到流转记录
            caseTurnRecord.setId(null); //主键置空
            caseTurnRecord.setCaseId(caseInfo.getId()); //案件ID
            caseTurnRecord.setDepartId(Objects.isNull(department) ? null : department.getId()); //部门ID
            caseTurnRecord.setReceiveUserRealName(user.getRealName()); //接受人名称
            caseTurnRecord.setReceiveDeptName(trunDeptName);
            caseTurnRecord.setCirculationType(CaseTurnRecord.circulationTypeEnum.AUTO.getValue()); //流转类型 1-自动流转
            caseTurnRecord.setOperatorUserName(user.getUserName()); //操作员用户名
            caseTurnRecord.setApplyName(user.getRealName());
            caseTurnRecord.setOperatorTime(ZWDateUtil.getNowDateTime()); //操作时间
            caseTurnRecordList.add(caseTurnRecord);
        }
        //协催审批结束
        Iterable<CaseAssistApply> caseAssistApplyList = caseAssistApplyRepository.findAll(QCaseAssistApply.caseAssistApply.approveStatus.in(CaseAssistApply.ApproveStatus.TEL_APPROVAL.getValue(), CaseAssistApply.ApproveStatus.VISIT_APPROVAL.getValue())
                .and(QCaseAssistApply.caseAssistApply.caseId.in(idSets)));
        for (Iterator<CaseAssistApply> it = caseAssistApplyList.iterator(); it.hasNext(); ) {
            CaseAssistApply obj = it.next();
            if (CaseAssistApply.ApproveStatus.TEL_APPROVAL.getValue().equals(obj.getApproveStatus())) {
                obj.setApproveStatus(CaseAssistApply.ApproveStatus.TEL_COMPLETE.getValue());
            }
            if (CaseAssistApply.ApproveStatus.VISIT_APPROVAL.getValue().equals(obj.getApproveStatus())) {
                obj.setApproveStatus(CaseAssistApply.ApproveStatus.VISIT_COMPLETE.getValue());
            }
            obj.setApprovePhoneResult(CaseAssistApply.ApproveResult.FORCED_REJECT.getValue());
            caseIdSets.add(obj.getCaseId());
        }
        //正在协催的案件全部置为协催结束
        List<CaseAssist> caseAssists = new ArrayList<>();
        Iterable<CaseAssist> caseAssistRepositoryAll = caseAssistRepository.findAll(
                QCaseAssist.caseAssist.assistStatus.in(CaseInfo.AssistStatus.ASSIST_COLLECTING.getValue(), CaseInfo.AssistStatus.ASSIST_WAIT_ACC.getValue(), CaseInfo.AssistStatus.ASSIST_WAIT_ASSIGN.getValue()));
        for (Iterator<CaseAssist> it = caseAssistRepositoryAll.iterator(); it.hasNext(); ) {
            CaseAssist caseAssist = it.next();
            caseAssist.setAssistStatus(CaseInfo.AssistStatus.ASSIST_COMPLATED.getValue());
            caseAssists.add(caseAssist);
        }
        List<CaseInfo> caseInfoList1 = caseInfoRepository.findAll(caseIdSets);
        updateCaseInfoAssit(caseInfoList1);
        caseInfoRepository.save(caseInfoList1);
        caseAssistApplyRepository.save(caseAssistApplyList);
        caseTurnRecordRepository.save(caseTurnRecordList);
        caseAssistRepository.save(caseAssists);
        caseInfoRepository.save(caseInfoList);
    }

    private void updateCaseInfoAssit(List<CaseInfo> caseInfoList1) {
        for (CaseInfo caseInfo : caseInfoList1) {
            caseInfo.setAssistFlag(CaseInfo.AssistFlag.NO_ASSIST.getValue());//协催标志
            caseInfo.setAssistCollector(null);//协催员
            caseInfo.setAssistStatus(null);//协催状态
            caseInfo.setAssistWay(null);//协催方式
        }
    }

    /**
     * 电催/外访待审批
     */
    private List<CaseAssistApply> approvePhoneCase(QCaseAssistApply qCaseAssistApply, SysParam sysParam, Integer type) {
        Iterable<CaseAssistApply> caseAssistApplyIterable = caseAssistApplyRepository.findAll(qCaseAssistApply.approveStatus.eq(type)
                .and(qCaseAssistApply.applyInvalidTime.loe(ZWDateUtil.getNowDate()))
                .and(qCaseAssistApply.companyCode.eq(sysParam.getCompanyCode())));
        return Lists.newArrayList(caseAssistApplyIterable);
    }

    /**
     * 更新审批状态和审批结果
     *
     * @param caseInfoList1
     * @param date
     */
    public void updatePhoneCase(List<CaseAssistApply> caseInfoList1, Date date) {
        for (CaseAssistApply caseAssistApply : caseInfoList1) {
            caseAssistApply.setApproveStatus(CaseAssistApply.ApproveStatus.FAILURE.getValue());//审批状态
            caseAssistApply.setApprovePhoneResult(CaseAssistApply.ApproveResult.OUT_DATE.getValue());//审批结果
            caseAssistApplyRepository.save(caseAssistApply);
        }

    }

    /**
     * 更新WAIFANG 审批状态和审批结果
     *
     * @param caseInfoList2
     * @param date
     */
    public void updateOutCase(List<CaseAssistApply> caseInfoList2, Date date) {
        for (CaseAssistApply caseAssistApply : caseInfoList2) {
            caseAssistApply.setApproveStatus(CaseAssistApply.ApproveStatus.FAILURE.getValue());//审批状态
            caseAssistApply.setApproveOutResult(CaseAssistApply.ApproveResult.OUT_DATE.getValue());//审批结果
            caseAssistApplyRepository.save(caseAssistApply);
        }
    }

    /**
     * 协催案件强制流转
     *
     * @param user
     * @param caseAssistList
     */
    private void assitForce(User user, List<CaseAssist> caseAssistList) {
        Set<String> caseIds = new HashSet<>();
        for (CaseAssist caseAssist : caseAssistList) {
            caseIds.add(caseAssist.getId());
            //协催案件结束
            caseAssist.setAssistStatus(CaseInfo.AssistStatus.ASSIST_COMPLATED.getValue());
            caseAssist.setAssistCloseFlag(CaseAssist.AssistCloseFlagEnum.AUTO.getValue());
            caseAssist.setOperatorTime(ZWDateUtil.getNowDateTime());
            caseAssist.setOperator(user);
            caseAssist.setMarkId(CaseInfo.Color.NO_COLOR.getValue());
        }
        caseAssistRepository.save(caseAssistList);
        List<CaseInfo> caseInfoList1 = caseInfoRepository.findAll(caseIds);
        updateCaseInfoAssit(caseInfoList1);
        caseInfoRepository.save(caseInfoList1);
    }
}
