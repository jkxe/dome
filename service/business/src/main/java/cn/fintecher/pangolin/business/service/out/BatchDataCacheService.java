package cn.fintecher.pangolin.business.service.out;

import cn.fintecher.pangolin.business.model.SyncDataModel;
import cn.fintecher.pangolin.entity.PersonalContact;
import cn.fintecher.pangolin.entity.Product;
import cn.fintecher.pangolin.entity.util.MD5;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @Author: hanwannan
 * @Description:
 * @Date 10:06 2017/9/5
 */
@Service("batchDataCacheService")
public class BatchDataCacheService {

    public void productMap_put(String key, Product value){
        SyncDataModel.productMap.put(key, value);
    }
    public void productMap_putAll(HashMap<String, Product> productMap){
        SyncDataModel.productMap.putAll(productMap);
    }
    public Product productMap_get(String key){
        Object value = SyncDataModel.productMap.get(key);
        if(value!=null){
            return (Product)value;
        }
        return null;
    }
    public void productMap_delete(){
        SyncDataModel.productMap.clear();
    }

    public void personalMap_put(String key, String value){
        SyncDataModel.personalMap.put(key, value);
    }
    public void personalMap_putAll(HashMap<String, String> personalMap){
        SyncDataModel.personalMap.putAll(personalMap);
    }
    public String personalMap_get(String key){
        Object value = SyncDataModel.personalMap.get(key);
        if(value!=null){
            return (String)value;
        }
        return null;
    }
    public void personalMap_delete(){
        SyncDataModel.personalMap.clear();
    }

    public void personalContactMap_put(String key, Object[] value){
        SyncDataModel.personalContactMap1.put(MD5.MD5Encode(key), value);
    }
    public void personalContactMap_delete(){
        SyncDataModel.personalContactMap1.clear();
    }

    public void personalJobMap_put(String key, String value){
        SyncDataModel.personalJobMap.put(key, value);
    }
    public void personalJobMap_putAll(HashMap<String, String> personalJobMap){
        SyncDataModel.personalJobMap.putAll(personalJobMap);
    }
    public String personalJobMap_get(String key){
        Object value=SyncDataModel.personalJobMap.get(key);
        if(value!=null){
            return (String) value;
        }
        return null;
    }
    public void personalJobMap_delete(){
        SyncDataModel.personalJobMap.clear();
    }

    public void personalAddressMap_put(String key, String value){
        SyncDataModel.personalAddressMap.put(MD5.MD5Encode(key), value);
    }
    public String personalAddressMap_get(String key){
        Object value=SyncDataModel.personalAddressMap.get(MD5.MD5Encode(key));
        if(value!=null){
            return (String) value;
        }
        return null;
    }
    public void personalAddressMap_delete(){
        SyncDataModel.personalAddressMap.clear();
    }

    public void personalBankMap_put(String key, String value){
        SyncDataModel.personalBankMap.put(key, value);
    }
    public void personalBankMap_putAll(HashMap<String, String> personalBankMap){
        SyncDataModel.personalBankMap.putAll(personalBankMap);
    }
    public String personalBankMap_get(String key){
        Object value=SyncDataModel.personalBankMap.get(key);
        if(value!=null){
            return (String) value;
        }
        return null;
    }
    public void personalBankMap_delete(){
        SyncDataModel.personalBankMap.clear();
    }

    public void personalImgFileMap_put(String key, String value){
        SyncDataModel.personalImgFileMap.put(key, value);
    }
    public void personalImgFileMap_putAll(HashMap<String, String> personalImgFileMap){
        SyncDataModel.personalImgFileMap.putAll(personalImgFileMap);
    }
    public String personalImgFileMap_get(String key){
        Object value=SyncDataModel.personalImgFileMap.get(key);
        if(value!=null){
            return (String) value;
        }
        return null;
    }
    public void personalImgFileMap_delete(){
        SyncDataModel.personalImgFileMap.clear();
    }

    public void personalSocialPlatMap_put(String key, String value){
        SyncDataModel.personalSocialPlatMap.put(key, value);
    }
    public void personalSocialPlatMap_putAll(HashMap<String, String> personalSocialPlatMap){
        SyncDataModel.personalSocialPlatMap.putAll(personalSocialPlatMap);
    }
    public String personalSocialPlatMap_get(String key){
        Object value=SyncDataModel.personalSocialPlatMap.get(key);
        if(value!=null){
            return (String) value;
        }
        return null;
    }
    public void personalSocialPlatMap_delete(){
        SyncDataModel.personalSocialPlatMap.clear();
    }

    public void personalAstOperCrdtMap_put(String key, String value){
        SyncDataModel.personalAstOperCrdtMap.put(key, value);
    }
    public void personalAstOperCrdtMap_putAll(HashMap<String, String> personalAstOperCrdtMap){
        SyncDataModel.personalAstOperCrdtMap.putAll(personalAstOperCrdtMap);
    }
    public String personalAstOperCrdtMap_get(String key){
        Object value=SyncDataModel.personalAstOperCrdtMap.get(key);
        if(value!=null){
            return (String) value;
        }
        return null;
    }
    public void personalAstOperCrdtMap_delete(){
        SyncDataModel.personalAstOperCrdtMap.clear();
    }

