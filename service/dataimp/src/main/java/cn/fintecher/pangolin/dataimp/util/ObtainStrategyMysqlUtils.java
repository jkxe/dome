package cn.fintecher.pangolin.dataimp.util;

import cn.fintecher.pangolin.dataimp.entity.CaseDistributedResult;
import cn.fintecher.pangolin.dataimp.entity.ObtainTaticsStrategy;
import cn.fintecher.pangolin.dataimp.mapper.CaseInfoDistributedMapper;
import cn.fintecher.pangolin.dataimp.model.ObtainStrategyJsonModel;
import cn.fintecher.pangolin.entity.CaseInfo;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author frank  braveheart1115@163.com
 * @Description: 将策略中的条件拼接为MySQL的查询条件。
 * @Package cn.fintecher.pangolin.dataimp.util
 * @ClassName: cn.fintecher.pangolin.dataimp.util.ObtainStrategyMysqlUtils
 * @date 2018年06月21日 15:00
 */
public class ObtainStrategyMysqlUtils {

    private final static Logger log = LoggerFactory.getLogger(ObtainStrategyMysqlUtils.class);


    /**
     * 将传入的Java属性转换为sql的字段，即遇到大写字母,将大写改为小写，然后前面添加"_"
     */
    private static String getDbColumn(String javaValue) {
        if (javaValue == null || "".equals(javaValue)) {
            return "";
        }
        javaValue = String.valueOf(javaValue.charAt(0)).toUpperCase().concat(javaValue.substring(1));
        StringBuffer sb = new StringBuffer();
        Pattern pattern = Pattern.compile("[A-Z]([a-z\\d]+)?");
        Matcher matcher = pattern.matcher(javaValue);
        while (matcher.find()) {
            String word = matcher.group();
            sb.append(word.toUpperCase());
            sb.append(matcher.end() == javaValue.length() ? "" : "_");
        }
        return sb.toString().toLowerCase();
    }



    /**
     * 返回最终的结果。
     * @param map
     * @param users
     * @return
     */
    private static List<cn.fintecher.pangolin.dataimp.model.CaseInfo> getResult(Map<Integer,List<cn.fintecher.pangolin.dataimp.model.CaseInfo>> map,List<String> users){
        List<cn.fintecher.pangolin.dataimp.model.CaseInfo> result=new ArrayList<>(0);
        String userName=null;
        Set<Integer> keys=map.keySet();
        for(Integer key:keys){
            userName=users.get(key-1);
            double total=0;
            cn.fintecher.pangolin.dataimp.model.CaseInfo model=null;
            int mapValueSize=map.get(key).size();
            for (int i = 0; i < mapValueSize; i++) {
                model= map.get(key).get(i);
                total=total+model.getAccountBalance().floatValue();
                model.setCurrentCollector(userName);
                result.add(model);
            }
            log.debug(userName+" 账单总额为："+total);
        }
        return result;
    }

    /**
     * 按照金额均分。在分配的时候，有部分案件是来自委外待分配里的案件，这些案件没执行预分配之前期数下降后，符合电催或委外策略，就会分配出去，
     * 这时候就要根据案件id在outsource_pool中删除掉。
     * @param caseList
     * @param userList
     * @param jdbcTemplate
     */
    public static List<String> divisionCaseByHouseholds(List<cn.fintecher.pangolin.dataimp.model.CaseInfo> caseList,List<String> userList,JdbcTemplate jdbcTemplate){
        int userSize=userList.size();
        String caseNumber=null;  // 案件编号
        int remainder=0; // 对人员总数取余后的值，表示分给哪个人员。
        String currentCollector=null; // 当前催收员ID
        List<String> sqlList=new ArrayList<>();
        log.info("按照户数均分案件开始....................");
        for (int i = 0,len=caseList.size(); i < len; i++){
            remainder=i%userSize;
            currentCollector=userList.get(remainder);
            caseNumber=caseList.get(i).getCaseNumber();
            sqlList.add(SqlUtils.divisionCase2User(currentCollector,caseNumber));
        }
        log.info("按照户数均分案件完成....................");
        return sqlList;
    }


