package com.hsjry.user.facade.pojo;

import java.io.Serializable;

import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotNull;

import com.hsjry.lang.common.base.enums.user.EnumImageKind;
import com.hsjry.user.facade.pojo.enums.EnumImageType;
import com.hsjry.user.facade.pojo.enums.EnumResourceSource;
import com.hsjry.user.facade.pojo.enums.EnumResourceStatus;

/**影像资料
 * 
 * @author liaosq23298
 * @version $Id: UserImageDataandSourcePojo.java, v 0.1 Nov 22, 2017 4:18:48 PM liaosq23298 Exp $
 */
public class UserImageDataandSourcePojo implements Serializable{

    /**  */
    private static final long serialVersionUID = 4859525311401360251L;
    //资源项ID
    private String             resourceId;
    //登记ID
    private String             registerId;
    //影像类型
    @NotNull(errorCode = "000001", message = "影像类型")
    private EnumImageKind      imageKind;
    //影像类别
    @NotNull(errorCode = "000001", message = "影像类别")
    private EnumImageType      imageType;
    //影像名称
    private String             imageName;
    //影像URL
    @NotNull(errorCode = "000001", message = "影像Url")
    @NotBlank(errorCode = "000001", message = "影像Url")
    private String             imageUrl;
    //影像状态
    @NotNull(errorCode = "000001", message = "影像状态")
    private EnumResourceStatus imageStatus;
    //资源来源
    private EnumResourceSource resourceSource;

    /**
     * Getter method for property <tt>imageStatus</tt>.
     * 
     * @return property value of imageStatus
     */
    public EnumResourceStatus getImageStatus() {
        return imageStatus;
    }

    /**
     * Setter method for property <tt>imageStatus</tt>.
     * 
     * @param imageStatus value to be assigned to property imageStatus
     */
    public void setImageStatus(EnumResourceStatus imageStatus) {
        this.imageStatus = imageStatus;
    }

    /**
     * Getter method for property <tt>resourceId</tt>.
     * 
     * @return property value of resourceId
     */
    public String getResourceId() {
        return resourceId;
    }

    /**
     * Setter method for property <tt>resourceId</tt>.
     * 
     * @param resourceId value to be assigned to property resourceId
     */
    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    /**
     * Getter method for property <tt>registerId</tt>.
     * 
     * @return property value of registerId
     */
    public String getRegisterId() {
        return registerId;
    }

    /**
     * Setter method for property <tt>registerId</tt>.
     * 
     * @param registerId value to be assigned to property registerId
     */
    public void setRegisterId(String registerId) {
        this.registerId = registerId;
    }

    /**
     * Getter method for property <tt>imageKind</tt>.
     * 
     * @return property value of imageKind
     */
    public EnumImageKind getImageKind() {
        return imageKind;
    }

    /**
     * Setter method for property <tt>imageKind</tt>.
     * 
     * @param imageKind value to be assigned to property imageKind
     */
    public void setImageKind(EnumImageKind imageKind) {
        this.imageKind = imageKind;
    }

    /**
     * Getter method for property <tt>imageType</tt>.
     * 
     * @return property value of imageType
     */
    public EnumImageType getImageType() {
        return imageType;
    }

    /**
     * Setter method for property <tt>imageType</tt>.
     * 
     * @param imageType value to be assigned to property imageType
     */
    public void setImageType(EnumImageType imageType) {
        this.imageType = imageType;
    }

    /**
     * Getter method for property <tt>imageName</tt>.
     * 
     * @return property value of imageName
     */
    public String getImageName() {
        return imageName;
    }

    /**
     * Setter method for property <tt>imageName</tt>.
     * 
     * @param imageName value to be assigned to property imageName
     */
    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    /**
     * Getter method for property <tt>imageUrl</tt>.
     * 
     * @return property value of imageUrl
     */
    public String getImageUrl() {
        return imageUrl;
    }

    /**
     * Setter method for property <tt>imageUrl</tt>.
     * 
     * @param imageUrl value to be assigned to property imageUrl
     */
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    /**
     * Getter method for property <tt>resourceSource</tt>.
     * 
     * @return property value of resourceSource
     */
    public EnumResourceSource getResourceSource() {
        return resourceSource;
    }

    /**
     * Setter method for property <tt>resourceSource</tt>.
     * 
     * @param resourceSource value to be assigned to property resourceSource
     */
    public void setResourceSource(EnumResourceSource resourceSource) {
        this.resourceSource = resourceSource;
    }
}
