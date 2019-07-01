package cn.fintecher.pangolin.dataimp.service.impl;

import cn.fintecher.pangolin.dataimp.entity.ObtainTaticsStrategy;
import cn.fintecher.pangolin.dataimp.mapper.CaseInfoDao;
import cn.fintecher.pangolin.dataimp.mapper.CaseInfoDistributedModelImpMapper;
import cn.fintecher.pangolin.dataimp.model.CaseInfoDistributedModelImp;
import cn.fintecher.pangolin.entity.CaseRecordApply;
import cn.fintecher.pangolin.entity.CaseTurnRecord;
import cn.fintecher.pangolin.entity.Department;
import cn.fintecher.pangolin.entity.User;
import cn.fintecher.pangolin.model.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service("DoDivisionService")
public class DoDivisionService {
    private final Logger log = LoggerFactory.getLogger(DoDivisionService.class);
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private CaseInfoDao caseInfoDao;
    @Autowired
    private CaseInfoDistributedModelImpMapper caseInfoDistributedModelImpMapper;

    //    @Async
    public void doDivision(Integer poolType, List<CaseInfoDistributedModelImp> list, ObtainTaticsStrategy strategy, Department department) {
        log.info("list:{}", list.size());
        List<String> distributedIdList = new ArrayList<>();  //存放案件distributeid
        ArrayList<String> distributeCaseNumList = new ArrayList<>();
        ArrayList<CaseInfoDistributedModelImp> distributeMOdelList = new ArrayList<>();
        List caseIds = new ArrayList();

        List<CaseInfoDistributedModelImp> needFlow = new ArrayList<>();//需要流转的案件集合
        List<CaseInfoDistributedModelImp> noNeedFlow = new ArrayList<>();//不需要流转的案件集合
        List<CaseInfoDistributedModelImp> receivedList = new ArrayList<>();//回收案件集合
        List<String> receivedIds = new ArrayList<>();//回收案件id集合
        List<String> receivedCaseNumbers = new ArrayList<>();//回收案件caseNumber集合

        for (CaseInfoDistributedModelImp modelImp : list) {
            if (StringUtils.isNotBlank(modelImp.getDistributedId())) {
                distributedIdList.add(modelImp.getDistributedId());
                distributeCaseNumList.add(modelImp.getCaseNumber());
                distributeMOdelList.add(modelImp);
            } else if (StringUtils.isNotBlank(modelImp.getId())) {
                Integer casePoolType = modelImp.getCasePoolType();
                if (modelImp.getRecoverRemark().equals(1)) {
                    receivedList.add(modelImp);
                    receivedIds.add(modelImp.getId());
                    receivedCaseNumbers.add(modelImp.getCaseNumber());
                } else if (!Objects.equals(casePoolType, poolType)) {
                    needFlow.add(modelImp);
                } else {
                    noNeedFlow.add(modelImp);
                }
            }
        }
        //资源池的案件
        log.info("case distributedIdList:{}", distributedIdList.size());
        if (!distributedIdList.isEmpty()) {
            log.debug("开始执行资源池案件分案,案件数量:{}------------------", distributedIdList.size());
            if (poolType.equals(cn.fintecher.pangolin.entity.CaseInfo.CasePoolType.OUTER.getValue())) {
                //如果是委外,调用接口,将从资源池中查出来的案件,分配到委外待分配
                if (!distributedIdList.isEmpty()) {
                    ManualParams manualParams = new ManualParams();
                    manualParams.setCaseNumberList(distributeCaseNumList);
                    manualParams.setType(1);//分配类型：0-分配到内催，1-分配到委外
                    ResponseEntity manualResponseEntity = null;
                    try {
                        manualResponseEntity = restTemplate.postForEntity("http://business-service/api/caseInfoDistributeController/manualAllocationScheduled", manualParams, Void.class);
                        if (manualResponseEntity.getStatusCode().equals(HttpStatus.OK)) {
                            //执行完成  更新caseInfo队列名称  根据caseNum
                            updateCaseInfoByCaseNum(distributeMOdelList);
                        }
                    } catch (RestClientException e) {
                        log.error("自动分案[委外]失败:{}", e);
                        singDistributedException(distributedIdList);
                    }
                }
            } else if (poolType.equals(cn.fintecher.pangolin.entity.CaseInfo.CasePoolType.INNER.getValue())) {
                //如果是内催
                //  1.对于资源池中的案件,调用接口,分配到内催待分配
                if (!distributedIdList.isEmpty()) {
                    ManualParams manualParams = new ManualParams();
                    manualParams.setCaseNumberList(distributeCaseNumList);
                    manualParams.setType(0);//分配类型：0-分配到内催，1-分配到委外
                    ResponseEntity<List> manualResponseEntity = null;
                    try {
                        manualResponseEntity = restTemplate.postForEntity("http://business-service/api/caseInfoDistributeController/manualAllocationScheduled", manualParams, List.class);
                        if (manualResponseEntity.getStatusCode().equals(HttpStatus.OK)) {
                            caseIds = manualResponseEntity.getBody();
                            //执行完成  更新caseInfo队列名称  根据caseNum
                            updateCaseInfoByCaseNum(distributeMOdelList);
                        }
                    } catch (RestClientException e) {
                        log.error("自动分案[内催]失败{}", e);
                        singDistributedException(distributedIdList);
                    }
                }
                innerCaseAllocate(strategy, caseIds, null);
            }
            log.debug("资源池案件分案执行完毕------------------");
        }

        log.info("case receivedList:{}", receivedList.size());
        if (!receivedList.isEmpty()) {
            log.debug("回收案件数量:{}------------------", receivedList.size());
            if (poolType.equals(cn.fintecher.pangolin.entity.CaseInfo.CasePoolType.INNER.getValue())) {
                //走流转
                needFlow.addAll(receivedList);
            } else if (poolType.equals(cn.fintecher.pangolin.entity.CaseInfo.CasePoolType.OUTER.getValue())) {
                ReDisRecoverCaseParams reDisRecoverCaseParams = new ReDisRecoverCaseParams();
                reDisRecoverCaseParams.setType(1);
                reDisRecoverCaseParams.setIds(receivedCaseNumbers);
                log.debug("开始调用接口:重新分配回收案件,入参:{}", reDisRecoverCaseParams);
                ResponseEntity responseEntity = null;
                try {
                    responseEntity = restTemplate.postForEntity("http://business-service/api/caseReturnController/reDisRecoverCaseAuto", reDisRecoverCaseParams, Void.class);
                    if (responseEntity.getStatusCode().equals(HttpStatus.OK)) {
                        //执行完成  更新caseInfo队列名称  根据caseNum
                        updateCaseInfoByCaseNum(receivedList);
                    }
                    log.debug("调用接口:重新分配回收案件完毕,返回:{}", responseEntity);
                } catch (RestClientException e) {
                    singCaseInfoException(receivedIds);
                    log.error("调用接口出错(重新分配回收案件):{}", e);
                }
            }
        }

        List<String> needFlowIds = new ArrayList<>();
        for (CaseInfoDistributedModelImp modelImp : needFlow) {
            needFlowIds.add(modelImp.getId());
        }

        log.info("case noNeedFlow:{}", noNeedFlow.size());
        if (!noNeedFlow.isEmpty()) {
            log.debug("不需要流转的案件分案开始执行,案件数量:{}------------------", noNeedFlow.size());
            //1.内催--内催
            if (poolType.equals(cn.fintecher.pangolin.entity.CaseInfo.CasePoolType.INNER.getValue())) {
                //内催待分配集合
                List<CaseInfoDistributedModelImp> waitForDis = new ArrayList();
                List<String> waitForDisIds = new ArrayList<>();
                //内催催收中集合
                List<CaseInfoDistributedModelImp> collecting = new ArrayList();
                List<String> collectingIds = new ArrayList<>();
                for (CaseInfoDistributedModelImp modelImp : noNeedFlow) {
                    if (modelImp.getCollectionStatus().equals(cn.fintecher.pangolin.entity.CaseInfo.CollectionStatus.WAIT_FOR_DIS.getValue())) {
                        waitForDis.add(modelImp);
                        waitForDisIds.add(modelImp.getId());

                    } else if (modelImp.getCollectionStatus().equals(cn.fintecher.pangolin.entity.CaseInfo.CollectionStatus.WAITCOLLECTION.getValue())
                            || modelImp.getCollectionStatus().equals(cn.fintecher.pangolin.entity.CaseInfo.CollectionStatus.COLLECTIONING.getValue())) {
                        collecting.add(modelImp);
                        collectingIds.add(modelImp.getId());
                    }
                }
                //1.内催待分配
                log.info("case waitForDis:{}", waitForDis.size());
                innerCaseAllocate(strategy, waitForDisIds, waitForDis);
                //2.内催催收中
                log.info("case collecting:{}", collecting.size());
                Integer allotType = strategy.getAllotType();//hy-分配方式 按催收员分配,按机构分配
                if (allotType.equals(ObtainTaticsStrategy.AllotType.PERSON.getValue())) {
                    List<Integer> disNumList = distributePreview(strategy, collectingIds);
                    //电催或外访页面批量分配接口
                    BatchDistributeModel batchDistributeModel = new BatchDistributeModel();
                    batchDistributeModel.setCaseIds(collectingIds);
                    List<BatchInfoModel> batchInfoModelList = new ArrayList<>();
                    for (Integer count : disNumList) {
                        BatchInfoModel batchInfoModel = new BatchInfoModel();
                        batchInfoModel.setDistributionCount(count);
                    }
                    List<String> users = strategy.getUsers();
                    for (int i = 0; i < disNumList.size(); i++) {
                        Integer count = disNumList.get(i);
                        BatchInfoModel batchInfoModel = new BatchInfoModel();
                        batchInfoModel.setDistributionCount(count);
                        User user = new User();
                        user.setId(users.get(i));
                        user.setDepartment(department);
                        batchInfoModel.setCollectionUser(user);
                        batchInfoModelList.add(batchInfoModel);
                    }
                    batchDistributeModel.setBatchInfoModelList(batchInfoModelList);
                    //电催或外访页面批量分配
                    log.debug("电催或外访页面批量分配,入参batchDistributeModel:{}", batchDistributeModel);
                    ResponseEntity batchTelCaseResponse = null;
                    try {
                        batchTelCaseResponse = restTemplate.postForEntity("http://business-service/api/AccTelPoolController/batchTelCaseAuto", batchDistributeModel, Void.class);
                        log.debug("电催或外访页面批量分配执行完毕,返回batchTelCaseResponse:{}", batchTelCaseResponse);
                        if (batchTelCaseResponse.getStatusCode().equals(HttpStatus.OK)) {
                            updateCaseInfoByCaseNum(collecting);
                        }
                    } catch (RestClientException e) {
                        singCaseInfoException(collectingIds);
                        log.error("调用接口出错(电催或外访页面批量分配):{}", e);
                    }
                } else if (allotType.equals(ObtainTaticsStrategy.AllotType.DEPARTMENT.getValue())) {
                    // 按部门
                    AccCaseInfoDisModel distributeCaseInfoModel = new AccCaseInfoDisModel();//内催分配接口的实体类
                    distributeCaseInfoModel.setCaseIdList(collectingIds);
                    ArrayList<Integer> caseNumList1 = new ArrayList<>();
                    caseNumList1.add(collecting.size());
                    distributeCaseInfoModel.setCaseNumList(caseNumList1);
                    ArrayList<String> deptList = new ArrayList<>();
                    deptList.add(strategy.getDepartId());
                    distributeCaseInfoModel.setDepIdList(deptList);
                    distributeCaseInfoModel.setDisType(0);//按部门
                    distributeCaseInfoModel.setIsDebt(0);
                    distributeCaseInfoModel.setIsNumAvg(0);
                    distributeCaseInfoModel.setIsPlan(0);
                    distributeCaseInfoModel.setFlag(true);
                    ResponseEntity distributeResponseEntity = null;
                    try {
                        distributeResponseEntity = restTemplate.postForEntity("http://business-service/api/caseInfoController/distributeCaseInfoScheduled", distributeCaseInfoModel, Void.class);
                        if (distributeResponseEntity.getStatusCode().equals(HttpStatus.OK)) {
                            updateCaseInfoByCaseNum(collecting);
                        }
                    } catch (RestClientException e) {
                        //标记异常案件
                        log.error("调用接口出错:自动分案[内催]失败", e);
                        singCaseInfoException(collectingIds);
                    }
                }
                //2.委外--委外
            } else if (poolType.equals(cn.fintecher.pangolin.entity.CaseInfo.CasePoolType.OUTER.getValue())) {
                updateCaseInfoByCaseNum(noNeedFlow);
            }
            log.debug("不需要流转的案件分案执行完毕------------------");
        }
        //需要流转的案件
        log.info("case needFlow:{}", needFlow.size());
        if (!needFlow.isEmpty()) {
            log.debug("开始执行需要流转的案件分案,案件数量:{}------------------", needFlow.size());
            //先执行流转
            List<CaseRecordApply> applyList = new ArrayList<>();
            for (CaseInfoDistributedModelImp modelImp : needFlow) {
                CaseRecordApply caseRecordApply = new CaseRecordApply();
                caseRecordApply.setPoolType(poolType);
                caseRecordApply.setCaseNumber(modelImp.getCaseNumber());
                if (modelImp.getCasePoolType().equals(cn.fintecher.pangolin.entity.CaseInfo.CasePoolType.OUTER.getValue())) {
                    caseRecordApply.setSourceType(CaseTurnRecord.TurnFromPool.OUTER.getValue());
                }
                caseRecordApply.setCaseId(modelImp.getId());
                applyList.add(caseRecordApply);
            }
            ResponseEntity doFlowResponse = null;
            try {
                doFlowResponse = restTemplate.postForEntity("http://business-service/api/CaseRoamController/CaseRecordInfoDoFlow", applyList, Void.class);
                if (doFlowResponse.getStatusCode().equals(HttpStatus.OK)) {
                    //执行完成  更新caseInfo队列名称  根据caseNum
                    updateCaseInfoByCaseNum(needFlow);
                    if (poolType.equals(cn.fintecher.pangolin.entity.CaseInfo.CasePoolType.INNER.getValue())) {
                        List<String> waitForDisIds = new ArrayList<>();
                        for (CaseInfoDistributedModelImp modelImp : needFlow) {
                            if (modelImp.getCollectionStatus().equals(cn.fintecher.pangolin.entity.CaseInfo.CollectionStatus.WAIT_FOR_DIS.getValue())) {
                                waitForDisIds.add(modelImp.getId());
                            }
                        }
                        //内催待分配
                        innerCaseAllocate(strategy, waitForDisIds, null);
                    }
                }
                log.debug("需要流转的案件分案执行完毕------------------");
            } catch (RestClientException e) {
                singCaseInfoException(needFlowIds);
                log.error("调用接口出错(案件流转):{}", e);
            }
        }
    }

