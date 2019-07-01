package cn.fintecher.pangolin.report.util;

import cn.fintecher.pangolin.entity.CaseInfo;
import cn.fintecher.pangolin.entity.User;
import cn.fintecher.pangolin.report.mapper.FollowupRecord4MobileMapper;
import cn.fintecher.pangolin.report.model.MapModel;
import cn.fintecher.pangolin.report.model.mobile.AssistModel4Mobile;
import cn.fintecher.pangolin.report.model.mobile.LongitudeAndLatitudeModel;
import cn.fintecher.pangolin.report.model.mobile.VisitModel4Mobile;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhangmingming  braveheart1115@163.com
 * @Description:
 * @Package cn.fintecher.pangolin.report.util
 * @ClassName: cn.fintecher.pangolin.report.util.AppUtils
 * @date 2018年10月26日 12:32
 */
public class AppUtils {

    private static final Logger log = LoggerFactory.getLogger(AppUtils.class);

    /**
     * @author zhangmingming  braveheart1115@163.com
     * @date 2018/10/26 0026 下午 12:19
     * @Description: 根据用户和催收状态返回查询参数的map集合。
     */
    public static Map<String,String> getQueryparam4Visit(User user, String collectionStatus, String personalName, String address, FollowupRecord4MobileMapper followupRecord4MobileMapper){
        Map<String,String> queryparam=new HashMap<>();
        String userId=user.getId();
        queryparam.put("collectionStatus",collectionStatus); // 外访使用
        queryparam.put("personalName",personalName);
        queryparam.put("address",address);

        //如果是催员，根据 催收状态 将当前人员的id赋值到 currentCollector 或 latelyCollector 上
        boolean flag1= CaseInfo.CollectionStatus.WAITCOLLECTION.getValue().toString().equals(collectionStatus);
        boolean flag2= CaseInfo.CollectionStatus.COLLECTIONING.getValue().toString().equals(collectionStatus);
        boolean flag3= CaseInfo.CollectionStatus.CASE_OVER.getValue().toString().equals(collectionStatus);
        boolean flag4= CaseInfo.CollectionStatus.NORMAL.getValue().toString().equals(collectionStatus);
        if(flag1 || flag2){
            queryparam.put("currentCollector","'"+userId+"'");
        }
        if(flag3 || flag4){ // 查询 已结清 与 归c，设置到 latelyCollector
            queryparam.put("latelyCollector","'"+userId+"'");
        }

        //以下是管理员权限的数据查询。 manager 是否具有查看下级用户的权限,NO_DATA_AUTH(0), DATA_AUTH(1);
        int isManager=user.getManager();
        if(1== isManager){
            String departCode=user.getDepartment().getCode();
            String currentCollector=null;
            if(StringUtils.isNotEmpty(departCode)){
                List<String>  list=followupRecord4MobileMapper.getAllUserIdByDeptCode(departCode);
                if(null != list && list.size()>0){
                    StringBuilder sb=new StringBuilder();
                    for (int i = 0; i < list.size(); i++) {
                        sb.append("'").append(list.get(i)).append("',");
                    }
                    currentCollector=sb.toString();
                    currentCollector=currentCollector.substring(0,currentCollector.length()-1);
                    if(flag1 || flag2){
                        queryparam.put("currentCollector",currentCollector);
                    }
                    if(flag3 || flag4){ // 查询 已结清 与 归c，设置到 latelyCollector
                        queryparam.put("latelyCollector",currentCollector);
                    }
                }

            }
        }
        return queryparam;
    }
    /**
     * @author zhangmingming  braveheart1115@163.com
     * @date 2018/10/28 0028 上午 11:39
     * @Description: 外访组长可以看到自己和组员的协催案件。
     * 因此当时组长的时候，将自己的id与组员的id拼接起来放到 assistCollector 上。
     */
    public static Map<String,String> getQueryparam4Assist(User user, String assistStatus, String personalName, String address, FollowupRecord4MobileMapper followupRecord4MobileMapper){
        Map<String,String> queryparam=new HashMap<>();
        String userId=user.getId();
        // 因为协催的催收中与待催收 的值与 外访的不一样，在后台转，前端就不用区分了。
        if(StringUtils.isNotEmpty(assistStatus)){
            if("20".equals(assistStatus)){
                assistStatus="118";  // 118, "协催待催收"
            }else if("21".equals(assistStatus)){
                assistStatus="28"; // 28, "协催催收中"
            }
        }
        // 协催使用的参数
        queryparam.put("assistCollector","'"+userId+"'");
        queryparam.put("assistStatus",assistStatus);
        queryparam.put("personalName",personalName);
        queryparam.put("address",address);

        /**
         * 以下是管理员权限的数据查询。 manager 是否具有查看下级用户的权限,NO_DATA_AUTH(0), DATA_AUTH(1);
         */
        int isManager=user.getManager();
        if(1== isManager){
            String departCode=user.getDepartment().getCode();
            String assistCollector=null;
            if(StringUtils.isNotEmpty(departCode)){
                List<String>  list=followupRecord4MobileMapper.getAllUserIdByDeptCode(departCode);
                if(null != list && list.size()>0){
                    StringBuilder sb=new StringBuilder();
                    for (int i = 0; i < list.size(); i++) {
                        sb.append("'").append(list.get(i)).append("',");
                    }
                    assistCollector=sb.toString();
                    assistCollector=assistCollector.substring(0,assistCollector.length()-1);
                }
            }
            queryparam.put("assistCollector",assistCollector);
        }
        return queryparam;
    }