    // 按催收员配比
    private static void divisionCaseByRatio(){}

    // 按金额填坑
    private static void divisionCaseByMoneyPit(){}

    // 按户数填坑
    private static void divisionCaseByHouseholdsPit(){}









    public static void updateProductTypeAndName(List<CaseDistributedResult> list,JdbcTemplate jdbcTemplate){
        String caseNumber=null; // 案件编号
        String productType=null; // 产品类型
        String productName=null; // 产品名称
        CaseDistributedResult result=null;
        int kk=0;
        log.debug("开始执行修改产品类型与产品名称的sql.............");
        if(null !=list && list.size()>0){
            StringBuilder sb=null;
            for (int i = 0,len=list.size(); i < len; i++){
                String sqlUpdate[]=new String[list.size()]; // 存放所有的update语句
                sb=new StringBuilder();
                result=list.get(i);
                caseNumber=result.getCaseNumber();
                productType=result.getProductTypeId();
                productName=result.getProductName();
                if(StringUtils.isNotEmpty(caseNumber) && StringUtils.isNotEmpty(productType) &&StringUtils.isNotEmpty(productName)){
                    sb.append("UPDATE case_info_distributed SET product_type = '");
                    sb.append(productType);
                    sb.append("',  product_name = '");
                    sb.append(productName);
                    sb.append("' where case_number = '");
                    sb.append(caseNumber).append("';");
                    sqlUpdate[i]=sb.toString();
                    kk++;
                    log.debug("修改产品类型与产品名称的sql："+sb.toString());
                }
            }
        }
        log.debug("修改产品类型与产品名称的sql执行完成,共"+kk+"条..............");
    }

    /**
     * 将策略中需要修改的属性转换为 update语句片段
     * @param strategyJson
     * @return
     */
    public static String convernStrategy2WhereCondition(String strategyJson){
        String whereCondition=null;
        Map<String, List<ObtainStrategyJsonModel>> map = new HashMap<>();
        if(StringUtils.isNotEmpty(strategyJson)){
            // 存放没有children的策略
            List<ObtainStrategyJsonModel> strategyList = new ArrayList<>();
            // 存放有children的策略
            List<ObtainStrategyJsonModel> strategyChildrenList = new ArrayList<>();
            JSONArray array = JSONArray.fromObject(strategyJson);
            ObtainStrategyJsonModel strategy = null;
            JSONObject json = null;
            String variable = null;
            for (int i = 0, len = array.size(); i < len; i++) {
                json = (JSONObject) array.get(i);
                strategy = getObtainStrategyJsonModel(json);
                variable = String.valueOf(strategy.getVariable());
                if (StringUtils.isNotEmpty(variable) && !"null".equals(variable)) {
                    strategyList.add(strategy);
                }
                List<ObtainStrategyJsonModel> children = strategy.getChildren();
                if (null != children) {
                    if (children.size() > 0) {
                        for (int j = 0, ll = children.size(); j < ll; j++) {
                            strategyChildrenList.add(children.get(j));
                        }
                    }
                }
            }
            map.put("strategyList", strategyList);
            map.put("strategyChildrenList", strategyChildrenList);
        }
        String strategyQuerySql = getStrategyQuerySql(map);
        String strategyChildrenList = getStrategyChildrenList(map);
        whereCondition=strategyQuerySql + strategyChildrenList;
        return whereCondition;
    }