    private Integer updateCaseInfoByCaseNum(List<CaseInfoDistributedModelImp> list) {
        if (list == null || list.isEmpty()) {
            return 0;
        }
        int n = caseInfoDao.updateBatchByCaseNum(list);
        log.debug("批量更新队列名称:{}", n);
        return n;
    }

    //内催待分配案件分配
    private boolean innerCaseAllocate(ObtainTaticsStrategy strategy, List caseIds, List<CaseInfoDistributedModelImp> modelList) {
        Boolean flag = false;
        //  2.caseinfo中的案件,以及上一步分配到caseinfo中的案件  调用内催分配接口
        //先拿到caseIds
        //调用预分配接口
        Integer allotType = strategy.getAllotType();//hy-分配方式 按催收员分配,按机构分配
        AccCaseInfoDisModel distributeCaseInfoModel = new AccCaseInfoDisModel();//内催分配接口的实体类
        if (allotType.equals(ObtainTaticsStrategy.AllotType.PERSON.getValue())) {
            //预分配
            //调用分配接口
            List<Integer> disNumList = distributePreview(strategy, caseIds);
            distributeCaseInfoModel.setCaseIdList(caseIds);
            distributeCaseInfoModel.setCaseNumList(disNumList);
            distributeCaseInfoModel.setDisType(1);//按用户
            distributeCaseInfoModel.setIsDebt(0);
            Integer distributeType = getDistributeType(strategy);//1按数量 2按金额 3综合
            distributeCaseInfoModel.setIsNumAvg(distributeType);
            distributeCaseInfoModel.setIsPlan(0);
            distributeCaseInfoModel.setUserIdList(strategy.getUsers());
        } else if (allotType.equals(ObtainTaticsStrategy.AllotType.DEPARTMENT.getValue())) {
            distributeCaseInfoModel.setCaseIdList(caseIds);
            ArrayList<Integer> caseNumList1 = new ArrayList<>();
            caseNumList1.add(caseIds.size());
            distributeCaseInfoModel.setCaseNumList(caseNumList1);
            ArrayList<String> deptList = new ArrayList<>();
            deptList.add(strategy.getDepartId());
            distributeCaseInfoModel.setDepIdList(deptList);
            distributeCaseInfoModel.setDisType(0);//按部门
            distributeCaseInfoModel.setIsDebt(0);
            distributeCaseInfoModel.setIsNumAvg(0);
            distributeCaseInfoModel.setIsPlan(0);
        }
        distributeCaseInfoModel.setFlag(true);
        ResponseEntity distributeResponseEntity = null;
        try {
            distributeResponseEntity = restTemplate.postForEntity("http://business-service/api/caseInfoController/distributeCaseInfoScheduled", distributeCaseInfoModel, Void.class);
            if (distributeResponseEntity.getStatusCode().equals(HttpStatus.OK) && modelList != null) {
                //执行完成  更新caseInfo队列名称  根据caseNum
                updateCaseInfoByCaseNum(modelList);
            }
            flag = true;
        } catch (RestClientException e) {
            //标记异常案件
            log.error("自动分案[内催]失败{}", e);
            singCaseInfoException(caseIds);
            return flag;
        }
        return flag;
    }

