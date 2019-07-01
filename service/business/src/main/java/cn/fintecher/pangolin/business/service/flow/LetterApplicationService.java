package cn.fintecher.pangolin.business.service.flow;

import cn.fintecher.pangolin.business.model.*;
import cn.fintecher.pangolin.business.repository.*;
import cn.fintecher.pangolin.business.service.ReminderService;
import cn.fintecher.pangolin.business.service.SaveCaseTurnRecordService;
import cn.fintecher.pangolin.entity.*;
import cn.fintecher.pangolin.entity.message.SendReminderMessage;
import cn.fintecher.pangolin.util.ZWDateUtil;
import cn.fintecher.pangolin.web.HeaderUtil;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import io.swagger.annotations.Example;
import org.apache.commons.collections4.IterableUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;
import java.util.*;

/**
 * @author ZhangYaJun
 * @Title: letterApplicationService
 * @ProjectName pangolin
 * @Description:
 * @date 2019/1/15 0015下午 17:11
 */
@Service("LetterApplicationService")
public class LetterApplicationService {

   @Autowired
   private CaseInfoRepository  caseInfoRepository;

   @Autowired
   private CaseRepairApplyRepository caseRepairApplyRepository;

   @Autowired
   private CaseRepairRecordRepository  caseRepairRecordRepository;

   @Autowired
   private   CaseRepairApprovalRecordRepository   caseRepairApprovalRecordRepository;

   @Autowired
   private  TaskInfoService  taskInfoService;

   @Autowired
   private ProcessBaseService  processBaseService;

   @Autowired
   private CaseRepairRepository  caseRepairRepository;

   @Autowired
   private RoleRepository  roleRepository;

   @Autowired
   private PersonalRepository  personalRepository;

   @Autowired
   private ReminderService reminderService;

   public void applyLetterApplication(ApplyLetterApplocationMode applyLetterApplocationMode, User user) {

      CaseInfo caseInfo = caseInfoRepository.findOne(QCaseInfo.caseInfo.loanInvoiceNumber.eq(applyLetterApplocationMode.getCaseId()).and(QCaseInfo.caseInfo.collectionStatus.ne(CaseInfo.CollectionStatus.CASE_OVER.getValue())));
       Iterable<CaseRepairApply> all = caseRepairApplyRepository.findAll(QCaseRepairApply.caseRepairApply.loanInvoiceNumber.eq(applyLetterApplocationMode.getCaseId()));
       if (all.iterator().hasNext() && all.iterator().next().getApprovalStatus() < 2){
           throw new RuntimeException("有案件在修复审批流程中,请勿重复申请!");
       }
       if (Objects.isNull(caseInfo)) {
         throw new RuntimeException("该案件未找到");
      }

      //催收来源
      //Integer turnFromPool = saveCaseTurnRecordService.getTurnFromPool(caseInfo);
      //判断是否申请角色
      if (taskInfoService.existsApply(applyLetterApplocationMode.getTaskId(), user)) {

         if (exitApply(caseInfo)) {
                  String approvalId = taskInfoService.applyCaseInfo(caseInfo.getCaseNumber(), applyLetterApplocationMode.getTaskId(), user,applyLetterApplocationMode.getApplyReason());//流程申请
                  CaseRepairApply caseRepairApply   =   new CaseRepairApply();
                  caseRepairApply.setCaseId(caseInfo.getId());
                  caseRepairApply.setApprovalId(approvalId); //申请流程id
                  caseRepairApply.setSourceType(null);//案件来源null
                  caseRepairApply.setLoanInvoiceNumber(caseInfo.getLoanInvoiceNumber());
                  caseRepairApply.setApplyUser(user.getRealName());//申请人
                  caseRepairApply.setApplyTime(new Date());//申请时间
                  caseRepairApply.setApprovalStatus(0);//待审批状态
                  caseRepairApply.setCaseNumber(caseInfo.getCaseNumber());
                  caseRepairApply.setLetterDescribe(applyLetterApplocationMode.getApplyReason());
                  caseRepairApplyRepository.save(caseRepairApply);//保存修复申请
            //提醒消息
           /* SendReminderMessage sendReminderMessage = new SendReminderMessage();
            sendReminderMessage.setTitle("客户[" + caseInfo.getPersonalInfo().getName() + "]发起信修申请");
            sendReminderMessage.setType(ReminderType.COMMON_MESSAGE);
            sendReminderMessage.setUserId(user.getId());
            sendReminderMessage.setContent("申请说明:" + applyLetterApplocationMode.getApplyReason());
            reminderService.sendReminder(sendReminderMessage);*/


         } else {
            throw new RuntimeException("已存在待审核/待修复案件,不能重复申请");
         }
      } else {
         throw new RuntimeException("该用户不是信修申请的角色");
      }
   }




