package cn.fintecher.pangolin.dataimp.util;

import cn.fintecher.pangolin.dataimp.entity.CollectionQueue;
import cn.fintecher.pangolin.dataimp.entity.ObtainTaticsStrategy;
import cn.fintecher.pangolin.dataimp.mapper.CaseInfoDistributedMapper;
import cn.fintecher.pangolin.dataimp.model.CaseInfoDistributedModelImp;
import cn.fintecher.pangolin.entity.CaseInfo;
import cn.fintecher.pangolin.entity.OutsourcePool;
import cn.fintecher.pangolin.entity.strategy.CaseNumberModel;
import cn.fintecher.pangolin.util.BeanUtils;
import cn.fintecher.pangolin.util.ZWDateUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author frank  braveheart1115@163.com
 * @Description: 这个里面组装所有的sql。
 * @Package cn.fintecher.pangolin.dataimp.util
 * @ClassName: cn.fintecher.pangolin.dataimp.util.SqlUtils
 * @date 2018年07月03日 12:00
 */
public class SqlUtils {

    private static final Logger log = Logger.getLogger(SqlUtils.class);


    /**
     * 处理caseInfo表的信息。添加或修改。
     */
    public static List<cn.fintecher.pangolin.dataimp.model.CaseInfo> getCaseInfoList(List<CaseInfoDistributedModelImp> list) {
        List<cn.fintecher.pangolin.dataimp.model.CaseInfo> resultList = new ArrayList<>();
        CaseInfoDistributedModelImp mode = null;
        for (int i = 0; i < list.size(); i++) {
            mode = list.get(i);
            cn.fintecher.pangolin.dataimp.model.CaseInfo caseinfo = new cn.fintecher.pangolin.dataimp.model.CaseInfo();
            BeanUtils.copyProperties(mode, caseinfo);
            resultList.add(caseinfo);
        }
        return resultList;
    }