    //待分配预览
    private List<Integer> distributePreview(ObtainTaticsStrategy strategy, List caseIds) {
        //获取分配预览 请求实体
        AccCaseInfoDisModel accCaseInfoDisModel = new AccCaseInfoDisModel();
        accCaseInfoDisModel.setDisType(1); //分配方式  按用户
        accCaseInfoDisModel.setCaseIdList(caseIds);
        List<String> strategyUsersIds = strategy.getUsers();
        accCaseInfoDisModel.setUserIdList(strategyUsersIds);
        accCaseInfoDisModel.setIsPlan(0);

        Integer distributeType = getDistributeType(strategy);//1按数量 2按金额 3综合
        accCaseInfoDisModel.setIsNumAvg(distributeType);
        List<Integer> disNumList = new ArrayList<>();//每个人分配的案件数量
        try {
            //调用business服务  内催待分配预览distributePreview   拿到numlist
            log.debug("待分配预览开始执行,入参accCaseInfoDisModel:{}", accCaseInfoDisModel);
            ResponseEntity<PreviewModel> entity1 = restTemplate.postForEntity("http://business-service/api/caseInfoController/distributePreviewScheduled", accCaseInfoDisModel, PreviewModel.class);
            log.debug("待分配预览执行完毕,返回entity1:{}", entity1);
            PreviewModel previewModel = entity1.getBody();
            //每个人分配的数量
            disNumList = previewModel.getNumList();
        } catch (RestClientException e) {
            log.error("调用接口出错(待分配预览):{}", e);
        }
        return disNumList;
    }


