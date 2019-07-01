package cn.fintecher.pangolin.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * @Author : hanwannan
 * @Description : 客户社交平台信息
 * @Date : 2017/8/15
 */
@Entity
@Table(name = "hy_personal_social_plats")
@Data
public class PersonalSocialPlat implements Serializable {

    @Id
    private String id;

    @ApiModelProperty(notes = "客户id")
    private String customerId;

    @ApiModelProperty(notes = "联系点id")
    private String stationId;

    @ApiModelProperty(notes = "社交类型")
    private String socialType;

    @ApiModelProperty(notes = "账号")
    private String account;

    @ApiModelProperty(notes = "昵称")
    private String nickName;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("更新时间")
    private Date updateTime;

}
