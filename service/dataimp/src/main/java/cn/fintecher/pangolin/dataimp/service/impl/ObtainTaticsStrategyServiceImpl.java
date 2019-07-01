package cn.fintecher.pangolin.dataimp.service.impl;

import cn.fintecher.pangolin.dataimp.entity.CaseDistributedResult;
import cn.fintecher.pangolin.dataimp.entity.CollectionQueue;
import cn.fintecher.pangolin.dataimp.entity.ObtainTaticsStrategy;
import cn.fintecher.pangolin.dataimp.mapper.CaseInfoDao;
import cn.fintecher.pangolin.dataimp.mapper.CaseInfoDistributedMapper;
import cn.fintecher.pangolin.dataimp.mapper.CaseInfoDistributedModelImpMapper;
import cn.fintecher.pangolin.dataimp.model.CaseInfo;
import cn.fintecher.pangolin.dataimp.model.CaseInfoDistributedModelImp;
import cn.fintecher.pangolin.dataimp.model.OutSourcePoolModel;
import cn.fintecher.pangolin.dataimp.model.request.ObtainTaticsStrategyRequest;
import cn.fintecher.pangolin.dataimp.repository.CollectionQueueRepository;
import cn.fintecher.pangolin.dataimp.repository.ObtainTaticsStrategyRepository;
import cn.fintecher.pangolin.dataimp.service.ObtainTaticsStrategyService;
import cn.fintecher.pangolin.dataimp.util.ObtainStrategyMysqlUtils;
import cn.fintecher.pangolin.dataimp.util.SqlUtils;
import cn.fintecher.pangolin.entity.Company;
import cn.fintecher.pangolin.entity.Department;
import cn.fintecher.pangolin.entity.OutsourcePool;
import cn.fintecher.pangolin.entity.User;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.regex.Pattern;

/**
 * @author frank  braveheart1115@163.com
 * @Description: 案件数据获取策略 service 接口实现类
 * 1 内催，委外，特殊，停催，这些是互斥的。诉讼和反欺诈与 内催，委外，特殊，停催并行。
 * 因此先过滤所有的案件，将诉讼与反欺诈的类型的直接创建任务，添加到对应的表中。
 * 2 然后依次执行停催，特殊，委外，内催的规则，如果执行完内催规则后还有剩余的案件则直接划分到内催中。
 * 暂时没有 停催，特殊 的规则，因此将2个的方法预留出来。
 * 3 拿到过滤规则后，直接将这些条件作为where语句的查询条件，将查询出来的案件类型修改为对应的值，然后再查询该类型的案件返回。
 * @Package cn.fintecher.pangolin.business.service.impl
 * @ClassName: cn.fintecher.pangolin.business.service.impl.ObtainTaticsStrategyServiceImpl
 * @date 2018年06月19日 15:08
 */
@Service("obtainTaticsStrategyService")
public class ObtainTaticsStrategyServiceImpl implements ObtainTaticsStrategyService {
    private final Logger log = LoggerFactory.getLogger(ObtainTaticsStrategyServiceImpl.class);
    private static final String ENTITY_NAME = "obtainTaticsStrategy";

    @Autowired
    private ObtainTaticsStrategyRepository obtainTaticsStrategyRepository;

    @Autowired
    private CollectionQueueRepository collectionQueueRepository;

    @Autowired
    private CaseInfoDistributedModelImpMapper caseInfoDistributedModelImpMapper;

