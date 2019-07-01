package cn.fintecher.pangolin.business.model;

import cn.fintecher.pangolin.entity.User;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class OutsourceParam {
    private String id;

    @ApiModelProperty(notes = "特定公司的标识")
    private String companyCode;

    @ApiModelProperty(notes = "委外方编码")
    private String outsCode;

    @ApiModelProperty(notes = "委外方")
    private String outsName;

    @ApiModelProperty(notes = "市的id")
    private Integer areaId;

    @ApiModelProperty(notes = "详细地址")
    private String outsAddress;

    @ApiModelProperty(notes = "联系人")
    private String outsContacts;

    @ApiModelProperty(notes = "联系电话")
    private String outsPhone;

    @ApiModelProperty(notes = "手机号")
    private String outsMobile;

    @ApiModelProperty(notes = "邮箱")
    private String outsEmail;

    @Size(max = 1000, message = "备注不能超过1000个字符")
    @ApiModelProperty(notes = "备注")
    private String outsRemark;

    @ApiModelProperty(notes = "创建时间")
    private Date operateTime;


    @ApiModelProperty(notes = "操作员")
    private User user;

    @ApiModelProperty(notes = "委外方状态（0、启用，1、停用，2、暂停）")
    private Integer flag;

    @ApiModelProperty(notes = "机构类型")
    private Integer outsOrgtype;

    @ApiModelProperty(notes = "费率")
    private BigDecimal outsRate;

    @ApiModelProperty(notes = "合同开始时间")
    private String contractStartTime;

    @ApiModelProperty(notes = "合同结束时间")
    private String contractEndTime;

    @ApiModelProperty(notes = "等级")
    private Integer outsLevel;

    private List<Integer> areaList;
}
