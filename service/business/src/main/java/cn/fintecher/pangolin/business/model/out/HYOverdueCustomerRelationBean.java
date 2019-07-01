package cn.fintecher.pangolin.business.model.out;

import lombok.Data;

@Data
public class HYOverdueCustomerRelationBean {
    private String userId;//客户id
    private String relationUserId;//客户关系人id
    private String clientRelation;//客户关系代码
    private String relationType;//关系类型
    private String relationClientName;//客户关系人名字
    private String relationCertificateKind;//客户关系人证件类型
    private String relationCertificateNo;//客户关系人证件编号
    private String relationTelephone;//客户关系人电话
    private String createTime;//创建时间
    private String updateTime;//更新时间
}