   /**
    * 判断该案件是否在流转中
    *
    * @param caseInfo
    *
    * @return
    */
   public boolean exitApply(CaseInfo caseInfo) {

      Iterable<FlowApproval> flowApprovals = processBaseService.getFlowApproavalListByCaseNumber(caseInfo.getCaseNumber());
      if (flowApprovals.iterator().hasNext()) {
         //审批通过后将会在申请表中删除对应的申请记录。
         Iterator<CaseRepairApply> all = caseRepairApplyRepository.findAll(QCaseRepairApply.caseRepairApply.caseId.eq(caseInfo.getId()).and(QCaseRepairApply.caseRepairApply.approvalStatus.eq(0))).iterator();
         Iterator<CaseRepair> iterator = caseRepairRepository.findAll(QCaseRepair.caseRepair.caseId.eq(caseInfo).and(QCaseRepair.caseRepair.repairStatus.eq(187))).iterator();
         while (all.hasNext()){
           Integer  next = all.next().getApprovalStatus();
            if(next != null && next.equals(0) ){
               return  false;
            }
         }
         while (iterator.hasNext()){
            Integer repairStatus = iterator.next().getRepairStatus();
            if(repairStatus.equals(187) && iterator != null){
             return  false;
            }
         }
      }
      return true;
   }

   public void caseRecordApproval(ProcessCaseRepairModel processCaseRepairModel, User user) {

      //流程审批走之前的审批链中的节点
      FlowApproval firstFlowApproval = processBaseService.getFlowApprovalById(processCaseRepairModel.getApprovalId());
      FlowNode berforeFlowNode = new FlowNode();
      if (Objects.nonNull(firstFlowApproval)) {
         //获取走审批之前的节点信息
         berforeFlowNode = processBaseService.getFlowNodeByApproval(firstFlowApproval);
      }
      if (existApproval(user, berforeFlowNode)) {
         taskInfoService.saveFlowApprovalAndHistory(processCaseRepairModel.getCaseId(), processCaseRepairModel.getNodeState().toString(),
                 processCaseRepairModel.getNodeOpinion(), processCaseRepairModel.getStep(), processCaseRepairModel.getApprovalId(), user);
         CaseRepairApply caseRepairApply =    caseRepairApplyRepository.findOne(processCaseRepairModel.getCaseRepairApplyId());
         if (Objects.nonNull(caseRepairApply)) {
            caseRepairApply.setApprovalStatus(1);
            caseRepairApplyRepository.save(caseRepairApply);
         }
         FlowApproval flowApproval = processBaseService.getFlowApprovalById(processCaseRepairModel.getApprovalId());
         if (Objects.nonNull(flowApproval)) {
               //获 取走审批之后的节点
            FlowNode flowNode = processBaseService.getFlowNodeByApproval(flowApproval); //下一个节点
            if (flowNode.getId().equals(berforeFlowNode.getId())) { //确认是不是最后的节点
               //  是最后节点
               if (!processCaseRepairModel.getNodeState().equals(FlowHistory.NodeState.REFUSE.getValue())) {//
                  updateCaseRepairApply(processCaseRepairModel, user);
               }
            } else {
               //  不是最后节点
               if (!processCaseRepairModel.getNodeState().equals(FlowHistory.NodeState.REJECT.getValue())) {
                  ApplyApprovalSuccess(processCaseRepairModel, user);
               }
            }
         }
      }else{
         throw new RuntimeException("该用户不是信修审批的角色");
      }

   }