    /**
     * @author zhangmingming  braveheart1115@163.com
     * @date 2018/10/26 0026 下午 1:02
     * @Description: 将地址转换为经纬度信息。
     */
    public static void setLatitudeAndLongitude4Visit(List<VisitModel4Mobile> list, RestTemplate restTemplate,FollowupRecord4MobileMapper followupRecord4MobileMapper,String userId){
        VisitModel4Mobile model=null;
        ResponseEntity<MapModel> enty = null;
        boolean flag1= false;
        boolean flag2= false;
        if(null != list && list.size()>0){
            LongitudeAndLatitudeModel tempModel=null;
            for (int i = 0; i < list.size(); i++) {
                model=list.get(i);
                flag1=StringUtils.isEmpty(model.getLatitude()+"") || model.getLatitude()==0.0;
                flag2=StringUtils.isEmpty(model.getLongitude()+"") || model.getLongitude()==0.0;
                if(flag1 || flag2){ // 当经纬度为空的时候
                    if(StringUtils.isNotEmpty(model.getAddress())){
                        try {
                            enty = restTemplate.getForEntity("http://business-service/api/personalController/getMapInfo?address="+model.getAddress(),MapModel.class);
                        }catch (Exception e){
                            log.error("调用business-service/api/personalController/getMapInfo 服务出错了，不能将地址转换为经纬度");
                            break;
                        }
                        if(null != enty){
                            model.setLatitude(enty.getBody().getLatitude());   // 纬度
                            model.setLongitude(enty.getBody().getLongitude());  // 经度
                            tempModel=new LongitudeAndLatitudeModel();
                            tempModel.setLatitude(enty.getBody().getLatitude());
                            tempModel.setLongitude(enty.getBody().getLongitude());
                            tempModel.setPId(model.getPId());
                            tempModel.setOperator(userId);
                            followupRecord4MobileMapper.updateLongitudeAndLatitude(tempModel);
                        }
                    }
                }
            }
        }
    }


    public static void setLatitudeAndLongitude4Assist(List<AssistModel4Mobile> list, RestTemplate restTemplate,FollowupRecord4MobileMapper followupRecord4MobileMapper,String userId){
        AssistModel4Mobile model=null;
        ResponseEntity<MapModel> enty = null;
        boolean flag1= false;
        boolean flag2= false;
        if(null != list && list.size()>0){
            LongitudeAndLatitudeModel tempModel=null;
            for (int i = 0; i < list.size(); i++) {
                model=list.get(i);
                flag1=StringUtils.isEmpty(model.getLatitude()+"") || model.getLatitude()==0.0;
                flag2=StringUtils.isEmpty(model.getLongitude()+"") || model.getLongitude()==0.0;
                if(flag1 || flag2){ // 当经纬度为空的时候
                    if(StringUtils.isNotEmpty(model.getAddress())){
                        try {
                            enty = restTemplate.getForEntity("http://business-service/api/personalController/getMapInfo?address="+model.getAddress(),MapModel.class);
                        }catch (Exception e){
                            log.error("调用business-service/api/personalController/getMapInfo 服务出错了，不能将地址转换为经纬度");
                            break;
                        }
                        if(null != enty){
                            model.setLatitude(enty.getBody().getLatitude());   // 纬度
                            model.setLongitude(enty.getBody().getLongitude());  // 经度
                            tempModel=new LongitudeAndLatitudeModel();
                            tempModel.setLatitude(enty.getBody().getLatitude());
                            tempModel.setLongitude(enty.getBody().getLongitude());
                            tempModel.setPId(model.getPId());
                            tempModel.setOperator(userId);
                            followupRecord4MobileMapper.updateLongitudeAndLatitude(tempModel);
                        }
                    }
                }
            }
        }
    }


}
