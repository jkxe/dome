package cn.fintecher.pangolin.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by  hukaijia.
 * Description:
 * Date: 2017-08-02-10:16
 */
@Entity
@Table(name = "user_backcash_plan")
@Data
@ApiModel(value = "UserBackcashPlan", description = "用户计划回款金额")
public class UserBackcashPlan extends BaseEntity {
    @ApiModelProperty(notes = "用户名")
    private String userName;

    @ApiModelProperty(notes = "姓名")
    private String realName;

    @ApiModelProperty(notes = "年份")
    private Integer year;

    @ApiModelProperty(notes = "月份")
    private Integer month;

    @ApiModelProperty(notes = "回款金额")
    private BigDecimal backCash;

    @ApiModelProperty(notes = "创建人用户名")
    private String operator;

    @ApiModelProperty(notes = "创建时间")
    private Date operateTime;

    @ApiModelProperty(notes = "特定公司的标识")
    private String companyCode;
}