    @Autowired
    private CaseInfoDistributedMapper caseInfoDistributedMapper;
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private CaseInfoDao caseInfoDao;
    @Autowired
    private DoDivisionService doDivisionService;
    /**
     * 新增/修改案件数据获取策略
     *
     * @param obtainTaticsStrategyRequest
     * @param token
     * @return
     */
    @Override
    public String addObtainTaticsStrategy(ObtainTaticsStrategyRequest obtainTaticsStrategyRequest, String token, User user) {
        String message="";
        /**
         * 判断策略是否重复，按照[案件类型][组别][催收员案件分配模式][分案模式][是否可用][产品类型]进行判断。
         * 如果没有策略返回true，已经存在策略返回false并弹出提示信息。
         */
        if(null == obtainTaticsStrategyRequest.getAllotType()){
            message="分配方式还没有选择，请重新选择";
        }
        if (obtainTaticsStrategyRequest.getAllotType().equals(ObtainTaticsStrategy.AllotType.PERSON.getValue()) && obtainTaticsStrategyRequest.getUserPattern()==null){
            message="分案策略还没有选择，请重新选择";
        }
        if(StringUtils.isEmpty(message)){
            String flag = "";
            StringBuilder sb = new StringBuilder();
            analysisRule(obtainTaticsStrategyRequest.getStrategyJson(), sb);
            String id = obtainTaticsStrategyRequest.getId();
                ObtainTaticsStrategy obtainTaticsStrategy = new ObtainTaticsStrategy();
            if ("null".equals(id) || StringUtils.isEmpty(id)) {
                flag = checkAllocationPatternIsExist(obtainTaticsStrategyRequest);
                obtainTaticsStrategyRequest.setId(String.valueOf(System.currentTimeMillis()));
                BeanUtils.copyProperties(obtainTaticsStrategyRequest,obtainTaticsStrategy);
                obtainTaticsStrategy.setCreateTime(new Date());
                obtainTaticsStrategy.setCreator(user.getRealName());
                obtainTaticsStrategy.setCreatorId(user.getId());
            }else {
                obtainTaticsStrategy = obtainTaticsStrategyRepository.findOne(id);
                if (!obtainTaticsStrategyRequest.getName().equals(obtainTaticsStrategy.getName())){
                    flag = checkAllocationPatternIsExist(obtainTaticsStrategyRequest);
                }
                BeanUtils.copyProperties(obtainTaticsStrategyRequest,obtainTaticsStrategy);
            }           if ("exist".equals(flag)) {
                message="该名称已存在";
                return message;
            }
            Integer casePoolType = null;
            Integer collectionType = null;
            Integer collectionStatus = null;
            if (obtainTaticsStrategy.getCaseType()!=null){
                casePoolType = ObtainStrategyMysqlUtils.getCasePoolTypeByCaseType(obtainTaticsStrategy.getCaseType());
            }
            if (obtainTaticsStrategy.getCaseType()!=null){
                collectionType = ObtainStrategyMysqlUtils.getCollectionTypeByCaseType(obtainTaticsStrategy.getCaseType());
            }
            if (obtainTaticsStrategy.getCaseType()!=null) {
                collectionStatus=ObtainStrategyMysqlUtils.getCollectionStatusByCaseType(obtainTaticsStrategy.getCaseType());
            }
            obtainTaticsStrategy.setCasePoolType(casePoolType);
            obtainTaticsStrategy.setCollectionType(collectionType);
            obtainTaticsStrategy.setCollectionStatus(collectionStatus);
            obtainTaticsStrategy.setStrategyJson(obtainTaticsStrategy.getStrategyJson());
            obtainTaticsStrategy.setUpdateBy(user.getRealName());
            obtainTaticsStrategy.setUpdateId(user.getId());
            obtainTaticsStrategy.setUpdateTime(new Date());
            obtainTaticsStrategyRepository.save(obtainTaticsStrategy);
        }
        return message;
    }
    /**
     * 新增/修改案件数据获取策略
     *
     * @param collectionQueue
     * @param token
     * @return
     */
    @Override
    public String addCollectionQueue(CollectionQueue collectionQueue, String token, User user) {
        String message = "";
        /**
         * 判断是否重复，队列编号和队列名称不能重复
         */

        String flag = "";
        StringBuilder sb = new StringBuilder();
        analysisRule(collectionQueue.getStrategyJson(), sb);
        String id = collectionQueue.getId();
        if ("null".equals(id) || StringUtils.isEmpty(id)) {
            //1.新增的时候校验
            flag = checkCollectionQueueIsExist(collectionQueue);
            collectionQueue.setId(String.valueOf(System.currentTimeMillis()));
            collectionQueue.setCreateTime(new Date());
            collectionQueue.setCreator(user.getRealName());
            collectionQueue.setCreatorId(user.getId());
        } else {
            CollectionQueue collectionQueueFind = collectionQueueRepository.findOne(id);
            //2.更新的时候||名称和code有修改  校验
            if (!collectionQueue.getName().equals(collectionQueueFind.getName())) {
                Query query = new Query();
                query.addCriteria(Criteria.where("name").is(collectionQueue.getName()));
                List<CollectionQueue> list = mongoTemplate.find(query, CollectionQueue.class);
                if (null != list && list.size() > 0 ) {
                    flag = "exist"; // 表示规则已经存在。
                }
            }
            if (!collectionQueue.getCode().equals(collectionQueueFind.getCode())){
                Query query = new Query();
                query.addCriteria(Criteria.where("code").is(collectionQueue.getCode()));
                List<CollectionQueue> list = mongoTemplate.find(query, CollectionQueue.class);
                if (null != list && list.size() > 0 ) {
                    flag = "exist"; // 表示规则已经存在。
                }
            }
            collectionQueue.setCreateTime(collectionQueueFind.getCreateTime());
            collectionQueue.setCreator(collectionQueueFind.getCreator());
            collectionQueue.setCreatorId(collectionQueueFind.getCreatorId());

        }

        if ("exist".equals(flag)) {
            message = "催收队列编号或队列名称已存在";
        }
        if (StringUtils.isEmpty(message)) {
            collectionQueue.setUpdateTime(new Date());
            collectionQueue.setUpdateBy(user.getRealName());
            collectionQueue.setUpdateId(user.getId());
            collectionQueue.setStrategyJson(collectionQueue.getStrategyJson());
            collectionQueueRepository.save(collectionQueue);
        }
        return message;
    }

