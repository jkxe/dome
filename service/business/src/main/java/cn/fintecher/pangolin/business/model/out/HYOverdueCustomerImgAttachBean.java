package cn.fintecher.pangolin.business.model.out;

import lombok.Data;

@Data
public class HYOverdueCustomerImgAttachBean {
    private String userId;//客户id
    private String resourceId;//资源项id
    private String imageKind;//影像类型
    private String imageType;//影像类别
    private String imageName;//影像名称
    private String imageUrl;//影像url
    private String createTime;//创建时间
    private String updateTime;//修改时间
}