    public static List<String> divisionCaseByMoney(
            List<cn.fintecher.pangolin.dataimp.model.CaseInfo> caseList,List<String> users,String deptId,JdbcTemplate jdbcTemplate,Integer collectionType){
        Map<Integer,List<cn.fintecher.pangolin.dataimp.model.CaseInfo>> map=new HashMap<Integer,List<cn.fintecher.pangolin.dataimp.model.CaseInfo>>();
        /**
         *  催缴员及对应账单,初始化map，key为催收员ID，value为分配的案件。
         */
        int userCount=users.size();
        for (int i = userCount; i >0; i--) {
            map.put(i,new ArrayList<cn.fintecher.pangolin.dataimp.model.CaseInfo>());
        }
        //分配催缴单
        divideDebit(map,caseList,userCount,true);
        List<cn.fintecher.pangolin.dataimp.model.CaseInfo> result=getResult(map,users);
        String caseNumber=null; // 案件编号
        String currentCollector=null; // 当前催收员ID
        List<String> sqlList=new ArrayList<>(0);
        for (int j = 0,len1=result.size(); j < len1; j++){
            caseNumber=caseList.get(j).getCaseNumber();
            currentCollector=caseList.get(j).getCurrentCollector();
            sqlList.add(SqlUtils.divisionCase2User(currentCollector,caseNumber));
        }
        return sqlList;
    }



    public static void relevanceCollectorAndCaseNumber(
            List<String> caseIdList,CaseInfoDistributedMapper caseInfoDistributedMapper,
            List<cn.fintecher.pangolin.dataimp.model.CaseInfo> caseList,ObtainTaticsStrategy strategy,JdbcTemplate jdbcTemplate){
        Integer collectionType=getCollectionTypeByCaseType(strategy.getCaseType());
        Integer caseType=strategy.getCaseType();
        List<String> users=strategy.getUsers();
        String deptId=strategy.getDepartId();
        // 电催案件进行金额，户数的分配。
        if(caseType.intValue()==ObtainTaticsStrategy.CaseType.INNER_CASE.getValue().intValue()){
            Integer pattern = strategy.getUserPattern();
            if(null == pattern || pattern.intValue()==0){
                pattern=ObtainTaticsStrategy.UserPattern.SYNTHESIZE.getValue();
            }
            if(null != caseIdList && caseIdList.size()>0){
                caseInfoDistributedMapper.deleteOutsourcePoolByCaseIds(caseIdList);
            }
            List<String> sqlList=new ArrayList<>(0);
            if(ObtainTaticsStrategy.UserPattern.HOUSEHOLDS.getValue().intValue()== pattern.intValue()){  // 按户数
                sqlList=divisionCaseByHouseholds(caseList,users,jdbcTemplate);
            }else if(ObtainTaticsStrategy.UserPattern.MONEY.getValue().intValue()== pattern.intValue()){  // 按金额
                Collections.sort(caseList);
                sqlList=divisionCaseByMoney(caseList,users,deptId,jdbcTemplate,collectionType);
            }else if(ObtainTaticsStrategy.UserPattern.SYNTHESIZE.getValue().intValue()== pattern.intValue()){  // 综合均分
                Collections.sort(caseList);
                sqlList=divisionCaseByMoney(caseList,users,deptId,jdbcTemplate,collectionType);
            }/*else if(ObtainTaticsStrategy.UserPattern.RATIO.getValue().intValue()== pattern.intValue()){  // 按催收员配比
                divisionCaseByRatio();
            }else if(ObtainTaticsStrategy.UserPattern.MONEY_PIT.getValue().intValue()== pattern.intValue()){ // 按金额填坑
                divisionCaseByMoneyPit();
            }else if(ObtainTaticsStrategy.UserPattern.HOUSEHOLDS_PIT.getValue().intValue()== pattern.intValue()){ // 按户数填坑
                divisionCaseByHouseholdsPit();
            }*/
            if(sqlList.size()>0){
                batchExecuteSql(sqlList,jdbcTemplate);
            }
        }


    }


    /**
     * 批量执行sql，每50条一个批次。
     * @param sqlList
     */
    private static void batchExecuteSql(List<String> sqlList,JdbcTemplate jdbcTemplate){
        if(sqlList.size()>0 && sqlList.size()<=50){
            String[] sqls=getArraySql(sqlList);
            if(sqls != null && sqls.length>0){
                jdbcTemplate.batchUpdate(sqls);
                log.debug("批次执行了sql,共"+sqls.length+"条 ***************");
            }
        }else{
            List<String> perExecuteList=new ArrayList<>();
            perExecuteList.addAll(sqlList.subList(0,50));
            String[] sqls=getArraySql(perExecuteList);
            if( sqls!=null && sqls.length>0){
                jdbcTemplate.batchUpdate(sqls);
                log.debug("批次执行了sql,共"+sqls.length+"条 ***************");
            }
            sqlList.removeAll(perExecuteList);
            batchExecuteSql(sqlList,jdbcTemplate);
        }
    }


