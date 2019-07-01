package cn.fintecher.pangolin.business.service;

import cn.fintecher.pangolin.business.model.CaseTurnModel;
import cn.fintecher.pangolin.business.model.ProcessApprovalModel;
import cn.fintecher.pangolin.business.repository.CaseInfoRepository;
import cn.fintecher.pangolin.business.repository.CaseTurnRecordRepository;
import cn.fintecher.pangolin.entity.*;
import cn.fintecher.pangolin.util.ZWStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import static cn.fintecher.pangolin.entity.QCaseInfo.caseInfo;

@Service
public class SaveCaseTurnRecordService {

    final Logger logger = LoggerFactory.getLogger(SaveCaseTurnRecordService.class);

    @Autowired
    CaseTurnRecordRepository caseTurnRecordRepository;

    @Autowired
    CaseInfoRepository caseInfoRepository;

    /**
     * @Description :保存案件流转记录
     */
    public void saveCaseTurningRecord(CaseTurnModel caseTurnModel) {
//            CaseInfo caseInfo = caseInfoRepository.findOne(QCaseInfo.caseInfo.caseNumber.eq(caseTurnModel.getCaseNumber()));
        List<CaseInfo> byCaseNumber = caseInfoRepository.findByCaseNumber(caseTurnModel.getCaseNumber());
        if (byCaseNumber.size() != 0) {
                //创建流转记录
            CaseInfo caseInfo = byCaseNumber.get(0);
                CaseTurnRecord caseTurnRecord = new CaseTurnRecord();
//                caseTurnRecord.setCaseId(caseInfo.getId());//案件ID
                caseTurnRecord.setCaseNumber(caseInfo.getCaseNumber());//案件编号
                caseTurnRecord.setCollectionStatus(caseInfo.getCollectionStatus());//催收状态
            for (int i = 0; i < byCaseNumber.size(); i++) {
                if (caseTurnRecord.getRealPayAmount() == null || caseTurnRecord.getContractAmount() == null){
//                    caseTurnRecord.setRealPayAmount(byCaseNumber.get(i).getRealPayAmount());
                    caseTurnRecord.setContractAmount(byCaseNumber.get(i).getOverdueAmount());
                    continue;
                }
//                caseTurnRecord.setRealPayAmount(byCaseNumber.get(i).getRealPayAmount().add(caseTurnRecord.getRealPayAmount()));//实际还款金额
                caseTurnRecord.setContractAmount(byCaseNumber.get(i).getOverdueAmount().add(caseTurnRecord.getContractAmount()));//合同金额
            }
                caseTurnRecord.setEarlyRealsettleAmt(caseInfo.getEarlyDerateAmt());//
                caseTurnRecord.setCaseId(caseInfo.getId());
                caseTurnRecord.setDepartId(Objects.isNull(caseInfo.getDepartment()) ? null : caseInfo.getDepartment().getId());//部门ID
                caseTurnRecord.setAssistWay(caseInfo.getAssistWay());//协催方式
                caseTurnRecord.setAssistFlag(caseInfo.getAssistFlag());//协催标识
                caseTurnRecord.setHoldDays(caseInfo.getHoldDays());//持案天数
                caseTurnRecord.setLeftDays(caseInfo.getLeftDays());//剩余天数
                caseTurnRecord.setCaseType(caseInfo.getCaseType());//案件类型
                caseTurnRecord.setCurrentCollector(Objects.isNull(caseInfo.getCurrentCollector()) ? null : caseInfo.getCurrentCollector().getId());//当前催员ID
                caseTurnRecord.setFollowUpNum(caseInfo.getFollowUpNum() + 1);//流转次数
                caseTurnRecord.setOperatorTime(new Date());//操作时间
                caseTurnRecord.setCompanyCode(caseInfo.getCompanyCode());//公司code码
                caseTurnRecord.setCollectionStatus(caseInfo.getCollectionStatus());//催收状态
                caseTurnRecord.setReceiveDeptName(null);//接受部门名称
                caseTurnRecord.setOperatorUserName(null);//操作员
                caseTurnRecord.setApplyName(caseTurnModel.getApplyName()); // 申请人
                caseTurnRecord.setCirculationType( caseTurnModel.getCirculationType());//手动流转);
                caseTurnRecord.setReceiveUserId(caseTurnModel.getReceiveUserId());//接收人ID
                caseTurnRecord.setReceiveUserRealName(caseTurnModel.getReceiveUserRealName());//接受人名称
                caseTurnRecord.setCirculationType(caseTurnModel.getCirculationType());//流转类型
                caseTurnRecord.setTurnFromPool(caseTurnModel.getTurnFromPool());//案件来源
                caseTurnRecord.setTurnToPool(caseTurnModel.getTurnToPool());//流转去向
                caseTurnRecord.setTurnApprovalStatus(caseTurnModel.getTurnApprovalStatus());//流转审核状态
                caseTurnRecord.setTurnDescribe(caseTurnModel.getTurnDescribe());//流转说明
                caseTurnRecord.setApprovalOpinion(caseTurnModel.getApprovalOpinion());//审批意见
                caseTurnRecord.setApprovalId(caseTurnModel.getApprovalId());//申请流程id
                caseTurnRecord.setTriggerAction(CaseTurnRecord.triggerActionEnum.MANUAL.getValue()); // 触发动作
                caseTurnRecordRepository.save(caseTurnRecord);
            }
    }