   /**
    * @Description :更新信修状态(最后节点)
    */
   public void updateCaseRepairApply(ProcessCaseRepairModel processCaseRepairModel, User user) {

  CaseRepairApply  caseRepairApply =       caseRepairApplyRepository.findOne(QCaseRepairApply.caseRepairApply.caseId.eq(processCaseRepairModel.getCaseId()).and(QCaseRepairApply.caseRepairApply.approvalStatus.eq(1)));
      if (Objects.nonNull(caseRepairApply)) {
         Integer turnApprovalStatus = null;
         CaseInfo caseInfo =null;
         caseInfo = caseInfoRepository.findOne(QCaseInfo.caseInfo.id.eq(processCaseRepairModel.getCaseId()));
         if(processCaseRepairModel.getNodeState() == 0){//同意
            turnApprovalStatus =Integer.valueOf(CaseRepairApply.AapprovalStatus.SUCCESS.getValue());
            //  同意时插入信修案件
            CaseRepair repair = caseRepairRepository.findOne(QCaseRepair.caseRepair.caseId.id.eq(caseInfo.getId()).and(QCaseRepair.caseRepair.repairStatus.eq(187)));//  防止重复提交
            if(null != repair){
                                     //  存在待修复的案件不再添加
            }else {                 // 不存在就添加
               CaseRepair caseRepair = new CaseRepair();
               caseRepair.setCaseId(caseInfo);
               caseRepair.setCompanyCode(caseInfo.getCompanyCode());
               caseRepair.setRepairStatus(CaseRepair.CaseRepairStatus.REPAIRING.getValue());
               caseRepair.setOperator(user);
               caseRepair.setOperatorTime(new Date());
               caseRepair.setCaseRepairRecordList(new ArrayList<>());
               caseRepair.setLoanInvoiceNumber(caseInfo.getLoanInvoiceNumber());
               caseRepair.setRepairApplyId(caseRepairApply.getId());
               caseRepair.setReadStatus("0");//  默认未读
               caseRepair.setCreateTime(ZWDateUtil.getNowDateTime());
               CaseRepair save = caseRepairRepository.save(caseRepair);

            }
         }else {//拒绝
            turnApprovalStatus = Integer.valueOf(CaseRepairApply.AapprovalStatus.ERR_FEID.getValue());
         }

         caseRepairApply.setApprovalStatus(turnApprovalStatus);//审核状态
         caseRepairApply.setRepairApplyRemark(processCaseRepairModel.getNodeOpinion());//审批意见
         caseRepairApply.setApprovalUser(user.getRealName());//操作员
         caseRepairApply.setApprovalTime(new Date());
         caseRepairApply.setApprovalId(processCaseRepairModel.getApprovalId());
         caseRepairApplyRepository.save(caseRepairApply);
         //  通过之后删除申请记录
        // caseRepairApplyRepository.delete(caseRepairApply.getId());
         //  插入审批记录
         CaseRepairApprovalRecord approvalRecord = new CaseRepairApprovalRecord();
         approvalRecord.setApprovalId(processCaseRepairModel.getApprovalId());//  流程id
         approvalRecord.setApprovalOpinion(processCaseRepairModel.getNodeOpinion());//  审批意见
         approvalRecord.setApprovalStatus(turnApprovalStatus);// 审批状态
         approvalRecord.setApprovalTime(ZWDateUtil.getNowDateTime());//  审批时间
         approvalRecord.setApprovalUser(user.getRealName());//  操作员
         approvalRecord.setCaseNumber(caseInfo.getCaseNumber());  //  案件号
         approvalRecord.setLoanInvoiceNumber(caseInfo.getLoanInvoiceNumber()); //  借据号
         approvalRecord.setRepairId(caseRepairApply.getId()); //  申请信修案件id
         caseRepairApprovalRecordRepository.save(approvalRecord);
      }
   }

