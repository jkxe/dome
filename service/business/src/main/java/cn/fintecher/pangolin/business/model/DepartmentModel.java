/**
 * Bestpay.com.cn Inc.
 * Copyright (c) 2011-2018 All Rights Reserved.
 */
package cn.fintecher.pangolin.business.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 *@description 返回部门模型
 *@author duanwenwu
 *@date 18/2/27
 */
@Data
public class DepartmentModel implements Serializable {


    @ApiModelProperty(notes = "主键")
    private String id;

    @ApiModelProperty(notes = "所属公司的特定标识")
    private String companyCode;

    @ApiModelProperty(notes = "父机构的id")
    private String parentId;

    @ApiModelProperty(notes = "机构的名称")
    private String name;

    @ApiModelProperty(notes = "机构编号")
    private String code;

    @ApiModelProperty(notes = "机构类型")
    private Integer type;

    @ApiModelProperty(notes = "机构状态（0是启用  1 是停用）")
    private Integer status;

    @ApiModelProperty(notes = "机构的描述")
    private String remark;

    @ApiModelProperty(notes = "备用字段")
    private String field;

    @ApiModelProperty(notes = "机构等级")
    private Integer level;

    @ApiModelProperty(notes = "创建人")
    private String operator;

    @ApiModelProperty(notes = "创建时间")
    private Date operateTime;
}