    /**
     * @Description :更新案件流转记录
     */
    public void updateCaseTurningRecord(ProcessApprovalModel processApprovalModel, User user) {

        CaseTurnRecord caseTurnRecord = caseTurnRecordRepository.findOne(QCaseTurnRecord.caseTurnRecord.caseNumber.eq(processApprovalModel.getCaseNumber())
                .and(QCaseTurnRecord.caseTurnRecord.turnApprovalStatus.eq(CaseTurnRecord.TurnApprovalStatus.WAIT_APPROVAL.getValue()))
                .and(QCaseTurnRecord.caseTurnRecord.circulationType.eq(CaseTurnRecord.circulationTypeEnum.APPROVAL.getValue())));
        if (Objects.nonNull(caseTurnRecord)) {
            Integer turnApprovalStatus = null;
            if(processApprovalModel.getNodeState() == 0){//同意
                turnApprovalStatus = CaseTurnRecord.TurnApprovalStatus.PASS.getValue();
            }else {//拒绝
                turnApprovalStatus = CaseTurnRecord.TurnApprovalStatus.REFUSE.getValue();
            }

            caseTurnRecord.setTurnApprovalStatus(turnApprovalStatus);//流转审核状态（待审批-222，通过-220，拒绝-221）
            caseTurnRecord.setApprovalOpinion(processApprovalModel.getNodeOpinion());//审批意见
            caseTurnRecord.setOperatorUserName(user.getRealName());//操作员
            caseTurnRecord.setApprovalId(processApprovalModel.getApprovalId());
            caseTurnRecordRepository.save(caseTurnRecord);
        }
    }


    public  Integer getTurnFromPool(CaseInfo caseInfo){

        Integer turnFromPool = null;
        if(Objects.isNull(caseInfo.getCasePoolType())){
            turnFromPool = CaseTurnRecord.TurnFromPool.EXCEL.getValue();
        }else if(Objects.equals(caseInfo.getCasePoolType(),CaseInfo.CasePoolType.INNER.getValue())){
            turnFromPool = CaseTurnRecord.TurnFromPool.INNER.getValue();
        }else if(Objects.equals(caseInfo.getCasePoolType(),CaseInfo.CasePoolType.OUTER.getValue())){
            turnFromPool = CaseTurnRecord.TurnFromPool.OUTER.getValue();
        }else if(Objects.equals(caseInfo.getCasePoolType(),CaseInfo.CasePoolType.SPECIAL.getValue())){
            turnFromPool = CaseTurnRecord.TurnFromPool.SPECIAL.getValue();
        }else if(Objects.equals(caseInfo.getCasePoolType(),CaseInfo.CasePoolType.STOP.getValue())){
            turnFromPool = CaseTurnRecord.TurnFromPool.STOP.getValue();
        }else if(Objects.equals(caseInfo.getCasePoolType(),CaseInfo.CasePoolType.RETURN.getValue())){
            turnFromPool = CaseTurnRecord.TurnFromPool.OUTER.getValue();
        }
        return turnFromPool;
    }

    public  Integer getTurnToPool(Integer turnToPool){
        if (Objects.equals(turnToPool, CaseTurnRecord.TurnToPool.INNER.getValue())) {
            turnToPool = CaseInfo.CasePoolType.INNER.getValue();
        } else if (Objects.equals(turnToPool, CaseTurnRecord.TurnToPool.OUTER.getValue())) {
            turnToPool = CaseInfo.CasePoolType.OUTER.getValue();
        } else if (Objects.equals(turnToPool, CaseTurnRecord.TurnToPool.SPECIAL.getValue())) {
            turnToPool = CaseInfo.CasePoolType.SPECIAL.getValue();
        } else if (Objects.equals(turnToPool, CaseTurnRecord.TurnToPool.STOP.getValue())) {
            turnToPool = CaseInfo.CasePoolType.STOP.getValue();
        }
        return turnToPool;
    }
}