    private String analysisRule(String jsonObject, StringBuilder stringBuilder) {
        if (Objects.isNull(jsonObject) || jsonObject.isEmpty()) {
            return null;
        }
        try {
            JSONArray jsonArray = new JSONArray(jsonObject);
            int iSize = jsonArray.length();
            for (int i = 0; i < iSize; i++) {
                JSONObject jsonObj = jsonArray.getJSONObject(i);
                if (jsonObj.getBoolean("leaf")) {
                    stringBuilder.append(jsonObj.get("relation"));
                    stringBuilder.append(jsonObj.get("variable"));
                    stringBuilder.append(jsonObj.get("symbol"));
                    stringBuilder.append("\"");
                    stringBuilder.append(jsonObj.get("value"));
                    stringBuilder.append("\"");
                } else {
                    stringBuilder.append(jsonObj.get("relation"));
                    stringBuilder.append("(");
                    analysisRule(jsonObj.getJSONArray("children").toString(), stringBuilder);
                    stringBuilder.append(")");
                }
            }
            return stringBuilder.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 判断策略是否重复，按照[案件类型][组别][催收员案件分配模式][分案模式][是否可用][产品类型]进行判断。
     * 如果没有策略返回true，已经存在策略返回false并弹出提示信息。
     *
     * @return
     */
    private String checkAllocationPatternIsExist(ObtainTaticsStrategyRequest obtainTaticsStrategy) {
        String result = null;
        String name = obtainTaticsStrategy.getName();

        Query query = new Query();
        query.addCriteria(Criteria.where("name").is(name));
        List<ObtainTaticsStrategy> list = mongoTemplate.find(query, ObtainTaticsStrategy.class);
        if (null != list && list.size() > 0 ) {
            result = "exist"; // 表示规则已经存在。
        }
        return result;
    }
    /**
     * 判断是否重复，队列编号和队列名称不能重复
     *
     * @return
     */
    private String checkCollectionQueueIsExist(CollectionQueue collectionQueue) {
        String result = null;
        String code = collectionQueue.getCode();
        String name = collectionQueue.getName();

        Query query = new Query();

        query.addCriteria(Criteria.where("code").is(code));
        List<CollectionQueue> list1 = mongoTemplate.find(query, CollectionQueue.class);
        if (null != list1 && list1.size() > 0 ) {
            result = "exist"; // 表示规则已经存在。
            return result;
        }
        query = new Query();
        query.addCriteria(Criteria.where("name").is(name));
        List<CollectionQueue> list = mongoTemplate.find(query, CollectionQueue.class);
        if (null != list && list.size() > 0 ) {
            result = "exist"; // 表示规则已经存在。
        }
        return result;
    }



    /**
     * 查询分案策略
     *
     * @param user
     * @param companyCode
     * @param name
     * @param caseType
     * @param status
     * @return
     */
    @Override
    public List<ObtainTaticsStrategy> getObtainTaticsStrategyList(User user, String companyCode, String name,
                                                                  Integer caseType, Integer status) {
        Query query = new Query();
        if (null != user) {
            if (StringUtils.isNotEmpty(user.getCompanyCode())) {
                query.addCriteria(Criteria.where("companyCode").is(user.getCompanyCode()));
            } else {
                query.addCriteria(Criteria.where("companyCode").is(companyCode));
            }
        } else {
            query.addCriteria(Criteria.where("companyCode").is(companyCode));
        }
        if (StringUtils.isNotBlank(name)) {
            Pattern pattern = Pattern.compile("^" + name + ".*$", Pattern.CASE_INSENSITIVE);
            query.addCriteria(Criteria.where("name").regex(pattern));
        }
        if (Objects.nonNull(caseType)) {
            query.addCriteria(Criteria.where("caseType").is(caseType));
        }
        if (Objects.nonNull(status)) {
            query.addCriteria(Criteria.where("status").is(status));
        }
        return mongoTemplate.find(query, ObtainTaticsStrategy.class);
    }


    /**
     * 每天执行的分案规则，针对逾期天数为T+3的案件
     * 所有的案件在 case_info_distributed 表中。
     * 每天执行的分案策略，针对逾期天数为T+3的案件.
     * 1 每天分案针对电催中逾期期数为1，天数为3的案件。
     * 2 委外的案件不需要分配，只需要进行回收操作。当委外案件的结束时间与当前时间相等，就将委外的案件进行回收。
     * 委外案件回收，当前日期等于委外结束时间就回收案件。
     * 已经分案的数据，最终放在 case_info 中，因此流转操作的都是 case_info表。
     *
     * @param companyCode
     */
    @Override
    @Async
    public void everyDayDivisionCase(String companyCode) {
        log.info("开始执行[每天执行的分案规则] ...");
        /**
         * 查询案件的类型和名称，然后将值修改到表中。
         */
        List<CaseDistributedResult> list = caseInfoDistributedMapper.getCaseProperties(companyCode);
        if (null != list && list.size() > 0) {
            ObtainStrategyMysqlUtils.updateProductTypeAndName(list,jdbcTemplate);
        }
        // 1 获取所有可用的，每天分案的策略.
        Query query = new Query();
        query.addCriteria(Criteria.where("companyCode").is(companyCode));
        query.addCriteria(Criteria.where("status").is(ObtainTaticsStrategy.Status.OPEN.getValue()));
        query.addCriteria(Criteria.where("divisionFrequency").is(ObtainTaticsStrategy.DivisionFrequency.EVERYDAY.getValue()));
        List<ObtainTaticsStrategy> strategylist = mongoTemplate.find(query, ObtainTaticsStrategy.class);
        long startTime = System.currentTimeMillis();
        executeEveryDayStrategy(strategylist);
        long endTime = System.currentTimeMillis();
        log.info(("自动分案完成,用时{}秒"),(endTime - startTime)/1000);
    }

    /**
     * 月初的分案规则，针对除过逾期天数为T+3的案件
     * 对于委外的案件，只需要标注案件类型为委外就可以了，委外的分配在委外与分案里。
     */
    @Override
    @Async
    public void monthEarlyDivisionCase(String companyCode) {
        log.debug("开始执行[月初的分案规则，针对除过逾期天数为T+3的案件] ...");
        /**
         * 查询案件的类型和名称，然后将值修改到表中。
         */
        List<CaseDistributedResult> list = caseInfoDistributedMapper.getCaseProperties(companyCode);
        if (null != list && list.size() > 0) {
            ObtainStrategyMysqlUtils.updateProductTypeAndName(list,jdbcTemplate);
        }
        // 1 获取所有可用的，每天分案的策略.
        Query query = new Query();
        query.addCriteria(Criteria.where("companyCode").is(companyCode));
        query.addCriteria(Criteria.where("status").is(ObtainTaticsStrategy.Status.OPEN.getValue()));
        query.addCriteria(Criteria.where("divisionFrequency").is(ObtainTaticsStrategy.DivisionFrequency.EARLY.getValue()));
        List<ObtainTaticsStrategy> strategylist = mongoTemplate.find(query, ObtainTaticsStrategy.class);
        executeMonthEarlyStrategy(strategylist);
        log.debug("完成[自动分案] ...");
    }


    /**
     * 执行频率为每天的分案策略。该策略针对逾期期数为1，逾期天数为3，类型为电催案件的所有案件。
     * 1 将策略转换为sql的where条件，查询符合条件的数据，如果没有数据，直接结束；有数据，执行分案策略
     * @param strategylist
     * @return
     */
    private boolean executeEveryDayStrategy(List<ObtainTaticsStrategy> strategylist) {
        boolean flag = false;
        ObtainTaticsStrategy strategy = null;
        String whereCondition = "";
        for (int h = 0; h < strategylist.size(); h++) {
            long startTime = System.currentTimeMillis();
            strategy = strategylist.get(h);
            List<String> collectionQueues = strategy.getCollectionQueues();

            //每个队列去查出一个list 标识出来对应的队列id
            // 去重  对于查出来的数据,要么来自caseinfo要么来自资源池(id为空,或者distributeId为空)
            //  保存每次队列查出来的caseId , distributeId
            //  每次查出来的结果 reomve掉已有的caseId或者distributeId,然后添加到总的list
            //  caseNum:都有
            ArrayList<CaseInfoDistributedModelImp> list = new ArrayList<CaseInfoDistributedModelImp>();
            ArrayList<String> alreadyCaseIds = new ArrayList<>();
            ArrayList<String> alreadyDistributeIds = new ArrayList<>();
            List<CollectionQueue> queues = new ArrayList<>();
            for (String queueId : collectionQueues) {
                CollectionQueue currentQueue = mongoTemplate.findById(queueId, CollectionQueue.class);
                queues.add(currentQueue);
            }
            queues.sort((o1, o2) -> o1.getUpdateTime().compareTo(o2.getUpdateTime()));
            if (queues.size() == 0){
                log.info("当前策略没有关联队列,没有查询到案件!!");
            }
            for (CollectionQueue collectionQueue : queues) {
                whereCondition = ObtainStrategyMysqlUtils.convernStrategy2WhereCondition(collectionQueue.getStrategyJson());
                log.debug("当前策略:{} 当前队列:{} ",strategy.getName(),collectionQueue.getName());
                List<CaseInfoDistributedModelImp> currentList=SqlUtils.getCaseInfoResult1(restTemplate,strategy,collectionQueue,caseInfoDistributedMapper,whereCondition);
                Iterator<CaseInfoDistributedModelImp> it = currentList.iterator();
                while(it.hasNext()){
                    CaseInfoDistributedModelImp x = it.next();
                    if (alreadyCaseIds.contains(x.getId()) || alreadyDistributeIds.contains(x.getDistributedId()) ){
                        it.remove();
                    }
                }
                for (CaseInfoDistributedModelImp modelImp : currentList) {
                    if (StringUtils.isNotBlank(modelImp.getId())){
                        alreadyCaseIds.add(modelImp.getId());
                    }
                    if (StringUtils.isNotBlank(modelImp.getDistributedId())){
                        alreadyDistributeIds.add(modelImp.getDistributedId());
                    }
                }
                list.addAll(currentList);
            }
            log.info("自动分案: 当前策略{}共查出数据{}条",strategy.getName(),list.size());
            Department department = null;
            //案件类型
            Integer caseType = strategy.getCaseType();
/*            if (caseType.equals(ObtainTaticsStrategy.CaseType.OUTSIDE_CASE.getValue())){
                strategy.setDepartId("404080b168a33b7a0168e912b7ae0df2");//外访
            }*/
            //对于策略  判断是内催还是委外  通过department判断
            String strategyDepartId = strategy.getDepartId();
            ResponseEntity<Department> entity = restTemplate.getForEntity("http://business-service/api/departmentController/department/" + strategyDepartId, Department.class);
            department = entity.getBody();
            if (department == null){
                continue;
            }

            Integer poolType = null;
            Integer type = department.getType();
            if(Objects.equals(type,Department.Type.TELEPHONE_COLLECTION.getValue())){//电催
                poolType = cn.fintecher.pangolin.entity.CaseInfo.CasePoolType.INNER.getValue();
            }else if(Objects.equals(type,Department.Type.OUTBOUND_COLLECTION.getValue())){//外访
                poolType = cn.fintecher.pangolin.entity.CaseInfo.CasePoolType.INNER.getValue();
            }else if(Objects.equals(type,Department.Type.OUTSOURCING_COLLECTION.getValue())){//委外
                poolType = cn.fintecher.pangolin.entity.CaseInfo.CasePoolType.OUTER.getValue();
            }
//            System.out.println("nThreads:"+(nThreads+1));
//            int everyNums = list.size() / nThreads;
//            int start = 0;
//            for (int i = 0; i < nThreads; i++){
//                List newList = list.subList(start, start+everyNums);
//                start = start+everyNums;
//                log.info("分案线程:{}启动...",i+1);
//                doDivisionService.doDivision(poolType, newList, strategy, department,i+1);
////                try {
////                    Thread.sleep(1000);
////                } catch (InterruptedException e) {
////                    e.printStackTrace();
////                }
//            }
//            if (list.size() % nThreads != 0){
//                log.info("分案线程:{}启动...",nThreads+1);
//                List newList = list.subList(start, start+list.size() % nThreads);
//                doDivisionService.doDivision(poolType, list, strategy, department,nThreads+1);
//            }
            doDivisionService.doDivision(poolType, list, strategy, department);
            long endTime = System.currentTimeMillis();
            log.info(("当前策略:{}执行完成,用时{}秒"),strategy.getName(),(endTime - startTime)/1000);
        }
        flag=true;
        return flag;
    }
//    static final int nThreads = Runtime.getRuntime().availableProcessors()/2 == 0 ? 1 : Runtime.getRuntime().availableProcessors()/2 ;
//    static final int nThreads = Runtime.getRuntime().availableProcessors()-2 ;
//    static final int nThreads = 1;


    /**
     * 执行除过T+3 的所有策略。这些策略是月初执行的。
     * 特别注意，案件的跨类型流转是需要修改collectionType，这个属性是在导入数据中获取的，所以对于后面跨类型后需要修改。
     * 这个属性策略中没有的，但是可以通过策略中的案件池类型进行判断获取。
     * @param strategylist
     * @return
     */
    private boolean executeMonthEarlyStrategy(List<ObtainTaticsStrategy> strategylist) {
        boolean flag = false;
        ObtainTaticsStrategy strategy = null;
        String whereCondition = null;
        for (int h = 0; h < strategylist.size(); h++) {
            strategy=strategylist.get(h);
            whereCondition = ObtainStrategyMysqlUtils.convernStrategy2WhereCondition(strategy.getStrategyJson());
            List<CaseInfoDistributedModelImp> list=SqlUtils.getCaseInfoResult(restTemplate,strategy,null,caseInfoDistributedMapper,whereCondition);
            if (null != list && list.size() > 0) {
                List<String> caseNumberList =new ArrayList<>();
                log.debug("开始执行 案件类型为"+strategy.getCaseType()+"产品类型为"+strategy.getProductType()+"的策略，满足的条件为:"+whereCondition);
                int caseType=strategy.getCaseType().intValue();
                List<cn.fintecher.pangolin.dataimp.model.CaseInfo> finalList= SqlUtils.getCaseInfoList(list);
                List<String> caseIdList=new ArrayList<>();  //存放案件id，在删除 outsource_pool 表中数据使用。
                for (int i = 0; i < finalList.size(); i++) {
                    cn.fintecher.pangolin.dataimp.model.CaseInfo temp=finalList.get(i);
                    caseNumberList.add(temp.getCaseNumber());

                    /*if(caseType == ObtainTaticsStrategy.CaseType.OUTSIDE_CASE.getValue().intValue() ||
                            caseType == ObtainTaticsStrategy.CaseType.VISIT_CASE.getValue().intValue()){
                        temp.setCurrentCollector(null);
                    }*/
                    if(temp.getCollectionType().intValue()== cn.fintecher.pangolin.entity.CaseInfo.CollectionType.VISIT.getValue().intValue()){
                        temp.setCollectionType(null);
                    }
                    String caseId=caseInfoDistributedMapper.getByCaseNumber(temp.getCaseNumber());
                    caseIdList.add(caseId);
                    if(StringUtils.isEmpty(caseId)){
                        caseInfoDistributedModelImpMapper.insert(temp);
                    }else{
                        caseInfoDistributedMapper.updateCaseInfo(temp.getCaseNumber(),temp.getCollectionType(),
                                temp.getOverduePeriods(),temp.getOverdueDays(),temp.getCaseType(),
                                temp.getCollectionStatus(),temp.getDepartId(),temp.getCurrentCollector(),
                                temp.getCasePoolType(),temp.getRecoverRemark());
                    }
                }
                if(caseType == ObtainTaticsStrategy.CaseType.OUTSIDE_CASE.getValue().intValue()){
                    insertOutSourcePool();
                }
                // 当类型为电催案件的时候，执行将案件与催员关联操作。
                /*if(caseType == ObtainTaticsStrategy.CaseType.TEL_CASE.getValue().intValue()){
                    ObtainStrategyMysqlUtils.relevanceCollectorAndCaseNumber(caseIdList,caseInfoDistributedMapper,finalList,strategy,jdbcTemplate);
                }*/
                // 将已经有案件类型的数据在 case_info_distributed 表中删除
                if(null != caseIdList && caseIdList.size()>0){
                    caseInfoDistributedMapper.deleteByCaseNumbers(caseNumberList);
                }
                log.debug("执行完了 案件类型为"+strategy.getCaseType()+"产品类型为"+strategy.getProductType()+"的策略，满足的条件为:"+whereCondition);
            }
        }
        flag=true;
        return flag;
    }


    /**
     * 更新委外案件池
     */
    public void insertOutSourcePool(){
        String companyName="杭银消费金融";
        List<Company> companyList = caseInfoDistributedMapper.selectCompanyByName(companyName);
        //查询案件中所有委外案件
        List<CaseInfo> caseInfoList = caseInfoDistributedMapper.getCaseInfoByCasePoolType(cn.fintecher.pangolin.entity.CaseInfo.CasePoolType.OUTER.getValue());
        if(caseInfoList != null && caseInfoList.size() > 0){
            for(CaseInfo caseInfo : caseInfoList){
                List<OutSourcePoolModel> outsourcePoolList = caseInfoDistributedMapper.getOutSourcePoolByCaseId(caseInfo.getId());
                //没有出差来，说明是新分配到委外案件池的
                if(outsourcePoolList == null || outsourcePoolList.size() == 0){
                    OutSourcePoolModel outsourcePool = new OutSourcePoolModel();
                    String id =  UUID.randomUUID().toString();
                    outsourcePool.setId(id);
                    outsourcePool.setCaseId(caseInfo.getId());//案件信息
                    outsourcePool.setOutTime(new Date());//委外时间..
                    outsourcePool.setOutStatus(OutsourcePool.OutStatus.TO_OUTSIDE.getCode());//委外状态
                    Integer overduePeriods=caseInfo.getOverduePeriods();
                    if(null != overduePeriods){
                        outsourcePool.setOverduePeriods("M"+overduePeriods.intValue());  // 逾期期数
                    }
                    outsourcePool.setContractAmt(caseInfo.getOverdueAmount());  // 逾期总金额
                    outsourcePool.setOutoperationStatus(null);//委外操作状态
                    outsourcePool.setOperator("administrator");
                    outsourcePool.setOperateTime(new Date());
                    if(companyList != null && companyList.size() > 0){
                        outsourcePool.setCompanyCode(companyList.get(0).getCode());
                    }
                    caseInfoDistributedMapper.insertOutSourcePool(outsourcePool);
                }
            }
        }
    }





}
