package cn.fintecher.pangolin.business.model.out;

import lombok.Data;

@Data
public class HYOverdueCustomerSocialPlatBean {
    private String userId;//客户id
    private String stationId;//联系点id
    private String socialType;//社交类型
    private String account;//账号
    private String nickName;//昵称
    private String createTime;//创建时间
    private String updateTime;//更新时间
}
