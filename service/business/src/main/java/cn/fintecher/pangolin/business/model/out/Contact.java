package cn.fintecher.pangolin.business.model.out;

import lombok.Data;

/**
 * 联系人
 *
 */
@Data
public class Contact {
    private String relRelationToCh;//关系
    private String relName;//姓名
    private String relIdno;//身份证号
    private String relMobile;//手机号
    private String relhomeAdr;//住宅地址
    private String relTelno;//住宅电话
    private String relCompName;//工作单位
    private String relCompTelno;//工作电话

}
