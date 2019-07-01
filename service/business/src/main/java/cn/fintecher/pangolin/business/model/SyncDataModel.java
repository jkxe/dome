package cn.fintecher.pangolin.business.model;

import cn.fintecher.pangolin.entity.*;
import lombok.Data;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 */
@Data
public class SyncDataModel {

    public static final int perHandleCount=3000;

    public static final int OVERDUE_COUNT=1000;

    public static int caseNumberTemp=0;

    public static Map<String, String> personalMap=new ConcurrentHashMap<>();
    public static Map<String, String> personalAddressMap=new ConcurrentHashMap<>();
    //关联人不同维度保存
    //userId+relation+phone
    public static Map<String, Object[]> personalContactMap1=new ConcurrentHashMap<>();
    //userId+relationUserId
    public static Map<String, Object[]> personalContactMap2=new ConcurrentHashMap<>();

    public static Map<String, String> personalJobMap=new ConcurrentHashMap<>();
    public static Map<String, String> personalBankMap=new ConcurrentHashMap<>();
    public static Map<String, String> personalImgFileMap=new ConcurrentHashMap<>();
    public static Map<String, String> personalSocialPlatMap=new ConcurrentHashMap<>();
    public static Map<String, String> personalAstOperCrdtMap=new ConcurrentHashMap<>();
    public static Map<String, String> caseFileMap=new ConcurrentHashMap<>();
    public static Map<String, Product> productMap=new ConcurrentHashMap<>();
    //以借据为维度
    public static Map<String, Object[]> caseInfoDistributedMap=new ConcurrentHashMap<>();
    public static Map<String, Object[]> caseInfoMap=new ConcurrentHashMap<>();
    //以用户为维度
    public static Map<String, Object[]> caseInfoDistributedMapByUser=new ConcurrentHashMap<>();
    public static Map<String, Object[]> caseInfoMapByUser=new ConcurrentHashMap<>();
    //已结清案件
    public static Map<String, List<Object[]>> overCaseInfoMap=new ConcurrentHashMap<>();
    //字典枚举值
    //教育程度
    public static Map<String, String> educationMap=new ConcurrentHashMap<>();
    //婚姻状况
    public static Map<String, String> marrageMap=new ConcurrentHashMap<>();
    //职业
    public static Map<String, String> professionalMap=new ConcurrentHashMap<>();
    //行业
    public static Map<String, String> industryMap=new ConcurrentHashMap<>();
    //职务
    public static Map<String, String> positionMap=new ConcurrentHashMap<>();
    //工作单位性质
    public static Map<String, String> unitPropertyMap=new ConcurrentHashMap<>();
    //证件类型
    public static Map<String, String> certificateTypeMap=new ConcurrentHashMap<>();
    //关联人关系
    public static Map<String, String> personalRelationMap=new ConcurrentHashMap<>();

    private Map<String, OverdueDetail> overdueDetailInsertMap = new ConcurrentHashMap<>();
    //待分配案件列表更新记录MAP
    private Map<String, CaseInfoDistributed> CaseInfoDistributedUdpateMap = new ConcurrentHashMap<>();
    //待分配案件列表新增记录MAP
    private Map<String, CaseInfoDistributed> CaseInfoDistributedInsertMap = new ConcurrentHashMap<>();
//    //本批客户共债案件
//    private Map<String, CaseInfoDistributed> CaseInfoDistributedByUserMap = new ConcurrentHashMap<>();
    //CaseInfo更新列表
    private Map<String, CaseInfo> caseInfoUpdateMap = new ConcurrentHashMap<>();
    //CaseInfo新增列表
    private Map<String, CaseInfo> caseInfoInsertMap = new ConcurrentHashMap<>();
    //personl更新列表
    private Map<String, Personal> personalInsertMap = new ConcurrentHashMap<>();
    //personl更新列表
    private Map<String, Personal> personalUpdateMap = new ConcurrentHashMap<>();
    //personlJob新增列表
    private Map<String, PersonalJob> personalJobInsertMap = new ConcurrentHashMap<>();
    //personlJob更新列表
    private Map<String, PersonalJob> personalJobUpdateMap = new ConcurrentHashMap<>();
    //personlAddress新增列表
    private Map<String, PersonalAddress> personalAddressInsertMap = new ConcurrentHashMap<>();
    //personlAddress更新列表
    private Map<String, PersonalAddress> personalAddressUpdateMap = new ConcurrentHashMap<>();
    //personlBank新增列表
    private Map<String, PersonalBank> personalBankInsertMap = new ConcurrentHashMap<>();
    //personlBank更新列表
    private Map<String, PersonalBank> personalBankUpdateMap = new ConcurrentHashMap<>();
    //CaseFile新增列表
    private Map<String, CaseFile> caseFileInsertMap = new ConcurrentHashMap<>();
    //CaseFile更新列表
    private Map<String, CaseFile> caseFileUpdateMap = new ConcurrentHashMap<>();
    //PersonalImgFile新增列表
    private Map<String, PersonalImgFile> personalImgFileInsertMap = new ConcurrentHashMap<>();
    //PersonalImgFile更新列表
    private Map<String, PersonalImgFile> personalImgFileUpdateMap = new ConcurrentHashMap<>();
    //PersonalContact新增列表
    private Map<String, PersonalContact> personalContactInsertMap = new ConcurrentHashMap<>();
    //PersonalContact更新列表
    private Map<String, PersonalContact> personalContactUpdateMap = new ConcurrentHashMap<>();
    //PersonalSocialPlat新增列表
    private Map<String, PersonalSocialPlat> personalSocialPlatInsertMap = new ConcurrentHashMap<>();
    //PersonalSocialPlat更新列表
    private Map<String, PersonalSocialPlat> personalSocialPlatUpdateMap = new ConcurrentHashMap<>();
    //PersonalAstOperCrdt新增列表
    private Map<String, PersonalAstOperCrdt> personalAstOperCrdtInsertMap = new ConcurrentHashMap<>();
    //PersonalAstOperCrdt更新列表
    private Map<String, PersonalAstOperCrdt> personalAstOperCrdtUpdateMap = new ConcurrentHashMap<>();