    /**
     * 对于月初执行的，案件类型为[外访],[委外] 分为2种.其中 委外的只需要修改案件池类型，分案由 委外预分配进行分案。
     * a 历史逾期数据，即逾期期数>1的，查询case_info_distributed 表
     * b 逾期期数升级/压降 的 查询case_info 表
     *
     * @param caseInfoDistributedMapper
     * @param whereCondition
     * @return
     */
    public static List<CaseInfoDistributedModelImp> getCaseInfoResult(RestTemplate restTemplate, ObtainTaticsStrategy strategy, CollectionQueue collectionQueue, CaseInfoDistributedMapper caseInfoDistributedMapper, String whereCondition) {
        List<CaseInfoDistributedModelImp> resultList = new ArrayList<>();
        List<CaseInfoDistributedModelImp> distributedList = caseInfoDistributedMapper.getCaseDistributedResultList(whereCondition);
        List<CaseInfoDistributedModelImp> caseInfoList = caseInfoDistributedMapper.getCaseInfoResultList(whereCondition);
        resultList.addAll(distributedList);
        resultList.addAll(caseInfoList);
        if (collectionQueue != null){
            for (CaseInfoDistributedModelImp modelImp : resultList) {
                modelImp.setQueueId(collectionQueue.getId());
                modelImp.setQueueName(collectionQueue.getName());
            }
        }
        Integer casePoolType = strategy.getCasePoolType();  // 案件池类型
        Integer caseType = strategy.getCaseType();  // 案件类型
        String deptId = strategy.getDepartId();  // 部门
        Integer collectionType = strategy.getCollectionType();  // 催收类型
        Integer collectionStatus = strategy.getCollectionStatus();  // 催收状态

        ResponseEntity<CaseNumberModel> caseNumberModelList = restTemplate.getForEntity("http://business-service/api/processCaseInfoController/getApprovalCaseNumber" , CaseNumberModel.class);
        List<String> caseNumberList= new ArrayList<>(0);
        caseNumberList = caseNumberModelList.getBody().getCaseNumberList();
        List<CaseInfoDistributedModelImp> finalList = new ArrayList<>();
        CaseInfoDistributedModelImp model = null;
        String caseId=null;
        for (int i = 0; i < resultList.size(); i++) {
            model = resultList.get(i);
            if(!caseNumberList.contains(model.getCaseNumber())){
                Integer tempCasePoolType = resultList.get(i).getCasePoolType();
                Integer tempCollectionStatus = resultList.get(i).getCollectionStatus();
                Integer tempRecoverRemark = resultList.get(i).getRecoverRemark();   // 回收标志：0-未回收，1-已回收
                Integer tempCaseType = resultList.get(i).getCaseType();   // 案件类型
                caseId=model.getId();
                if(null == model.getAccountBalance()){
                    model.setAccountBalance(BigDecimal.ZERO);
                }
                model.setCasePoolType(casePoolType);
                model.setCaseType(caseType);
                model.setDepartId(StringUtils.isEmpty(deptId) ? null : deptId);
                model.setCollectionStatus(collectionStatus);
                model.setCollectionType(collectionType);
                /**
                 * 案件在执行夜间跑批任务后，会将符合条件的案件进行回收，回收后的回收标识设置为[1:已回收],在执行完分案策略后将这个值改为[未回收]
                 */
                model.setRecoverRemark(CaseInfo.RecoverRemark.NOT_RECOVERED.getValue());
                model.setCaseFollowInTime(new Date());
                Integer overduePeriods = model.getOverduePeriods();
                if (null != overduePeriods) {
                    model.setPayStatus("M" + model.getOverduePeriods());
                }
                CaseInfoDistributedModelImp result = new CaseInfoDistributedModelImp();
                BeanUtils.copyProperties(model, result);
                // 当为委外案件的时候，催收状态为【待分配】的时候，重新分案的时候需要将催收类型设置为空。
                if(caseType.intValue() == ObtainTaticsStrategy.CaseType.OUTSIDE_CASE.getValue().intValue()){
                    if(tempCollectionStatus.intValue() == CaseInfo.CollectionStatus.WAIT_FOR_DIS.getValue().intValue()){
                        result.setCurrentCollector(null);
                    }
                }
                /*// 外访案件如果是 【待催收】，重新分案的时候需要将催收类型设置为空。
                if (caseType.intValue() == ObtainTaticsStrategy.CaseType.VISIT_CASE.getValue().intValue()) {
                    if(tempCollectionStatus.intValue() == CaseInfo.CollectionStatus.WAITCOLLECTION.getValue().intValue()){
                        result.setCurrentCollector(null);
                    }
                }*/
                /**
                 * 对于委外待分配的案件,催收状态为 【待分配】;委外回收的案件 回收标识为【已回收】的案件需要添加进来，执行后面的分案操作。
                 * 委外案件的主要信息在case_info中，案件状态信息在 outsource_pool.out_status 中。判断的时候根据这个进行判断。
                 *
                 * 如果是外访案件，对于非留案的案件全部加入 finalList，对于有留案标识的，需要比较当前时间与留案时间。
                 * 判断案件的结案日期是否过了。如果当前日期等于或大于结案日期，需要对该案件进行重新分配，否则不分配。
                 * 重新分配的时候需要将案件的催收状态设置为 催收中。
                 */

                if (Objects.isNull(tempCasePoolType) || tempCasePoolType.equals(CaseInfo.CasePoolType.INNER.getValue())) {
                    if(false){
//                    if(tempCaseType.intValue() == ObtainTaticsStrategy.CaseType.VISIT_CASE.getValue().intValue()){
                        Integer leaveCaseFlag=result.getLeaveCaseFlag();
                        if(leaveCaseFlag.intValue()== CaseInfo.leaveCaseFlagEnum.NO_LEAVE.getValue().intValue()){
                            finalList.add(result);
                        }else if(leaveCaseFlag.intValue()== CaseInfo.leaveCaseFlagEnum.YES_LEAVE.getValue().intValue()){
                            boolean ff= ZWDateUtil.compareDate3(new Date(),result.getCloseDate());
                            if(ff){
                                result.setCollectionStatus(CaseInfo.CollectionStatus.WAIT_FOR_DIS.getValue());
                                result.setLeaveCaseFlag(CaseInfo.leaveCaseFlagEnum.NO_LEAVE.getValue());
                                /**
                                 * 回收后将结案日期设置为当前日期，该值为空的时候会报错。在案件留案的时候会给案件设置上留案日期。
                                 */
                                result.setCloseDate(new Date());
                                finalList.add(result);
                            }
                        }
                    }else {
                        finalList.add(result);
                    }
                } else if (tempCasePoolType.equals(CaseInfo.CasePoolType.OUTER.getValue())) {
                    // 委外案件池中的【委外状态】为待委外的那件需要
                    Integer outStatus=caseInfoDistributedMapper.getOutStatusByCaseId(caseId);
                    if(null != outStatus){
                        if (Objects.nonNull(tempCollectionStatus) && Objects.nonNull(tempRecoverRemark)) {
                            boolean flag1 = outStatus.equals(OutsourcePool.OutStatus.TO_OUTSIDE.getCode().intValue());
                            boolean flag2 = tempRecoverRemark.equals(CaseInfo.RecoverRemark.RECOVERED.getValue());
                            if (flag1 || flag2) {
                                finalList.add(result);
                            }
                        }
                    }
                }
        }

        }
        return finalList;
    }