   /**
    * @Description :更新信修状态(不是最后节点)
    */
   public void ApplyApprovalSuccess(ProcessCaseRepairModel processCaseRepairModel, User user) {

     // CaseRepairApply  caseRepairApply =       caseRepairApplyRepository.findOne(QCaseRepairApply.caseRepairApply.caseId.eq(processCaseRepairModel.getCaseId()).and(QCaseRepairApply.caseRepairApply.approvalStatus.eq(1)));
      CaseRepairApply caseRepairApply = caseRepairApplyRepository.findOne(processCaseRepairModel.getCaseRepairApplyId());
      if (Objects.nonNull(caseRepairApply) ) {
     //  CaseInfo caseInfo = caseInfoRepository.findOne(QCaseInfo.caseInfo.id.eq(processCaseRepairModel.getCaseId()));
         Integer turnApprovalStatus = null;
         if(processCaseRepairModel.getNodeState() == 0){//同意
            turnApprovalStatus =Integer.valueOf(CaseRepairApply.AapprovalStatus.SUCCESS.getValue());

         }else {//拒绝
            turnApprovalStatus = Integer.valueOf(CaseRepairApply.AapprovalStatus.ERR_FEID.getValue());
         }
         caseRepairApply.setApprovalStatus(turnApprovalStatus);//审核状态
         caseRepairApply.setRepairApplyRemark(processCaseRepairModel.getNodeOpinion());//审批意见
         caseRepairApply.setApprovalUser(user.getRealName());//操作员
         caseRepairApply.setApprovalTime(caseRepairApply.getApprovalTime());//  审批时间
         caseRepairApply.setApprovalId(processCaseRepairModel.getApprovalId());
         caseRepairApplyRepository.save(caseRepairApply);
         //  插入审批记录
         CaseRepairApprovalRecord approvalRecord = new CaseRepairApprovalRecord();
         approvalRecord.setApprovalId(processCaseRepairModel.getApprovalId());//  流程id
         approvalRecord.setApprovalOpinion(processCaseRepairModel.getNodeOpinion());//  审批意见
         approvalRecord.setApprovalStatus(turnApprovalStatus);// 审批状态
         approvalRecord.setApprovalTime(ZWDateUtil.getNowDateTime());//  审批时间
         approvalRecord.setApprovalUser(user.getRealName());//  操作员
         approvalRecord.setCaseNumber(caseRepairApply.getCaseNumber());  //  案件号
         approvalRecord.setLoanInvoiceNumber(caseRepairApply.getLoanInvoiceNumber()); //  借据号
         approvalRecord.setRepairId(caseRepairApply.getId()); //  申请信修案件id
         caseRepairApprovalRecordRepository.save(approvalRecord);
      }
   }



   /**
    * 判断该用户是否是审批人
    *
    * @return
    */
   public boolean existApproval(User user, FlowNode flowNode) {
      Set<Role> set = user.getRoles();
      Iterator<Role> it = set.iterator();
      while (it.hasNext()) {
         if (it.next().getId().equals(flowNode.getRoleId())) {

            return true;
         }
      }
      return false;
   }

   public List<CaseRepairApplyModel> getApplyCaseRepairList(String taskId, User user) {

      List<CaseRepairApplyModel> list = new ArrayList<>();
      CaseRepairApplyModel caseRepairApplyModel = null;
      //  所有流程
      List<FlowApproval> flowApprovalList = taskInfoService.getCaseNumberByUser(user, taskId);
      if (flowApprovalList != null && !flowApprovalList.isEmpty()) {
         for (FlowApproval flowApproval : flowApprovalList) {
            FlowNode flowNode = processBaseService.getFlowNodeByApproval(flowApproval);
            if (Objects.nonNull(flowNode)) {
               FlowTask flowTask = processBaseService.getFlowTaskByNode(flowNode);//为了获取任务名称
               Role role = roleRepository.findOne(flowNode.getRoleId());//为了获取角色名称
               if (Objects.nonNull(flowTask)) {
                  String caseNumber = flowApproval.getCaseNumber();
                  Iterator<CaseRepairApply> iterator = caseRepairApplyRepository.findAll(QCaseRepairApply.caseRepairApply.approvalId.eq(flowApproval.getId())).iterator();
                  if (iterator.hasNext()) {
                     CaseRepairApply repairApply = iterator.next();
                     CaseInfo caseInfo = caseInfoRepository.findOne(repairApply.getCaseId());
                     if(Objects.nonNull(caseInfo)){
                        Personal personal = personalRepository.findOne(caseInfo.getPersonalInfo().getId());
                        caseRepairApplyModel = new CaseRepairApplyModel();
                        caseRepairApplyModel.setCaseRepairApplyId(repairApply.getId());
                        caseRepairApplyModel.setCaseId(repairApply.getCaseId());
                        caseRepairApplyModel.setCaseNumber(caseInfo.getCaseNumber());
                        caseRepairApplyModel.setPersonalMobileNo(personal.getMobileNo());
                        caseRepairApplyModel.setPersonalIdCard(personal.getCertificatesNumber());
                        caseRepairApplyModel.setOverdueAmount(caseInfo.getOverdueAmount());
                        caseRepairApplyModel.setPersonalName(personal.getName());
                        caseRepairApplyModel.setPersonalMobileNo(personal.getMobileNo());
                        caseRepairApplyModel.setOverdueDays(caseInfo.getOverdueDays());
                        caseRepairApplyModel.setApplyTime(repairApply.getApplyTime());
                        caseRepairApplyModel.setApprovalId(repairApply.getApprovalId());
                        caseRepairApplyModel.setLoanInvoiceNumber(caseInfo.getLoanInvoiceNumber());
                        caseRepairApplyModel.setPayStatus(caseInfo.getPayStatus());
                        caseRepairApplyModel.setLetterDescribe(repairApply.getLetterDescribe());
                        caseRepairApplyModel.setOverduePeriods(caseInfo.getOverduePeriods());//逾期阶段
                        caseRepairApplyModel.setApprovalStatus(repairApply.getApprovalStatus());// 审批状态
                        caseRepairApplyModel.setId(repairApply.getId());//   申请信修主键id
                        list.add(caseRepairApplyModel);
                       }
                  }
               }
            }
         }
      }
      Collections.sort(list,new Comparator<CaseRepairApplyModel>(){
         @Override
         public int compare(CaseRepairApplyModel o1, CaseRepairApplyModel o2) {
            int i = o2.getApplyTime().compareTo(o1.getApplyTime());// 降序
            return i;
         }
      });
  return list;
   }


