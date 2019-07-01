package com.hsjry.user.facade.pojo.request;

import java.io.Serializable;
import java.util.List;

import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotNull;

import com.hsjry.user.facade.pojo.UserImageDiffDataPojo;
import com.hsjry.user.facade.pojo.enums.EnumRelationDimension;

/**
 * 新建或修改影像资料资源项信息请求
 * 
 * @author jiangjd12837
 * @version $Id: modifyBasicResourceInfoRequest.java, v 1.0 2017年3月14日 下午3:35:49 jiangjd12837 Exp $
 */
public class ModifyDataResourceInfoRequest implements Serializable {

    /**  */
    private static final long           serialVersionUID = 6421623291371828408L;
    //维度ID
    @NotNull(errorCode = "000001", message = "维度ID")
    @NotBlank(errorCode = "000001", message = "维度ID")
    private String                      dimensionId;
    //关系维度
    @NotNull(errorCode = "000001", message = "关系维度")
    private EnumRelationDimension       enumRelationDimension;
    //影像资料列表
    private List<UserImageDiffDataPojo> userImageDiffDataPojos;

    /**
     * Getter method for property <tt>dimensionId</tt>.
     * 
     * @return property value of dimensionId
     */
    public String getDimensionId() {
        return dimensionId;
    }

    /**
     * Setter method for property <tt>dimensionId</tt>.
     * 
     * @param dimensionId value to be assigned to property dimensionId
     */
    public void setDimensionId(String dimensionId) {
        this.dimensionId = dimensionId;
    }

    /**
     * Getter method for property <tt>enumRelationDimension</tt>.
     * 
     * @return property value of enumRelationDimension
     */
    public EnumRelationDimension getEnumRelationDimension() {
        return enumRelationDimension;
    }

    /**
     * Setter method for property <tt>enumRelationDimension</tt>.
     * 
     * @param enumRelationDimension value to be assigned to property enumRelationDimension
     */
    public void setEnumRelationDimension(EnumRelationDimension enumRelationDimension) {
        this.enumRelationDimension = enumRelationDimension;
    }

    /**
     * Getter method for property <tt>userImageDiffDataPojos</tt>.
     * 
     * @return property value of userImageDiffDataPojos
     */
    public List<UserImageDiffDataPojo> getUserImageDiffDataPojos() {
        return userImageDiffDataPojos;
    }

    /**
     * Setter method for property <tt>userImageDiffDataPojos</tt>.
     * 
     * @param userImageDiffDataPojos value to be assigned to property userImageDiffDataPojos
     */
    public void setUserImageDiffDataPojos(List<UserImageDiffDataPojo> userImageDiffDataPojos) {
        this.userImageDiffDataPojos = userImageDiffDataPojos;
    }

}
