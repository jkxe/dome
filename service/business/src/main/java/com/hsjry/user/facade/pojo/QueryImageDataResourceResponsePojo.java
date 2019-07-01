/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * @author jiangjd12837
 * @version $Id: QueryResourceResponsePojo.java, v 1.0 2017年4月8日 下午3:23:31 jiangjd12837 Exp $
 */
public class QueryImageDataResourceResponsePojo implements Serializable {

    /**  */
    private static final long           serialVersionUID = -7065265957688156921L;
    //影像资料
    private List<UserImageDiffDataPojo> imageDiffDataPojos;

    /**
     * Getter method for property <tt>imageDiffDataPojos</tt>.
     * 
     * @return property value of imageDiffDataPojos
     */
    public List<UserImageDiffDataPojo> getImageDiffDataPojos() {
        return imageDiffDataPojos;
    }

    /**
     * Setter method for property <tt>imageDiffDataPojos</tt>.
     * 
     * @param imageDiffDataPojos value to be assigned to property imageDiffDataPojos
     */
    public void setImageDiffDataPojos(List<UserImageDiffDataPojo> imageDiffDataPojos) {
        this.imageDiffDataPojos = imageDiffDataPojos;
    }

}