    private static String[] getArraySql(List<String> perExecuteList){
        String[] sqls= null;
        if(null != perExecuteList && !perExecuteList.isEmpty()){
            sqls=new String[perExecuteList.size()];
            for (int i = 0; i < perExecuteList.size(); i++) {
                sqls[i]=perExecuteList.get(i);
            }
        }
        return sqls;
    }

    private static Map<Integer,List<cn.fintecher.pangolin.dataimp.model.CaseInfo>> divideDebit(Map<Integer,List<cn.fintecher.pangolin.dataimp.model.CaseInfo>> map,
                                                                                               List<cn.fintecher.pangolin.dataimp.model.CaseInfo> dList,Integer num,boolean direction){
        if(dList.size()>=num){//账单大于人数
            for(int i=0;i<num;i++){
                Integer index;
                if(direction){
                    index=i+1;
                }else{
                    index=num-i;
                }
                List<cn.fintecher.pangolin.dataimp.model.CaseInfo> list=map.get(index);
                list.add(dList.get(i));
                map.put(index, list);

            }
            //去除已经分配的账单
            List<cn.fintecher.pangolin.dataimp.model.CaseInfo> newDebitList=new ArrayList<cn.fintecher.pangolin.dataimp.model.CaseInfo>();
            for(int i=0;i<dList.size();i++){
                if(i>num-1){
                    newDebitList.add(dList.get(i));
                }
            }
            if(newDebitList.size()>0){
                //下次分配账单，按反方向分配
                divideDebit(map,newDebitList,num,!direction);
            }
        }else if(dList.size()<num){//账单小于人数
            for(int i=0;i<dList.size();i++){
                List<cn.fintecher.pangolin.dataimp.model.CaseInfo> list=map.get(i+1);
                list.add(dList.get(i));
                map.put(i+1, list);
            }
        }
        return map;
    }



    private static ObtainStrategyJsonModel getObtainStrategyJsonModel(JSONObject json) {
        ObtainStrategyJsonModel model = new ObtainStrategyJsonModel();
        String variable = getDbColumn(String.valueOf(json.get("variable")));
        String symbol = String.valueOf(json.get("symbol"));
        if ("==".equals(symbol)) {
            symbol = "=";
        }
        if (StringUtils.isNotEmpty(variable)) {
            model.setRelation(String.valueOf(json.get("relation")));
            model.setLeaf(Boolean.valueOf(json.get("leaf") + ""));
            model.setVariable(variable);
            model.setValue(String.valueOf(json.get("value")));
            model.setSymbol(symbol);
            model.setBracket(String.valueOf(json.get("bracket")));
        }
        String children = String.valueOf(json.get("children")) == "null" ? "" : String.valueOf(json.get("children"));
        if (!StringUtils.isEmpty(children)) {
            JSONArray array = JSONArray.fromObject(children);
            List<ObtainStrategyJsonModel> childrenList = new ArrayList<>();
            ObtainStrategyJsonModel temp = null;
            JSONObject jsonT = null;
            for (int i = 0, len = array.size(); i < len; i++) {
                jsonT = (JSONObject) array.get(i);
                temp = getObtainStrategyJsonModel(jsonT);
                childrenList.add(temp);
            }
            model.setChildren(childrenList);
        }
        return model;
    }

