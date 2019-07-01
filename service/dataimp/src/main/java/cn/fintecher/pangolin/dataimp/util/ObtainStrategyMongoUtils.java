package cn.fintecher.pangolin.dataimp.util;

import cn.fintecher.pangolin.dataimp.model.ObtainStrategyJsonModel;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author frank  braveheart1115@163.com
 * @Description:
 * @Package cn.fintecher.pangolin.dataimp.util
 * @ClassName: cn.fintecher.pangolin.dataimp.util.ObtainStrategyMongoUtils
 * @date 2018年06月20日 18:32
 */
public class ObtainStrategyMongoUtils {

    public static void main(String [] args){
        String strategyJson="[{\"relation\":\"\",\"leaf\":true,\"variable\":\"asdqw\",\"value\":\"1\",\"symbol\":\">\",\"cityArr\":[],\"bracket\":\"null\"},{\"relation\":\"||\",\"leaf\":true,\"variable\":\"asdasd\",\"value\":\"2\",\"symbol\":\">=\",\"cityArr\":[],\"bracket\":\"或者\"},{\"relation\":\"||\",\"leaf\":true,\"variable\":\"asvavfs\",\"value\":\"3\",\"symbol\":\"==\",\"cityArr\":[],\"bracket\":\"或者\"},{\"relation\":\"||\",\"leaf\":true,\"variable\":\"asdqw\",\"value\":\"4\",\"symbol\":\"<=\",\"cityArr\":[],\"bracket\":\"或者\"},{\"relation\":\"||\",\"leaf\":true,\"variable\":\"overduePeriods\",\"value\":\"5\",\"symbol\":\"<\",\"cityArr\":[],\"bracket\":\"或者\"},{\"relation\":\"&&\",\"leaf\":true,\"variable\":\"overduePeriods\",\"value\":\"1\",\"symbol\":\">\",\"cityArr\":[],\"bracket\":\"并且\"},{\"relation\":\"&&\",\"leaf\":true,\"variable\":\"avasd\",\"value\":\"2\",\"symbol\":\">=\",\"cityArr\":[],\"bracket\":\"并且\"},{\"children\":[{\"relation\":\"\",\"leaf\":true,\"variable\":\"overdueAmount\",\"value\":\"10000\",\"symbol\":\">\",\"cityArr\":[],\"bracket\":\"并且(\"},{\"relation\":\"&&\",\"leaf\":true,\"variable\":\"overdueAmount\",\"value\":\"50000\",\"symbol\":\"<=\",\"cityArr\":[],\"bracket\":\"并且\"}],\"relation\":\"&&\",\"leaf\":false,\"variable\":\"\",\"value\":\"\",\"symbol\":\"\",\"cityArr\":[],\"bracket\":\"并且(\"}]";
        List<ObtainStrategyJsonModel> list=convertString2ObtainStrategy(strategyJson);
        System.out.println(list.size());
        /**
         * 将多种条件一次写到一个query对象中，条件拼接比较麻烦，因此根据bracket的取值分为四类：
         * 将这4个条件执行完后，取所有数据的交集
         * 1 bracket : 或者
         * 2 bracket : 并且 null 为null的是第一个置空的或者是带“(”里的第一个条件。
         * 3 bracket : 或者(
         * 4 bracket : 并且(
         */
        Query query = new Query();
        getOrQueryObject(list,query);
        System.out.println(888888888);
    }


    /**
     * 获取 bracket : 或者 的条件。
     * @param query
     * @param
     * @return
     */
    private static Query getOrQueryObject(List<ObtainStrategyJsonModel> strategyList,Query query){
        String variable=null;  // 变量字段属性名
        String value=null;       // 变量字段属性值
        String symbol=null;    // 值符号(大于，小于，大于等于等)
        ObtainStrategyJsonModel strategy=null;
        for (int i = 0,len=strategyList.size(); i < len; i++) {
            strategy=strategyList.get(i);
            variable=String.valueOf(strategy.getVariable());
            value=String.valueOf(strategy.getValue());
            symbol=String.valueOf(strategy.getSymbol());

            if(">".equals(symbol)){
                if (Objects.nonNull(variable)) {
                    query.addCriteria(Criteria.where(variable).gt(value));
                }
            }
            if(">=".equals(symbol)){
                if (Objects.nonNull(variable)) {
                    query.addCriteria(Criteria.where(variable).gte(value));
                }
            }
            if("==".equals(symbol)){
                if (Objects.nonNull(variable)) {
                    query.addCriteria(Criteria.where(variable).is(value));
                }
            }
            if("<=".equals(symbol)){
                if (Objects.nonNull(variable)) {
                    query.addCriteria(Criteria.where(variable).lte(value));
                }
            }
            if("<".equals(symbol)){
                if (Objects.nonNull(variable)) {
                    query.addCriteria(Criteria.where(variable).lt(value));
                }
            }
        }

        return query;
    }