   /**
    * 信修记录
    * @param loanInvoiceNumber
    * @return
    */

   public List<CaseRepairApplyModel> getCaseRepairRecordList(String loanInvoiceNumber) {
      ArrayList<CaseRepairApplyModel> list = new ArrayList<>();
      CaseRepairApplyModel caseRepairApplyModel =null;
      if(ObjectUtils.isEmpty(loanInvoiceNumber)){
         list.add(null);
      }else{
            Iterator<CaseRepairApply> repairApplies = caseRepairApplyRepository.findAll(QCaseRepairApply.caseRepairApply.loanInvoiceNumber.eq(loanInvoiceNumber)).iterator();

            while (repairApplies.hasNext())
            {
               CaseRepairApply next = repairApplies.next();
               caseRepairApplyModel =   new CaseRepairApplyModel();
               caseRepairApplyModel.setApprovalStatus(next.getApprovalStatus());
               caseRepairApplyModel.setApplyTime(next.getApplyTime());
               caseRepairApplyModel.setApplyUser(next.getApplyUser());
               caseRepairApplyModel.setLoanInvoiceNumber(next.getLoanInvoiceNumber());
               caseRepairApplyModel.setLetterDescribe(next.getLetterDescribe());
               caseRepairApplyModel.setId(next.getId());
               list.add(caseRepairApplyModel);
            }
      }
      Collections.sort(list,new Comparator<CaseRepairApplyModel>(){
               @Override
               public int compare(CaseRepairApplyModel o1, CaseRepairApplyModel o2) {
                  int i = o2.getApplyTime().compareTo(o1.getApplyTime());// 降序
                  return i;
               }
      });
      return  list;
   }
   public List<CaseRepairApplyModel> getCaseRepairByCaseNumberRecordList(String caseNumber) {
      ArrayList<CaseRepairApplyModel> list = new ArrayList<>();
      CaseRepairApplyModel caseRepairApplyModel =null;
      if(ObjectUtils.isEmpty(caseNumber)){
         list.add(null);
      }else{
         Iterator<CaseRepairApply> repairApplies = caseRepairApplyRepository.findAll(QCaseRepairApply.caseRepairApply.caseNumber.eq(caseNumber)).iterator();

         while (repairApplies.hasNext())
         {
            CaseRepairApply next = repairApplies.next();
            caseRepairApplyModel =   new CaseRepairApplyModel();
            caseRepairApplyModel.setApprovalStatus(next.getApprovalStatus());
            caseRepairApplyModel.setApplyTime(next.getApplyTime());
            caseRepairApplyModel.setApplyUser(next.getApplyUser());
            caseRepairApplyModel.setLoanInvoiceNumber(next.getLoanInvoiceNumber());
            caseRepairApplyModel.setLetterDescribe(next.getLetterDescribe());
            caseRepairApplyModel.setId(next.getId());
            list.add(caseRepairApplyModel);
         }
      }
      Collections.sort(list,new Comparator<CaseRepairApplyModel>(){
         @Override
         public int compare(CaseRepairApplyModel o1, CaseRepairApplyModel o2) {
            int i = o2.getApplyTime().compareTo(o1.getApplyTime());// 降序
            return i;
         }
      });
      return  list;
   }