    /**
     * 获取没有children属性的分配策略
     * @param map
     * @return
     */
    private static String getStrategyQuerySql(Map<String, List<ObtainStrategyJsonModel>> map) {
        List<ObtainStrategyJsonModel> strategyList = map.get("strategyList");
        StringBuilder sb = new StringBuilder(" where 1=1 ");
        StringBuilder groupBy = new StringBuilder(" group by case_number having 1=1 ");
        String variable = null;
        for (int i = 0, len = strategyList.size(); i < len; i++) {
            ObtainStrategyJsonModel model = strategyList.get(i);
            variable = String.valueOf(model.getVariable());
            if (variable.equals("sum_overdue_amount") || variable.equals("max_overdue_days") || variable.equals("max_overdue_periods") ){
                appendSql(groupBy,model);
            }else {
                appendSql(sb,model);
            }

        }
        String collectionStatuss = "" + cn.fintecher.pangolin.entity.CaseInfo.CollectionStatus.WAITCOLLECTION.getValue() + ","
                + cn.fintecher.pangolin.entity.CaseInfo.CollectionStatus.COLLECTIONING.getValue() + ","
                + cn.fintecher.pangolin.entity.CaseInfo.CollectionStatus.WAIT_FOR_DIS.getValue();
        String whereCondition = " and (collection_status in("+ collectionStatuss +") or recover_remark = 1)";
        return sb.append(whereCondition).append(groupBy).toString();
    }

    private static StringBuilder appendSql(StringBuilder sb,ObtainStrategyJsonModel model){
        String relation = String.valueOf(model.getRelation());
        String symbol = String.valueOf(model.getSymbol());
        String variable = String.valueOf(model.getVariable());
        String value = String.valueOf(model.getValue());
        if (StringUtils.isEmpty(relation) || "&&".equals(relation)) {
            if("like".equals(symbol)){
                sb.append(" and ").append(variable).append(" like '%").append(value).append("%'");
            }else if("!=".equals(symbol)){
                String values[]=null;
                if(value.contains(",")){
                    values=value.split(",");
                }else if(value.contains("，")){
                    values=value.split("，");
                }else{
                    values=new String[1];
                    values[0]=value;
                }
                if(values.length == 1){
                    sb.append(" and ").append(variable).append(" not like '%").append(value).append("%'");
                }else{
                    sb.append(getProductParams(values,variable));
                }
            }else{
                sb.append(" and ").append(variable).append(" ").append(symbol).append(" '").append(value).append("'");
            }
        } else if ("||".equals(relation)) {
            if("like".equals(symbol)){
                sb.append(" or ").append(variable).append(" like '%").append(value).append("%'");
            }else if("!=".equals(symbol)){
                sb.append(" or ").append(variable).append(" not like '%").append(value).append("%'");
            }else{
                sb.append(" or ").append(variable).append(" ").append(symbol).append(" '").append(value).append("'");
            }
        }
        return sb;
    }


    public static String getProductParams(String[] values,String variable){
        StringBuilder sb=new StringBuilder();
        String value=null;
        for (int i = 0; i < values.length; i++) {
            value=values[i];
            sb.append(" and ").append(variable).append(" not like '%").append(value).append("%'");
        }
        return sb.toString();

    }

    /**
     * 获取有children属性的分配策略
     * @param map
     * @return
     */
    private static String getStrategyChildrenList(Map<String, List<ObtainStrategyJsonModel>> map) {
        List<ObtainStrategyJsonModel> strategyList = map.get("strategyChildrenList");
        ObtainStrategyJsonModel model = null;
        StringBuilder sb = new StringBuilder();
        String relation = null;
        String symbol = null;
        String variable = null;
        String value = null;
        String bracket = null; // 符号文字描述，需要在条件的前后拼接括号
        int len = strategyList.size();
        for (int i = 0; i < len; i++) {
            model = strategyList.get(i);
            relation = model.getRelation();
            symbol = model.getSymbol();
            variable = model.getVariable();
            value = model.getValue();
            bracket = model.getBracket();
            if (i == 0) {
                if ("并且(".equals(bracket)) {
                    sb.append(" and (");
                } else if ("或者(".equals(bracket)) {
                    sb.append(" or (");
                }
            }
            if (StringUtils.isEmpty(relation) || "&&".equals(relation)) {
                if("(".equals(sb.substring(sb.length()-1))){
                    sb.append(" ").append(variable).append(" ").append(symbol).append(" '").append(value).append("'");
                }else{
                    sb.append(" and").append(" ").append(variable).append(" ").append(symbol).append(" '").append(value).append("'");
                }
            } else if ("||".equals(relation)) {
                if("(".equals(sb.substring(sb.length()-1))){
                    sb.append(" ").append(variable).append(" ").append(symbol).append(" '").append(value).append("'");
                }else{
                    sb.append(" or").append(" ").append(variable).append(" ").append(symbol).append(" '").append(value).append("'");
                }
            }
        }
        return sb.toString();
    }



