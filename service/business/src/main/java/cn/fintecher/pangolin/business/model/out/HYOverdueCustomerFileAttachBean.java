package cn.fintecher.pangolin.business.model.out;

import lombok.Data;

@Data
public class HYOverdueCustomerFileAttachBean {
    private String contractId;//合同id
    private String userId;//客户id
    private String contractType;//合同类型
    private String contractStatus;//合同状态
    private String contractName;//合同名称
    private String creditEndDate;//授信到期日
    private String authorizedValid;//授信有效期
    private String productName;//产品名称(手机号)
    private String productNo;//产品编号
    private String bak1;//备用字段1 存储法大大uid
    private String bak2;//备用字段2
    private String bak3;//备用字段3
    private String createTime;//创建时间
    private String updateTime;//修改时间
}