   /**
    *  信修查看
    * @param loanInvoiceNumber
    * @return
    */

   public List<CaseRepairApplyModel> getRepairCaseRecordList(String loanInvoiceNumber) {

      ArrayList<CaseRepairApplyModel> list = new ArrayList<>();
      if(ObjectUtils.isEmpty(loanInvoiceNumber)){
         list.add(null);
      }else {

         Iterator<CaseRepair> repairIterator = caseRepairRepository.findAll(QCaseRepair.caseRepair.loanInvoiceNumber.eq(loanInvoiceNumber).and(QCaseRepair.caseRepair.repairStatus.eq(188))).iterator();
         while (repairIterator.hasNext()){
            CaseRepair next = repairIterator.next();
            CaseRepairApplyModel caseRepairApplyModel = new CaseRepairApplyModel();
            CaseRepairRecord caseRepairRecord = caseRepairRecordRepository.findOne(QCaseRepairRecord.caseRepairRecord.repairFileId.eq(next.getId()));
            if(ObjectUtils.isEmpty(caseRepairRecord)){
               caseRepairApplyModel.setFileUrl(null);
            }else{
               caseRepairApplyModel.setFileUrl(caseRepairRecord.getFileUrl());
            }
            caseRepairApplyModel.setOperator(next.getOperator().getRealName());
            caseRepairApplyModel.setReadStatus(next.getReadStatus());
            caseRepairApplyModel.setOperatorTime(next.getOperatorTime());
            caseRepairApplyModel.setRepairContent(next.getRepairContent()==null?null:next.getRepairContent());
            list.add(caseRepairApplyModel);
         }
      }
           return  list;
   }

   /**
    * 审批记录
    * @param id
    * @return
    */
   public List<CaseRepairApplyModel> getRepairApprovalRecordList(String id,User  user) {
      ArrayList<CaseRepairApplyModel> list = new ArrayList<>();
      if(ObjectUtils.isEmpty(id)){
  //       list.add(null);
      }else {
         CaseRepairApplyModel caseRepairApplyModel1 = new CaseRepairApplyModel();
        // final Iterator<CaseRepair> iterator = caseRepairRepository.findAll(QCaseRepair.caseRepair.repairApplyId.eq(id)).iterator();
         //CaseRepairApply repairApplys = caseRepairApplyRepository.findOne(id);
         Iterator<CaseRepairApprovalRecord> all = caseRepairApprovalRecordRepository.findAll(QCaseRepairApprovalRecord.caseRepairApprovalRecord.repairId.eq(id)).iterator();

         while (all.hasNext()){
            CaseRepairApplyModel caseRepairApplyModel = new CaseRepairApplyModel();
            CaseRepairApprovalRecord  caseRepairApprovalRecord =    all.next();
            caseRepairApplyModel.setApprovalTime(caseRepairApprovalRecord.getApprovalTime());
            caseRepairApplyModel.setOperator(caseRepairApprovalRecord.getApprovalUser());
           if( caseRepairApprovalRecord.getApprovalStatus() == 1){
              caseRepairApplyModel.setApprovalStatus(119);
           }else if (caseRepairApprovalRecord.getApprovalStatus() == 2){
              caseRepairApplyModel.setApprovalStatus(121);
           }else if(caseRepairApprovalRecord.getApprovalStatus() == 3){
              caseRepairApplyModel.setApprovalStatus(122);
           }else{
              caseRepairApplyModel.setApprovalStatus(120);
           }
            caseRepairApplyModel.setApprovalOpinion(caseRepairApprovalRecord.getApprovalOpinion());
            list.add(caseRepairApplyModel);
         }
      }
      return list;
   }

   public void updateStatus(String loanInvoiceNumber) {
      Iterator<CaseRepair> repairIterator = caseRepairRepository.findAll(QCaseRepair.caseRepair.loanInvoiceNumber.eq(loanInvoiceNumber)).iterator();
      try{
         while (repairIterator.hasNext()){
            CaseRepair next = repairIterator.next();
            next.setReadStatus("1");
            caseRepairRepository.save(next);
         }
      }catch (Exception e){
         e.printStackTrace();
         throw new RuntimeException("修给状态失败");
      }

   }
}