    public static Integer getCollectionStatusByCaseType(Integer caseType){
        Integer collectionType=0;
        switch (caseType.intValue()) {
            case 540:  // 电催
                collectionType = CaseInfo.CollectionStatus.WAITCOLLECTION.getValue();
                break;
            case 541:  //委外
                collectionType = CaseInfo.CollectionStatus.WAIT_FOR_DIS.getValue();
                break;
            case 542:  // 特殊
//                collectionType = CaseInfo.CollectionType.SPECIAL.getValue();
//                break;
            case 543:  // 停催
//                collectionType = CaseInfo.CollectionType.STOP.getValue();
//                break;
            case 544: // 诉讼
//                collectionType = CaseInfo.CollectionType.JUDICIAL.getValue();
//                break;
            case 545:   // 反欺诈不知道分到哪个下面
//                collectionType = CaseInfo.CollectionType.TEL.getValue();
//                break;
            case 546:  // 外访
                collectionType = CaseInfo.CollectionStatus.WAITCOLLECTION.getValue();
                break;
            default:
                collectionType = CaseInfo.CollectionStatus.WAITCOLLECTION.getValue();
                break;
        }
        return collectionType;
    }

    /**
     * 通过案件池类型获取案件的催收类型(电催/外访/委外....)
     * casePoolType  案件池类型
     * 540:内催案件,541:委外案件,546:外访案件,542:特殊案件,543:停催案件,544:诉讼案件,545:反欺诈案件
     * @param caseType
     * @return
     */
    public static Integer getCollectionTypeByCaseType(Integer caseType){
        Integer collectionType=0;
        switch (caseType.intValue()) {
            case 540:
                collectionType = CaseInfo.CollectionType.TEL.getValue();
                break;
            case 541:
                collectionType = CaseInfo.CollectionType.outside.getValue();
                break;
            case 542:
                collectionType = CaseInfo.CollectionType.SPECIAL.getValue();
                break;
            case 543:
                collectionType = CaseInfo.CollectionType.STOP.getValue();
                break;
            case 544:
                collectionType = CaseInfo.CollectionType.JUDICIAL.getValue();
                break;
            case 545:   // 反欺诈不知道分到哪个下面
                collectionType = CaseInfo.CollectionType.TEL.getValue();
                break;
            case 546:
                collectionType = CaseInfo.CollectionType.VISIT.getValue();
                break;
            default:
                collectionType = CaseInfo.CollectionType.TEL.getValue();
                break;
        }
        return collectionType;
    }



    /**
     * 由案件类型获取对应的案件池类型
     * @return
     */
    public static Integer getCasePoolTypeByCaseType(Integer caseType){
        Integer resultType=0;
        switch (caseType.intValue()) {
            case 540:
                resultType = CaseInfo.CasePoolType.INNER.getValue();
                break;
            case 541:
                resultType = CaseInfo.CasePoolType.OUTER.getValue();
                break;
            case 542:
                resultType = CaseInfo.CasePoolType.SPECIAL.getValue();
                break;
            case 543:
                resultType = CaseInfo.CasePoolType.STOP.getValue();
                break;
            case 544:
                resultType = CaseInfo.CasePoolType.JUDICIAL.getValue();
                break;
            case 545:   // 反欺诈不知道分到哪个下面
                resultType = CaseInfo.CollectionType.TEL.getValue();
                break;
            case 546:
                resultType = CaseInfo.CasePoolType.INNER.getValue();
                break;
            default:
                resultType = CaseInfo.CasePoolType.INNER.getValue();
                break;
        }
        return resultType;
    }



}
