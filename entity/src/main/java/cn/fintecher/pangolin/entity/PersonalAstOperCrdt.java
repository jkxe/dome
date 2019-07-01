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
@Table(name = "hy_personal_ast_oper_crdt")
@Data
public class PersonalAstOperCrdt implements Serializable {

    @Id
    private String id;

    @ApiModelProperty(notes = "客户id")
    private String customerId;

    @ApiModelProperty(notes = "资源项id")
    private String resourceId;

    @ApiModelProperty(notes = "资源项类型")
    private String resourceType;

    @ApiModelProperty(notes = "数据体")
    private String originalData;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("更新时间")
    private Date updateTime;

}
