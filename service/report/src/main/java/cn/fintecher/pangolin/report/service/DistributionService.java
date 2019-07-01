package cn.fintecher.pangolin.report.service;

import cn.fintecher.pangolin.entity.*;
import cn.fintecher.pangolin.report.entity.*;
import cn.fintecher.pangolin.report.mapper.CaseInfoMapper;
import cn.fintecher.pangolin.report.mapper.CaseTurnRecordMapper;
import cn.fintecher.pangolin.report.mapper.QueryDistributionMapper;
import cn.fintecher.pangolin.report.model.AreaCaseNumberModel;
import cn.fintecher.pangolin.report.model.OutSourceNumberModel;
import cn.fintecher.pangolin.report.model.distribution.DistributionCaseInfoByPersionModel;
import cn.fintecher.pangolin.report.model.distribution.DistributionCaseParam;
import cn.fintecher.pangolin.report.model.distribution.OutSourceDistributionModel;
import cn.fintecher.pangolin.report.model.distribution.QueryDistributionCaseInfoModel;
import cn.fintecher.pangolin.util.ZWDateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;


@Service("distributionService")
public class DistributionService {

    final Logger log = LoggerFactory.getLogger(DistributionService.class);

    @Autowired
    private QueryDistributionMapper queryDistributionMapper;

    @Autowired
    private CaseInfoMapper caseInfoMapper;

    @Autowired
    private CaseTurnRecordMapper caseTurnRecordMapper;

    @Autowired
    RestTemplate restTemplate;


    /**
     * 案件分配
     */
    @Transactional
    public void distributionCases(){
        //删除临时表
        queryDistributionMapper.deleteOutSourceWhip();
        Map<String,Integer> countMap = new HashMap<>();
        //获取待分配的委外案件，并将同一个客户的案件整合
        List<DistributionCaseInfoByPersionModel> list = getDistributionCaseInfo();
        //将正在委外催收的同一个客户分配出去
        List<DistributionCaseInfoByPersionModel> surpluslist = getSurplusDisCaseInfo(list);
        //分配具有相同案件的客户
        List<DistributionCaseInfoByPersionModel> surPersonals = samePersonalDistribution(surpluslist,countMap);
        //分配剩余的案件
        SurplusDistribution(surPersonals,countMap);
    }