    /**
     * 获取 bracket : 或者( 的条件。
     * @param query
     * @param strategy
     * @return
     */
    private static Query getSpecialOrQueryObject(Query query,ObtainStrategyJsonModel strategy){
        String variable=String.valueOf(strategy.getVariable());  // 变量字段属性名
        String value=String.valueOf(strategy.getValue());       // 变量字段属性值
        String symbol=String.valueOf(strategy.getSymbol());    // 值符号(大于，小于，大于等于等)
        if(">".equals(symbol)){
            if (Objects.nonNull(variable)) {
                query.addCriteria(Criteria.where(variable).gt(value));
            }
        }
        if(">=".equals(symbol)){
            if (Objects.nonNull(variable)) {
                query.addCriteria(Criteria.where(variable).gte(value));
            }
        }
        if("==".equals(symbol)){
            if (Objects.nonNull(variable)) {
                query.addCriteria(Criteria.where(variable).is(value));
            }
        }
        if("<=".equals(symbol)){
            if (Objects.nonNull(variable)) {
                query.addCriteria(Criteria.where(variable).lte(value));
            }
        }
        if("<".equals(symbol)){
            if (Objects.nonNull(variable)) {
                query.addCriteria(Criteria.where(variable).lt(value));
            }
        }
        return query;
    }




    public static List<ObtainStrategyJsonModel> convertString2ObtainStrategy(String strategyJson){
        List<ObtainStrategyJsonModel> strategyList=new ArrayList<>();
        JSONArray array=JSONArray.fromObject(strategyJson);
        ObtainStrategyJsonModel strategy=null;
        JSONObject json=null;
        for (int i = 0,len=array.size(); i < len; i++) {
            json=(JSONObject)array.get(i);
            strategy=getObtainStrategyJsonModel(json);
            List<ObtainStrategyJsonModel> children=strategy.getChildren();
            if(null != children){
                if(children.size()>0){
                    for (int j = 0,ll=children.size(); j < ll; j++) {
                        strategyList.add(children.get(j));
                    }
                }
            }

            strategyList.add(strategy);
        }

        return strategyList;
    }

    private static ObtainStrategyJsonModel getObtainStrategyJsonModel(JSONObject json){
        ObtainStrategyJsonModel model=new ObtainStrategyJsonModel();
        String variable=String.valueOf(json.get("variable"));
        if(StringUtils.isNotEmpty(variable)){
            model.setRelation(String.valueOf(json.get("relation")));
            model.setLeaf(Boolean.valueOf(json.get("leaf")+""));
            model.setVariable(variable);
            model.setValue(String.valueOf(json.get("value")));
            model.setSymbol(String.valueOf(json.get("symbol")));
            model.setBracket(String.valueOf(json.get("bracket")));
        }

        String children=String.valueOf(json.get("children"))== "null"? "":String.valueOf(json.get("children"));
        if(!StringUtils.isEmpty(children)){
            JSONArray array=JSONArray.fromObject(children);
            List<ObtainStrategyJsonModel> childrenList=new ArrayList<>();
            ObtainStrategyJsonModel temp=null;
            JSONObject jsonT=null;
            for (int i = 0,len=array.size(); i < len; i++) {
                jsonT=(JSONObject)array.get(i);
                temp=getObtainStrategyJsonModel(jsonT);
                childrenList.add(temp);
            }
            model.setChildren(childrenList);
        }
        String cityArr=String.valueOf(json.get("cityArr"))== "null"? "":String.valueOf(json.get("cityArr"));

        return model;
    }


    /**
     * 将策略中的条件转换为Query对象并返回。
     * 先执行没有children的对象，执行完一个后，将集合中的元素删除。
     *
     * @param strategyList
     * @return
     */
    private static Query getdynamicQuery(List<ObtainStrategyJsonModel> strategyList){
        Query query = new Query();
        Criteria criteria = new Criteria();
        ObtainStrategyJsonModel model=null;
        String relation; // 逻辑关系符号表示 或|| 并且&&
        for (int i = 0,len=strategyList.size(); i < len; i++) {
            model=strategyList.get(i);
            relation=String.valueOf(model.getRelation());
            if(StringUtils.isNotEmpty(relation)){
                if("&&".equals(relation)){
                    getAndQueryObject(query,criteria,strategyList,model);
                }
            }else{  // 针对置空的与children下的第一个条件
                getAndQueryObject(query,criteria,strategyList,model);
            }
        }
        return query;
    }


    private static Query getAndQueryObject(Query query,Criteria criteria,List<ObtainStrategyJsonModel> strategyList,ObtainStrategyJsonModel strategy){
        String variable=String.valueOf(strategy.getVariable());    // 变量字段属性名
        String value=String.valueOf(strategy.getValue());  // 变量字段属性值
        String symbol=String.valueOf(strategy.getSymbol());    // 值符号(大于，小于，大于等于等)
        if(">".equals(symbol)){
            if (Objects.nonNull(variable)) {
                query.addCriteria(Criteria.where(variable).gt(value));
            }
        }

        if(">=".equals(symbol)){
            if (Objects.nonNull(variable)) {
                query.addCriteria(Criteria.where(variable).gte(value));

            }
        }
        if("==".equals(symbol)){
            if (Objects.nonNull(variable)) {
                query.addCriteria(Criteria.where(variable).is(value));

            }
        }
        if("<=".equals(symbol)){
            if (Objects.nonNull(variable)) {

                query.addCriteria(Criteria.where(variable).lte(value));

            }
        }
        if("<".equals(symbol)){
            if (Objects.nonNull(variable)) {

                query.addCriteria(Criteria.where(variable).lt(value));

            }
        }
        return query;
    }




}