    //自动分案失败标记异常案件
    private void singCaseInfoException(List<String> caseIdList) {
        if (caseIdList != null && !caseIdList.isEmpty()) {
            int n = caseInfoDao.signExceptionCase(caseIdList);
            log.info("updateE {}", n);
        }
    }

    //自动分案失败标记异常案件
    private void singDistributedException(List<String> distributedIdList) {
        if (distributedIdList != null && !distributedIdList.isEmpty()) {
            int n = caseInfoDistributedModelImpMapper.signExceptionCase(distributedIdList);
            log.info("updateE {}", n);
        }
    }

    private Integer getDistributeType(ObtainTaticsStrategy strategy) {
        Integer userPattern = strategy.getUserPattern();//hy-分案策略
        Integer distributeType = null;//1按数量 2按金额 3综合
        if (userPattern.equals(ObtainTaticsStrategy.UserPattern.HOUSEHOLDS.getValue())) {
            distributeType = 1;
        }
        //分配策略: 按金额平均
        if (userPattern.equals(ObtainTaticsStrategy.UserPattern.MONEY.getValue())) {
            distributeType = 2;
        }
        //分配策略: 按综合平均
        if (userPattern.equals(ObtainTaticsStrategy.UserPattern.SYNTHESIZE.getValue())) {
            distributeType = 3;
        }
        return distributeType;
    }

}