    /**
     * 委外待分配案件预分配确定
     * @param user
     */
    @Transactional
    public void savePredistributionOutSource(List<String> list,User user){

        //获取待分配的委外案件
        List<OutSourceDistributionModel> outSourceDistributionModelList =
                queryDistributionMapper.findOutSourceDistritbution(list);
        String batch = getSystemTime();
        Date date = new Date();
        for(OutSourceDistributionModel outSourceDistributionModel : outSourceDistributionModelList){
            //查询出预分配委外方信息
            List<OutSourceWhip> whipList = queryDistributionMapper.selectOutSourceWhipByCaseId(outSourceDistributionModel.getCaseId());
            if(whipList != null && whipList.size() != 0){
                OutSourceWhip outSourceWhip = whipList.get(0);//一般一条案件在临时表中只会出现一次
                //查询委外池中对应的案件信息
                List<OutSourcePoolReport> poolList = queryDistributionMapper.selectOutsourcePoolByCaseId(outSourceDistributionModel.getCaseId());
                for(OutSourcePoolReport outsourcePool : poolList){
                    //需要更新的内容
                    MultiValueMap<String, Object> data = new LinkedMultiValueMap<>();
                    data.add("caseId", outSourceDistributionModel.getCaseId());
                    ResponseEntity<Date> enty = restTemplate.postForEntity("http://business-service/api/caseInfoResource/getOverOutSourceTimeByCaseId",data,Date.class);
                    if(enty != null){
                        outsourcePool.setOverOutsourceTime(enty.getBody());
                    }
                    outsourcePool.setOutId(outSourceWhip.getOutId());//委外方
                    outsourcePool.setOutStatus(OutsourcePool.OutStatus.OUTSIDING.getCode());//委外状态
                    outsourcePool.setOperateTime(date);//操作时间
                    outsourcePool.setOutTime(date);//委外时间
                    outsourcePool.setOperator(user.getUserName());//操作人
                    outsourcePool.setOutBatch(batch);//委外批次号
                    queryDistributionMapper.updateOutSourcePool(outsourcePool);


                    // 添加案件流转记录
                    cn.fintecher.pangolin.report.entity.CaseInfo infoById = caseInfoMapper.findCaseInfoById(outSourceDistributionModel.getCaseId());
                    CaseTurnRecord caseTurnRecord = new CaseTurnRecord();
                    BeanUtils.copyProperties(infoById, caseTurnRecord); //将案件信息复制到流转记录
                    caseTurnRecord.setId(null); //主键置空
                    caseTurnRecord.setCaseId(infoById.getId()); //案件ID
//                    caseTurnRecord.setDepartId(infoById.getDepartment().getId()); //部门ID
//                    if (Objects.nonNull(infoById.getCurrentCollector())) {
//                        caseTurnRecord.setReceiveDeptName(infoById.getCurrentCollector().getDepartment().getName()); //接收部门名称
//                        caseTurnRecord.setReceiveUserId(infoById.getCurrentCollector().getId()); //接收人ID
//                        caseTurnRecord.setReceiveUserRealName(infoById.getCurrentCollector().getRealName()); //接受人名称
//                    } else {
//                        caseTurnRecord.setReceiveDeptName(infoById.getDepartment().getName());
//                    }
                    caseTurnRecord.setReceiveUserId(outSourceDistributionModel.getOutId());
                    caseTurnRecord.setReceiveUserRealName(outSourceDistributionModel.getOutName());
                    caseTurnRecord.setOperatorUserName(user.getUserName()); //操作员用户名
                    caseTurnRecord.setApplyName(user.getRealName());
                    caseTurnRecord.setOperatorTime(ZWDateUtil.getNowDateTime()); //操作时间
                    caseTurnRecord.setCirculationType(2); //流转类型 2-正常流转
                    caseTurnRecordMapper.save(caseTurnRecord);


                    //保存历史表
                    saveHistoryOutSourceDistribution(outSourceDistributionModel.getPersonalId(),outSourceWhip.getOutId());
                    //删除临时表数据
                    queryDistributionMapper.deleteOutSourceWhipByCaseId(outsourcePool.getCaseId());
                    MultiValueMap<String, Object> param = new LinkedMultiValueMap<>();
                    param.add("caseId", outSourceDistributionModel.getCaseId());
                    restTemplate.postForEntity("http://business-service/api/caseInfoResource/saveCaseInfoDateByOutSource",param,Void.class);
                }
            }
        }
    }

    /**
     * 查询分配结果
     * @return
     */
    public List<OutSourceNumberModel> selectDistributionResults(){
        List<OutSourceNumberModel> outSourceNumberModelList = new ArrayList<>();
        Map<String,String> map = getTimeBySysTime();//获取上一个月的开始和结束时间
        List<OutSourceNumberModel> list = queryDistributionMapper.selectOutSourceWhipRate(map.get("start"),map.get("end"));
        /*//将委外方分组
        Map<String,List<PredistributionOutModel>> outMap = new HashMap<>();//分组临时变量
        //将案件按照委外方分配
        for(int i=0;i<list.size();i++){
            List<PredistributionOutModel> pre = new ArrayList<>();
            pre.add(list.get(i));
            for(int j=list.size()-1;j>i;j--){
                if(list.get(i).getOutId().equals(list.get(j).getOutId())){
                    pre.add(list.get(j));
                    list.remove(j);
                }
            }
            outMap.put(list.get(i).getOutId(),pre);
        }
        Iterator<Map.Entry<String, List<PredistributionOutModel>>> iterator = outMap.entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry<String, List<PredistributionOutModel>> entry = iterator.next();
            List<PredistributionOutModel> bution = entry.getValue();
            OutSourceNumberModel outSourceNumberModel = new OutSourceNumberModel();
            outSourceNumberModel.setOutId(entry.getKey());
            if(bution != null && bution.size() != 0){
                outSourceNumberModel.setOutName(bution.get(0).getOutName());
            }
            outSourceNumberModel.setOutNumber(bution.size());

            List<AreaCaseNumberModel> arealist = new ArrayList<>();
            Map<Integer,List<PredistributionOutModel>> areaMap = new HashMap<>();//案件按照地区分组临时变量
            for(int n=0;n<bution.size();n++){
                List<PredistributionOutModel> outAreaList = new ArrayList<>();
                outAreaList.add(bution.get(n));
                for(int m=bution.size()-1;m>n;m--){
                    if(bution.get(n).getAreaId().equals(bution.get(m).getAreaId())){
                        outAreaList.add(bution.get(m));
                        bution.remove(m);
                    }
                }
                areaMap.put(bution.get(n).getAreaId(),outAreaList);
            }
            Iterator<Map.Entry<Integer, List<PredistributionOutModel>>> it = areaMap.entrySet().iterator();
            while (it.hasNext()){
                Map.Entry<Integer, List<PredistributionOutModel>> entry1 = it.next();
                AreaCaseNumberModel areaCaseNumberModel = new AreaCaseNumberModel();
                areaCaseNumberModel.setAreaId(entry1.getKey());
                if(entry1.getValue() != null && entry1.getValue().size() != 0){
                    areaCaseNumberModel.setAreaName(entry1.getValue().get(0).getAreaName());
                }
                areaCaseNumberModel.setNumber(entry1.getValue().size());
                arealist.add(areaCaseNumberModel);
            }

            outSourceNumberModel.setAreaList(arealist);
            outSourceNumberModelList.add(outSourceNumberModel);
        }*/
        return list;
    }

