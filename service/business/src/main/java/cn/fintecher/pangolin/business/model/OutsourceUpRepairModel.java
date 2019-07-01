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
public class OutsourceUpRepairModel extends BaseEntity{

    @ApiModelProperty(notes = "借据号")
    @ExcelAnno(cellName = "借据号")
    private String loanInvoiceNumber;

    @ApiModelProperty(notes = "客户姓名")
    @ExcelAnno(cellName = "客户姓名")
    private String coustmerName;

    @ApiModelProperty(notes = "身份证号")
    @ExcelAnno(cellName = "身份证号")
    private String idCard;

    @ApiModelProperty(notes = "手机号")
    @ExcelAnno(cellName = "手机号")
    private String phoneNumber;

    @ApiModelProperty(notes = "修复手机号")
    @ExcelAnno(cellName = "修复手机号")
    private String repairNumber;

    @ApiModelProperty(notes = "修复地址")
    @ExcelAnno(cellName = "修复地址")
    private String repairAddress;

    @ApiModelProperty(notes = "备注")
    @ExcelAnno(cellName = "备注")
    private String remark;

}
