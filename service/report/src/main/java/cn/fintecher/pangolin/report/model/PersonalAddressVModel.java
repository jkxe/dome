package cn.fintecher.pangolin.report.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author zhangmingming  braveheart1115@163.com
 * @Description:  客户地址信息 视图 model对象
 * @Package cn.fintecher.pangolin.report.model
 * @ClassName: cn.fintecher.pangolin.report.model.PersonalAddressVModel
 * @date 2018年09月30日 14:24
 */
@Data
public class PersonalAddressVModel{

    @ApiModelProperty(notes = "关系：145-本人，146-配偶，147-父母，148-子女，149-亲属，150-同事，151-朋友，152-其他，153-亲兄弟姐妹，154-单位")
    private Integer relation;

    @ApiModelProperty(notes = "关系名称")
    private String relationName;

    @ApiModelProperty(notes = "姓名")
    private String name;

    @ApiModelProperty(notes = "地址类型")
    private Integer type;

    @ApiModelProperty(notes = "地址类型名称")
    private String typeName;

    @ApiModelProperty(notes = "地址状态")
    private Integer status;

    @ApiModelProperty(notes = "地址状态名称")
    private String statusName;

    @ApiModelProperty(notes = "数据来源")
    private Integer source;

    @ApiModelProperty(notes = "数据来源名称")
    private String sourceName;

    @ApiModelProperty(notes = "详细地址")
    private String detail;

    @ApiModelProperty(notes = "客户ID")
    private String personalId;

    @ApiModelProperty(notes = "操作员")
    private String operator;

    @ApiModelProperty(notes = "操作时间")
    private Date operatorTime;

    @ApiModelProperty(notes = "经度")
    private BigDecimal longitude;

    @ApiModelProperty(notes = "纬度")
    private BigDecimal latitude;
}
