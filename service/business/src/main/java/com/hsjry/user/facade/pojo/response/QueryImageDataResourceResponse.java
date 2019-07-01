package com.hsjry.user.facade.pojo.response;

import java.io.Serializable;
import java.util.List;

import com.hsjry.user.facade.pojo.UserImageDiffDataPojo;

public class QueryImageDataResourceResponse implements Serializable {

    /**  */
    private static final long           serialVersionUID = 2394223841825155339L;
    //影像资料
    private List<UserImageDiffDataPojo> userImageDiffDataPojos;

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
