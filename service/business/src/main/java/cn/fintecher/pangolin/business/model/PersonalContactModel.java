package cn.fintecher.pangolin.business.model;

import cn.fintecher.pangolin.entity.PersonalContact;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Transient;
import java.util.Date;
import java.util.List;

/**
 * @author : xiaqun
 * @Description : 客户联系人模型
 * @Date : 11:55 2017/8/17
 */

@Data
public class PersonalContactModel {
    @Id
    private String id;
    private Integer relation; //关系
    private String name; //联系人姓名
    private Integer informed; // 是否知晓此项借款
    private String phone;// 手机号码
    private Integer phoneStatus;//联系电话状态
    private String mail;//电子邮箱
    private String mobile;//固定电话
    private String idCard;//身份证号码
    private String employer;//联系人工作单位
    private String department;//联系人部门
    private String position;//联系人职位
    private Integer source;//数据来源
    private String address;//联系人的现居住地址
    private String workPhone;//联系人单位电话
    private String operator;//操作员
    private Date operatorTime;//操作时间
    private Integer socialType;//社交帐号类型
    private String socialValue;//社交帐号内容
    private String personalId;//客户信息ID
    private Integer addressStatus;//地址状态
    private String customerId;//hy-客户ID
    private String relationUserId;//客户联系人id
    private String clientRelation;//客户关系代码
    private String relationCertificateKind;//联系人证件类型
    private Date createTime;//创建时间
    private Date updateTime;//更新时间
    private String certificatesNumber;
    private Integer connectionRate;//接通率

}