    /**
     * 查询分配结果详细信息
     * @return
     */
    public List<AreaCaseNumberModel> getOutSourceCaseInfoDetail(String outId){
        List<AreaCaseNumberModel> list = queryDistributionMapper.getOutSourceCaseInfoDetail(outId);
        return list;
    }


    /**
     * 展示分配好的案件信息
     * @param distributionCaseParam
     * @return
     */
    public List<QueryDistributionCaseInfoModel> queryDistributionCaseAll(DistributionCaseParam distributionCaseParam){
        List<QueryDistributionCaseInfoModel> queryDistributionCaseInfoModels = queryDistributionMapper.queryDistributionCaseInfo(distributionCaseParam);
        return queryDistributionCaseInfoModels;
    }

    /**
     * 获取待分配的委外案件，并将同一个客户的案件整合
     */
    public List<DistributionCaseInfoByPersionModel> getDistributionCaseInfo(){
        List<DistributionCaseInfoByPersionModel> list = new ArrayList<>();
        //获取待分配的委外案件
        List<OutSourceDistributionModel> outSourceDistributionModelList =
                queryDistributionMapper.findOutSourceDistritbution(null);
        Map<String,List<OutSourceDistributionModel>> userGroupMap=new HashMap<>();

        for(OutSourceDistributionModel outSourceDistributionModel : outSourceDistributionModelList){
            if(userGroupMap.containsKey(outSourceDistributionModel.getIdCard())){
                userGroupMap.get(outSourceDistributionModel.getIdCard()).add(outSourceDistributionModel);
            }else {
                List<OutSourceDistributionModel> userGroupList=new ArrayList<>();
                userGroupList.add(outSourceDistributionModel);
                userGroupMap.put(outSourceDistributionModel.getIdCard(),userGroupList);
            }
        }
        for(Map.Entry<String,List<OutSourceDistributionModel>> entry :userGroupMap.entrySet()){
            List<String> caseIdList=new ArrayList<>();
            DistributionCaseInfoByPersionModel distributionCaseInfoByPersionModel=new DistributionCaseInfoByPersionModel();
            for(OutSourceDistributionModel outSourceDistributionModel :entry.getValue()){
                distributionCaseInfoByPersionModel.setPersonalId(outSourceDistributionModel.getPersonalId());
                distributionCaseInfoByPersionModel.setAreaId(outSourceDistributionModel.getAreaId());
                distributionCaseInfoByPersionModel.setAreaName(outSourceDistributionModel.getAreaName());
                distributionCaseInfoByPersionModel.setIdCard(outSourceDistributionModel.getIdCard());
                distributionCaseInfoByPersionModel.setPersonalName(outSourceDistributionModel.getPersonalName());
                caseIdList.add(outSourceDistributionModel.getCaseId());
            }
            distributionCaseInfoByPersionModel.setCaseId(caseIdList);
            list.add(distributionCaseInfoByPersionModel);
        }
        return list;
    }

    /**
     * 将正在委外催收的同一个客户分配出去
     * @param distributionCaseInfoByPersionModelList
     * @return
     */
    @Transactional
    public List<DistributionCaseInfoByPersionModel> getSurplusDisCaseInfo(
            List<DistributionCaseInfoByPersionModel> distributionCaseInfoByPersionModelList){
        List<DistributionCaseInfoByPersionModel> list = new ArrayList<>();
        for(DistributionCaseInfoByPersionModel distributionCaseInfoByPersionModel : distributionCaseInfoByPersionModelList){
            //根据客户id查询对应的正在委外催收的委外方
            List<OutSourceDistributionModel> outSourceDistributionModelList =
                    queryDistributionMapper.findOutSourceDisByPersonalId(distributionCaseInfoByPersionModel.getIdCard());
            if(outSourceDistributionModelList != null && outSourceDistributionModelList.size() != 0){
                String outId = outSourceDistributionModelList.get(0).getOutId();
                List<String> caseIdList = distributionCaseInfoByPersionModel.getCaseId();
                for(String caseId : caseIdList){
                    saveDistribution(outId,caseId);
                }
                list.add(distributionCaseInfoByPersionModel);
            }
        }
        distributionCaseInfoByPersionModelList.removeAll(list);
        return distributionCaseInfoByPersionModelList;
    }