    public void personalFileAttachMap_put(String key, String value){
        SyncDataModel.caseFileMap.put(key, value);
    }
    public void personalFileAttachMap_putAll(HashMap<String, String> personalFileAttachMap){
        SyncDataModel.caseFileMap.putAll(personalFileAttachMap);
    }
    public String personalFileAttachMap_get(String key){
        Object value=SyncDataModel.caseFileMap.get(key);
        if(value!=null){
            return (String) value;
        }
        return null;
    }
    public void personalFileAttachMap_delete(){
        SyncDataModel.caseFileMap.clear();
    }

    public void caseInfoDistributedMap_put(String key, Object[] value){
        SyncDataModel.caseInfoDistributedMap.put(key, value);
    }
    public void caseInfoDistributedMap_putAll(HashMap<String, Object[]> caseInfoDistributedMap){
        SyncDataModel.caseInfoDistributedMap.putAll(caseInfoDistributedMap);
    }
    public Object[] caseInfoDistributedMap_get(String key){
        Object[] value=SyncDataModel.caseInfoDistributedMap.get(key);
        if(value!=null){
            return (Object[]) value;
        }
        return null;
    }
    public void caseInfoDistributedMap_delete(){
        SyncDataModel.caseInfoDistributedMap.clear();
    }

    public void caseInfoMap_put(String key, Object[] value){
        SyncDataModel.caseInfoMap.put(key, value);
    }
    public void caseInfoMap_putAll(HashMap<String, Object[]> caseInfoMap){
        SyncDataModel.caseInfoMap.putAll(caseInfoMap);
    }
    public Object[] caseInfoMap_get(String key){
        Object value=SyncDataModel.caseInfoMap.get(key);
        if(value!=null){
            return (Object[]) value;
        }
        return null;
    }
    public void caseInfoMap_delete(){
        SyncDataModel.caseInfoMap.clear();
    }

    public void caseInfoDistributedMapByUser_put(String key, Object[] value){
        SyncDataModel.caseInfoDistributedMapByUser.put(key, value);
    }
    public void caseInfoDistributedMapByUser_putAll(HashMap<String, Object[]> caseInfoDistributedMapByUser){
        SyncDataModel.caseInfoDistributedMapByUser.putAll(caseInfoDistributedMapByUser);
    }
    public Object[] caseInfoDistributedMapByUser_get(String key){
        Object value=SyncDataModel.caseInfoDistributedMapByUser.get(key);
        if(value!=null){
            return (Object[]) value;
        }
        return null;
    }
    public void caseInfoDistributedMapByUser_delete(){
        SyncDataModel.caseInfoDistributedMapByUser.clear();
    }

    public void caseInfoMapByUser_put(String key, Object[] value){
        SyncDataModel.caseInfoMapByUser.put(key, value);
    }
    public Object[] caseInfoMapByUser_get(String key){
        Object value=SyncDataModel.caseInfoMapByUser.get(key);
        if(value!=null){
            return (Object[]) value;
        }
        return null;
    }
    public void caseInfoMapByUser_delete(){
        SyncDataModel.caseInfoMapByUser.clear();
    }

    public void overCaseInfoMap_put(String key, List<Object[]> value){
        SyncDataModel.overCaseInfoMap.put(key, value);
    }
    public List<Object[]> overCaseInfoMap_get(String key){
        Object value =SyncDataModel.overCaseInfoMap.get(key);
        if(value!=null){
            return (List<Object[]>) value;
        }
        return null;
    }
    public void overCaseInfoMap_delete(){
        SyncDataModel.overCaseInfoMap.clear();
    }


    /**
     * 数据库中的所有联系人唯一标识集合
     */
    private static Set<String> personalContactUniqueIdentifyCache = new CopyOnWriteArraySet<>();

    public static void personalContactUniqueIdentifyCacheAdd(String element) {
        personalContactUniqueIdentifyCache.add(element);
    }
    public static boolean personalContactUniqueIdentifyCacheContains(String element) {
        return personalContactUniqueIdentifyCache.contains(element);
    }
    public static void personalContactUniqueIdentifyCacheClear() {
        personalContactUniqueIdentifyCache.clear();
    }

    /**
     * 最终要保存的联系人数据
     */
    private static ConcurrentMap<String, PersonalContact> personalContactCache = new ConcurrentHashMap<>();

    public static void personalContactCachePut(String key, PersonalContact value) {
        personalContactCache.put(key, value);
    }

    public static Map<String, PersonalContact> personalContactCacheGetAll() {
        return personalContactCache;
    }
    public static void personalContactCacheClear() {
        personalContactCache.clear();
    }
}
