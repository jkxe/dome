package cn.fintecher.pangolin.business.model.out;

import lombok.Data;

@Data
public class HYOverdueCustomerDetailBean {
    private String userId;
    private String clientName;//客户名称(姓名)
    private String certificateNo;//证件号码(客户身份证号)
    private String invalidDate;//证件失效时间
    private String telephone;//手机号码(手机号)
    private String beRecommenderName;//推荐人
    private String companyName;//公司名称
    private String companyTelephone;//公司电话
    private String companyTelAreaCode;//公司电话区号
    private String companyTelephoneExt;//公司电话分机号
    private String companyAddress;//公司地址
    private String contact;//联系人
    private String contactTel;//联系人电话
    private String livingAddress;//住宅地址
    private String spouseName;//配偶姓名
    private String spouseTel;//配偶手机
    private String age;//年龄
    private String sex;//性别
    private String education;//教育程度
    private String marriage;//婚姻状况
    private String career;//职业
    private String industry;//行业
    private String duty;//职务
    private String unitProperty;//工作单位性质
    private String workingYears;//工作年限
    private String bankcard;//银行卡号
    private String livingProvince;//住宅省级编码
    private String livingCity;//住宅市级编码
    private String livingArea;//住宅区级编码
    private String livingProvinceName;//住宅省
    private String livingCityName;//住宅市
    private String livingAreaName;//住宅区
    private String companyProvince;//单位地址省级编码
    private String companyCity;//单位地址市级编码
    private String companyArea;//单位地址区级编码
    private String companyProvinceName;//单位地址省
    private String companyCityName;//单位地址市
    private String companyAreaName;//单位地址区
    private String certificateKind;//证件类型
    private String creditLevel;//信用等级
    private String permanentAddress;//户籍地址
    private String createTime;//创建时间
    private String updateTime;//更新时间
}