    /**
     * 将具有相同客户的不同案件分配出去
     * @param list
     */
    public List<DistributionCaseInfoByPersionModel>  samePersonalDistribution(List<DistributionCaseInfoByPersionModel> list, Map<String,Integer> countMap) {
        //将具有相同案件的客户归类
        Map<String,List<DistributionCaseInfoByPersionModel>> map = new HashMap<>();

        if(list != null && list.size() > 0){
            for(DistributionCaseInfoByPersionModel distributionCaseInfoByPersionModel : list){
                if(map.containsKey(distributionCaseInfoByPersionModel.getIdCard())){
                    map.get(distributionCaseInfoByPersionModel.getIdCard()).add(distributionCaseInfoByPersionModel);
                }else{
                    List<DistributionCaseInfoByPersionModel> samel = new ArrayList<>();//具有相同客户的案件集合
                    samel.add(distributionCaseInfoByPersionModel);
                    map.put(distributionCaseInfoByPersionModel.getIdCard(),samel);
                }
            }
        }

        List<DistributionCaseInfoByPersionModel> samelist = new ArrayList<>();//具有相同客户的案件集合
        Iterator<Map.Entry<String, List<DistributionCaseInfoByPersionModel>>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, List<DistributionCaseInfoByPersionModel>> entry = iterator.next();
            String idCard = entry.getKey();//客户身份证号
            List<DistributionCaseInfoByPersionModel> personals = entry.getValue();//同一个客户对应的不同案件
            //根据用户和案件地区查询符合的委外方
            List<Outsource> outsourceList = new ArrayList<>();
            for (int i = 0; i < personals.size(); i++) {
                outsourceList = getOutSourceByidCardAndAreaId(idCard, personals.get(0).getAreaId());
                if (outsourceList != null && outsourceList.size() > 0) {
                    break;
                }
            }
            if (personals.size() > 1) {
                samelist.addAll(personals);
                for (DistributionCaseInfoByPersionModel dis : personals) {
                    if (outsourceList != null && outsourceList.size() > 0) {
                        if (outsourceList.size() == 1) {
                            String outId = outsourceList.get(0).getId();
                            for (String caseId : dis.getCaseId()) {
                                saveDistribution(outId, caseId);
                            }
                            countMap.put(outId, personals.size());
                        } else {
                            String mixOutsourceId = outsourceList.get(0).getId();
                            if (countMap.containsKey(mixOutsourceId)) {
                                //获取符合该用户下所有委外方分配案件最少的委外方
                                for (int p = 1; p < outsourceList.size(); p++) {
                                    if (countMap.containsKey(outsourceList.get(p).getId())) {
                                        //选择出案件最少的委外方
                                        if (countMap.get(mixOutsourceId) > countMap.get(outsourceList.get(p).getId())) {
                                            mixOutsourceId = outsourceList.get(p).getId();
                                        }
                                    }
                                    for (String caseId : dis.getCaseId()) {
                                        saveDistribution(mixOutsourceId, caseId);
                                    }
                                    countMap.put(mixOutsourceId, personals.size());
                                }
                            } else {
                                for (String caseId : dis.getCaseId()) {
                                    saveDistribution(mixOutsourceId, caseId);
                                }
                                countMap.put(mixOutsourceId, personals.size());
                            }
                        }
                    }
                }
            }
        }
        list.removeAll(samelist);
        return list;
    }

    /**
     * 分配剩余的委外待分配案件
     * @param distributionCaseInfoByPersionModelList
     */
    @Transactional
    public void SurplusDistribution(List<DistributionCaseInfoByPersionModel> distributionCaseInfoByPersionModelList,Map<String,Integer> map){
        Map<Integer,List<DistributionCaseInfoByPersionModel>> disMap = distributionGroup(distributionCaseInfoByPersionModelList);
        Iterator<Map.Entry<Integer, List<DistributionCaseInfoByPersionModel>>> it = disMap.entrySet().iterator();
        while (it.hasNext()){
            Map.Entry<Integer, List<DistributionCaseInfoByPersionModel>> entry = it.next();
           // Integer areaId = entry.getKey();
//            Map<String,Integer> map = new HashMap<>();
            List<DistributionCaseInfoByPersionModel> list = entry.getValue();
            //统计出该城市的客户下有多少符合条件的委外方
            List<Outsource> outlister = new ArrayList<>();
            for(DistributionCaseInfoByPersionModel dis : list){
                List<Outsource> outsourceList = getOutSourceByidCardAndAreaId(dis.getIdCard(),dis.getAreaId());
                outlister.addAll(outsourceList);
            }
            Set<String> stringSet = outlister.stream().map(outsource ->{
                return outsource.getId();
            }).collect(Collectors.toSet());
            List<String> outlist = new ArrayList<>(stringSet);
            for(String id : outlist){
                if(!map.containsKey(id)){
                    map.put(id,0);
                }
            }
            //开始分配
            for(DistributionCaseInfoByPersionModel diser : list){
                //获取该用户下符合条件的委外方
                List<Outsource> outsourceList = getOutSourceByidCardAndAreaId(diser.getIdCard(),diser.getAreaId());
                if(outsourceList != null && outsourceList.size() > 0) {
                    if (outsourceList.size() == 1) { //符合改用的只有一个委外方
                        String outIder = outsourceList.get(0).getId();
                        //保存临时表
                        for (String caseId : diser.getCaseId()) {
                            saveDistribution(outIder, caseId);
                        }
                        map.put(outIder, map.get(outIder) + 1);
                    } else {
                        String mixOutsourceId = outsourceList.get(0).getId();
                        //获取符合该用户下所有委外方分配案件最少的委外方
                        for (int p = 1; p < outsourceList.size(); p++) {
                            if (map.get(mixOutsourceId) > map.get(outsourceList.get(p).getId())) {
                                mixOutsourceId = outsourceList.get(p).getId();
                            }
                        }
                        //保存临时表
                        for (String caseId : diser.getCaseId()) {
                            saveDistribution(mixOutsourceId, caseId);
                        }
                        map.put(mixOutsourceId, map.get(mixOutsourceId) + 1);
                    }
                }
            }
        }
    }

    /**
     * 保存历史表
     * @param outIder
     * @param caseId
     */
    @Transactional
    public void saveDistribution(String outIder,String caseId){
        OutSourceWhip outSourceWhip = new OutSourceWhip();
        outSourceWhip.setId(UUID.randomUUID().toString());
        outSourceWhip.setOutId(outIder);
        outSourceWhip.setCaseId(caseId);
        queryDistributionMapper.insertIntoOutSourceWhip(outSourceWhip);
    }

    /**
     * 将分配记录插入数据库
     * @param personalId
     * @param outId
     */
    @Transactional
    public void saveHistoryOutSourceDistribution(String personalId,String outId){
        HistoryOutSourceDistribution historyOutSourceDistribution = new HistoryOutSourceDistribution();
        historyOutSourceDistribution.setId(UUID.randomUUID().toString());
        historyOutSourceDistribution.setPersonalId(personalId);
        historyOutSourceDistribution.setOutId(outId);
        historyOutSourceDistribution.setOpertorTime(new Date());
        queryDistributionMapper.insertIntoHistoryOutSourceDistribution(historyOutSourceDistribution);
    }

    /**
     * 将分配剩余的案件按城市进行分组
     */
    public Map<Integer,List<DistributionCaseInfoByPersionModel>> distributionGroup(List<DistributionCaseInfoByPersionModel> dislist){
        Map<Integer,List<DistributionCaseInfoByPersionModel>> map = new HashMap<>();
        for(DistributionCaseInfoByPersionModel distributionCaseInfoByPersionModel : dislist){
            if(map.containsKey(distributionCaseInfoByPersionModel.getAreaId())){
                map.get(distributionCaseInfoByPersionModel.getAreaId()).add(distributionCaseInfoByPersionModel);
            }else{
                List<DistributionCaseInfoByPersionModel> list = new ArrayList<>();
                list.add(distributionCaseInfoByPersionModel);
                map.put(distributionCaseInfoByPersionModel.getAreaId(),list);
            }
        }
        return map;
    }

    /**
     * 将符合地区的委外方按照级别分组
     * @param areaId
     * @return
     */
    public Map<Integer,List<Outsource>> selectOutSourceByAreaId(Integer areaId){
        //根据地区查询对应的委外方
        List<Outsource> outsourceList = queryDistributionMapper.findOutSourceByAreaId(areaId);
        Map<Integer,List<Outsource>> map = new HashMap<>();
        List<Integer> list = new ArrayList<>();//级别去重,进行分组的key
        for(Outsource outsource : outsourceList){
            if(!list.contains(outsource.getOutsLevel())){
                list.add(outsource.getOutsLevel());
            }
        }
        for(Integer count : list){
            List<Outsource> outlist = new ArrayList<>();
            //将级别相同的委外放到一个集合中
            for(Outsource out : outsourceList){
                if(count.equals(out.getOutsLevel())){
                    outlist.add(out);
                }
            }
            map.put(count,outlist);
        }
        return map;
    }

    /**
     * 查询出符合该用户的委外方
     * @param idCard
     * @param areaId
     */
    public List<Outsource> getOutSourceByidCardAndAreaId(String idCard,Integer areaId){
        List<Outsource> list = new ArrayList<>();
        //根据客户身份证号查询所有的委外历史
        List<HistoryOutSourceDistribution> historyOutSourceDistributions = queryDistributionMapper.findHistoryOutSourceByidCard(idCard);
        // 将符合地区的委外方按照级别分组
        Map<Integer,List<Outsource>> map =  selectOutSourceByAreaId(areaId);
        if(map != null && map.size() > 0) {
            List<Integer> levelList = new ArrayList<>();//针对map的key值排序
            List<Outsource> allSource = new ArrayList<>();
            Iterator<Map.Entry<Integer, List<Outsource>>> it = map.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<Integer, List<Outsource>> entry = it.next();
                levelList.add(entry.getKey());
                allSource.addAll(entry.getValue());
            }
            Collections.sort(levelList);//对list进行排序
            List<Outsource> historylist = new ArrayList<>();
            for (HistoryOutSourceDistribution historyOutSourceDistribution : historyOutSourceDistributions) {
                Outsource outsource = getOutSourceById(historyOutSourceDistribution.getOutId(), allSource);
                historylist.add(outsource);
            }
            if (historyOutSourceDistributions == null || historyOutSourceDistributions.size() == 0) {
                //第一次分配
                list = map.get(levelList.get(0));
            } else {
                List<Outsource> outsHisAndSql = new ArrayList<>();//符合不重复条件的委外方
                for (Integer num : levelList) {
                    if (outsHisAndSql == null || outsHisAndSql.size() == 0) {
                        //查询同一个级别符合的委外方
                        List<Outsource> levelOuts = map.get(num);//委外方表中符合条件的
                        for (Outsource out : levelOuts) {
                            List<Outsource> whiplist = new ArrayList<>();//记录符合级别的前num条的委外方（临时变量）
                            //记录历史记录表中没有的委外方
                            if (!historylist.contains(out)) {
                                outsHisAndSql.add(out);
                            } else {
                                for (int i = 0; i < num; i++) {
                                    //只有历史表中的次数大于级别才会出现符合条件的委外方
                                    if (historylist.size() > num) {
                                        whiplist.add(historylist.get(i));
                                    }
                                }
                                if (whiplist.size() != 0 && !whiplist.contains(out)) {
                                    outsHisAndSql.add(out);
                                }
                            }
                        }
                    }
                }
                list = outsHisAndSql;
            }
        }
        return list;
    }


    /**
     * 根据委外id获取委外对象
     * @param outId
     * @param outsourceList
     * @return
     */
    public Outsource getOutSourceById(String outId,List<Outsource> outsourceList){
        Outsource outsource = null;
        for(Outsource outsource1 : outsourceList){
            if(outsource1.getId().equals(outId)){
                outsource = outsource1;
                break;
            }
        }
        return outsource;
    }

    /**
     * 获取批次号
     * @return
     */
    public String getSystemTime(){
        String str = new String();
        Date date = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
        str = df.format(date);
        return str;
    }


    /**
     * 获取当前月的开始和结束时间
     * @return
     */
    public Map<String,String>  getTimeBySysTime(){
        Map<String,String> map = new HashMap<>();
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar =Calendar.getInstance();

        calendar.add(Calendar.MONTH, -1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        String firstday = format.format(calendar.getTime());


        calendar.add(Calendar.MONTH, 1);
        calendar.set(Calendar.DAY_OF_MONTH, 0);
        String lastday = format.format(calendar.getTime());
        map.put("start",firstday);
        map.put("end",lastday);
        return map;
    }


}
