package cn.fintecher.pangolin.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author frank  braveheart1115@163.com
 * @Description:
 * @Package cn.fintecher.pangolin.dataimp.entity
 * @ClassName: cn.fintecher.pangolin.dataimp.entity.OfflineHistoryRemark
 * @date 2018年06月28日 09:48
 */
@Data
@Entity
@ApiModel(description = "线下历史催记")
@Table(name = "offline_history_remark")
public class OfflineHistoryRemark extends BaseEntity{


    @ApiModelProperty("申请号")
    private String applicationNumber;


    @ApiModelProperty("客户姓名")
    private String clientName;


    @ApiModelProperty("分案月份")
    private String divisionMonth;

    @ApiModelProperty("催记")
    private String collectionRemark;

    @ApiModelProperty("催收员")
    private String collectionUser;



}
