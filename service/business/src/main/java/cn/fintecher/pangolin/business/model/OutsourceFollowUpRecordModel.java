package cn.fintecher.pangolin.business.model;

import cn.fintecher.pangolin.entity.BaseEntity;
import cn.fintecher.pangolin.entity.util.ExcelAnno;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;


/**
 * Created by huaynmin on 2017/9/26.
 * @Description : 委外跟进记录实体类
 */
@Data
public class OutsourceFollowUpRecordModel extends BaseEntity{

    @ApiModelProperty(notes = "借据号")
    @ExcelAnno(cellName = "借据号")
    private String caseNum;


    @ApiModelProperty(notes = "跟进时间")
    @ExcelAnno(cellName = "跟进时间")
    private Date followTime;

    @ApiModelProperty(notes = "跟进方式")
    @ExcelAnno(cellName = "跟进方式")
    private String followType;

    @ApiModelProperty(notes = "催收对象")
    @ExcelAnno(cellName = "催收对象")
    private String objectName;

    @ApiModelProperty(notes = "姓名")
    @ExcelAnno(cellName = "姓名")
    private String userName;

    @ApiModelProperty(notes = "电话状态")
    @ExcelAnno(cellName = "电话状态")
    private String telStatus;

    @ApiModelProperty(notes = "催收反馈")
    @ExcelAnno(cellName = "催收反馈")
    private String feedback;

    @ApiModelProperty(notes = "跟进内容")
    @ExcelAnno(cellName = "跟进内容")
    private String followRecord;

    @ApiModelProperty(notes = "跟进人员")
    @ExcelAnno(cellName = "跟进人员")
    private String followPerson;

    @ApiModelProperty(notes = "催记来源：电催 ，外访 ，协催 ，委外，特殊，停催")
    @ExcelAnno(cellName = "催记来源")
    private Integer source;

    @ApiModelProperty(notes = "催收类型")
    @ExcelAnno(cellName = "催收类型")
    private Integer collectionType;

    @ApiModelProperty(notes = "电话/地址")
    @ExcelAnno(cellName = "电话/地址")
    private String contactPhoneOrAddress;

    @ApiModelProperty(notes = "受托方")
    @ExcelAnno(cellName = "受托方")
    private String principalName;
}