    public static List<CaseInfoDistributedModelImp> getCaseInfoResult1(RestTemplate restTemplate, ObtainTaticsStrategy strategy,CollectionQueue collectionQueue, CaseInfoDistributedMapper caseInfoDistributedMapper, String whereCondition) {
        List<CaseInfoDistributedModelImp> resultList = new ArrayList<>();
        log.info("whereCondition" + whereCondition);
        List<CaseInfoDistributedModelImp> distributedList = caseInfoDistributedMapper.getCaseDistributedResultList(whereCondition);

        log.info("whereCondition" + whereCondition);
        List<CaseInfoDistributedModelImp> caseInfoList = caseInfoDistributedMapper.getCaseInfoResultList(whereCondition);

        resultList.addAll(distributedList);
        resultList.addAll(caseInfoList);

        if (collectionQueue != null){
            for (CaseInfoDistributedModelImp modelImp : resultList) {
                modelImp.setQueueId(collectionQueue.getId());
                modelImp.setQueueName(collectionQueue.getName());
            }
        }
        ResponseEntity<CaseNumberModel> caseNumberModelList = restTemplate.getForEntity("http://business-service/api/processCaseInfoController/getApprovalCaseNumber", CaseNumberModel.class);
        List<String> caseNumberList = new ArrayList<>(0);
        caseNumberList = caseNumberModelList.getBody().getCaseNumberList();
        List<CaseInfoDistributedModelImp> finalList = new ArrayList<>();
        CaseInfoDistributedModelImp model = null;
        String caseId = null;
        for (int i = 0; i < resultList.size(); i++) {
            model = resultList.get(i);
            if (!caseNumberList.contains(model.getCaseNumber())) {
                finalList.add(model);
/*                //3.资源池
                //1.内催待待分配   1.电催,2.外访
                //2.委外待分配
                if (model.getCasePoolType() == null) {
                    finalList.add(model);
                    continue;
                }

                if (model.getCasePoolType().equals(CaseInfo.CasePoolType.INNER.getValue())
                        && model.getCollectionStatus().equals(CaseInfo.CollectionStatus.WAIT_FOR_DIS.getValue())) {
                    finalList.add(model);
                    continue;
                }
                *//**
                 * 对于委外待分配的案件,催收状态为 【待分配】;委外回收的案件 回收标识为【已回收】的案件需要添加进来，执行后面的分案操作。
                 * 委外案件的主要信息在case_info中，案件状态信息在 outsource_pool.out_status 中。判断的时候根据这个进行判断。
                 *//*
                if (model.getCasePoolType().equals(CaseInfo.CasePoolType.OUTER.getValue())) {
//                    if (model.getCollectionStatus().equals(CaseInfo.CollectionStatus.WAIT_FOR_DIS.getValue())) {
//                        finalList.add(model);
//                    }
                    caseId = model.getId();
                    Integer outStatus = caseInfoDistributedMapper.getOutStatusByCaseId(caseId);
                    if (null != outStatus) {
                        Integer recoverRemark = model.getRecoverRemark();   // 回收标志：0-未回收，1-已回收
                        if (Objects.nonNull(model.getCollectionStatus()) && Objects.nonNull(recoverRemark)) {
                            boolean flag1 = outStatus.equals(OutsourcePool.OutStatus.TO_OUTSIDE.getCode().intValue());
                            boolean flag2 = recoverRemark.equals(CaseInfo.RecoverRemark.RECOVERED.getValue());
                            if (flag1 || flag2) {
                                finalList.add(model);
                                continue;
                            }
                        }
                    }
                }*/
            }
        }
        return finalList;
    }


    public static String divisionCase2User(String currentCollector, String caseNumber) {
        StringBuilder sb = new StringBuilder();
        sb.append("update case_info  set current_collector = '");
        sb.append(currentCollector);
        sb.append("' WHERE case_number = '");
        sb.append(caseNumber);
        sb.append("';");
        log.debug("关联催员与案件编号的sql为" + sb.toString());
        return sb.toString();
    }


}
