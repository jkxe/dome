package com.hsjry.user.facade.pojo.request;

import com.hsjry.lang.common.base.enums.user.EnumCertificateKind;
import com.hsjry.user.facade.pojo.enums.EnumRelationDimension;

import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotNull;

import java.io.Serializable;

/**
 * @Description:修改证件信息
 * @author zhengjl <zhengjl20204@hundsun.com>
 * @version V1.2.3
 * @date 2017年07月02日  21:33
 */
public class ModifyUserCertificationInfoRequest implements Serializable {

    private static final long serialVersionUID = -3845424455867067111L;
    //维度ID
    @NotNull(errorCode = "000001", message = "维度ID")
    @NotBlank(errorCode = "000001", message = "维度ID")
    private String                             dimensionId;
    //关系维度
    @NotNull(errorCode = "000001", message = "关系维度")
    private EnumRelationDimension enumRelationDimension;

    @NotNull(errorCode = "000001", message = "证件号")
    @NotBlank(errorCode = "000001", message = "证件号")
    private String certificateNo;

    @NotNull(errorCode = "000001", message = "证件类型")
    private EnumCertificateKind enumCertificateKind;


    public String getDimensionId() {
        return dimensionId;
    }

    public void setDimensionId(String dimensionId) {
        this.dimensionId = dimensionId;
    }

    public EnumRelationDimension getEnumRelationDimension() {
        return enumRelationDimension;
    }

    public void setEnumRelationDimension(EnumRelationDimension enumRelationDimension) {
        this.enumRelationDimension = enumRelationDimension;
    }

    public String getCertificateNo() {
        return certificateNo;
    }

    public void setCertificateNo(String certificateNo) {
        this.certificateNo = certificateNo;
    }

    public EnumCertificateKind getEnumCertificateKind() {
        return enumCertificateKind;
    }

    public void setEnumCertificateKind(EnumCertificateKind enumCertificateKind) {
        this.enumCertificateKind = enumCertificateKind;
    }
}
