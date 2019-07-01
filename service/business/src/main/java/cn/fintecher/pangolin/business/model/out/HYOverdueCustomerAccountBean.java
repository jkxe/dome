package cn.fintecher.pangolin.business.model.out;

import lombok.Data;

@Data
public class HYOverdueCustomerAccountBean {
    private String userId;//客户id
    private String resourceId;//资源项id
    private String accountKind;//账号类型
    private String branchName;//开户机构名称
    private String accountName;//户名
    private String account;//账号
    private String buildDate;//账号创建日期
    private String createTime;//创建时间
    private String updateTime;//更新时间
}
