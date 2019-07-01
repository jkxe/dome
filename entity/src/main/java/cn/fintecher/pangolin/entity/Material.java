package cn.fintecher.pangolin.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import springfox.documentation.service.ApiListing;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "material")
@Data
public class Material extends BaseEntity{

    @ApiModelProperty(notes = "客户id")
    private String personalId;

    @ApiModelProperty(notes = "居住证明核查备注")
    private String residentDocMemo;

    @ApiModelProperty(notes = "居住证明审批备注")
    private String residentApproveMemo;

    @ApiModelProperty(notes = "收入证明核查备注")
    private String incomeDocMemo;

    @ApiModelProperty(notes = "收入证明审批备注")
    private String incomeApproveMemo;

    @ApiModelProperty(notes = "单位电话调查结果")
    private String cophoneExistind;

    @ApiModelProperty(notes = "单位电话备注")
    private String cophoneMemo;

    @ApiModelProperty(notes = "单位电话审批备注")
    private String cophoneApproveMemo;

    @ApiModelProperty(notes = "家庭电话备注")
    private String homePhoneMemo;

    @ApiModelProperty(notes = "家庭电话审批备注")
    private String homePhoneApproveMemo;

    @ApiModelProperty(notes = "本人手机备注")
    private String mobileMemo;

    @ApiModelProperty(notes = "本人手机审批备注")
    private String mobileApproveMemo;

    @ApiModelProperty(notes = "其他联系人备注")
    private String relativeMemo;

    @ApiModelProperty(notes = "系统校验备注")
    private String inetMemo;

    @ApiModelProperty(notes = "备注")
    private String remarks;

    @Transient
    private String certificatesNumber;
}
