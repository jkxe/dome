package com.hsjry.user.facade.pojo.request;

import java.io.Serializable;

import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotNull;

public class HeadImgUploadRequest implements Serializable {

    /**  */
    private static final long serialVersionUID = -8718312369777589819L;

    //头像（url）
    @NotNull(errorCode = "000001", message = "头像（url）")
    @NotBlank(errorCode = "000001", message = "头像（url）")
    private String            headImg;

    /**
     * Getter method for property <tt>headImg</tt>.
     * 
     * @return property value of headImg
     */
    public String getHeadImg() {
        return headImg;
    }

    /**
     * Setter method for property <tt>headImg</tt>.
     * 
     * @param headImg value to be assigned to property headImg
     */
    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

}