    //ExceptionData新增列表
    private Map<String, ExceptionData> exceptionDataInsertMap = new ConcurrentHashMap<>();


    //客户信息(身份证号)
    private Map<String, Personal> existPMap = new ConcurrentHashMap<>();
    private Map<String, Personal> insertPMap = new ConcurrentHashMap<>();
    //Material
    private Map<String, Material> insertMaMap = new ConcurrentHashMap<>();
    //PersonalIncomeExp
    private Map<String, PersonalIncomeExp> insertPIMap = new ConcurrentHashMap<>();
    //PersonalJob
    private Map<String, PersonalJob> insertPJMap = new ConcurrentHashMap<>();
    //开户信息
    private Map<String, PersonalBank> insertBMap = new ConcurrentHashMap<>();
    //PersonalCar
    private Map<String, PersonalCar> insertPCarMap = new ConcurrentHashMap<>();
    //PersonalProperty
    private Map<String, PersonalProperty> insertPPMap = new ConcurrentHashMap<>();
    //PersonalContact
    private Map<String, PersonalContact> insertPConMap = new ConcurrentHashMap<>();
    //PersonalAddress
    private Map<String, PersonalAddress> insertAddressMap = new ConcurrentHashMap<>();

    //产品系列(系列名称)
    private Map<String, ProductSeries> insertPSMap = new ConcurrentHashMap<>();
    private Map<String, ProductSeries> existPSMap = new ConcurrentHashMap<>();
    //产品（Name_Type_SeriesId）
    private Map<String, Product> insertPCMap = new ConcurrentHashMap<>();
    private Map<String, Product> existPCMap = new ConcurrentHashMap<>();
    //PayPlan
    private Map<String, PayPlan> insertPayPMap = new ConcurrentHashMap<>();
    private Map<String, String> deletePayPMap = new ConcurrentHashMap<>();
    //CaseFile
    private Map<String, CaseFile> insertCaseFileMap = new ConcurrentHashMap<>();
    private Map<String, Commodity> insertCommodity = new ConcurrentHashMap<>();
    private Map<String, List<Commodity>> existCommodityMap = new ConcurrentHashMap<>();
    //orderInfo
    private Map<String, OrderInfo> insertOrderInfoMap = new ConcurrentHashMap<>();
    private Map<String, OrderInfo> selectOrderInfoId = new ConcurrentHashMap<>();
    private Map<String, OrderInfo> deleteOrderInfoMap = new ConcurrentHashMap<>();


    //OrderRepaymentPlan
    private Map<String, OrderRepaymentPlan> insertOrderRepaymentPlan = new ConcurrentHashMap<>();
    private Map<String, OrderRepaymentPlan> deleteOrderRepaymentPlanMap = new ConcurrentHashMap<>();

    private Map<String, WriteOffDetails> insertWriteOffDetailsMap = new ConcurrentHashMap<>();
    private Map<String, String> deleteWriteOffDetailsMap = new ConcurrentHashMap<>();
}
