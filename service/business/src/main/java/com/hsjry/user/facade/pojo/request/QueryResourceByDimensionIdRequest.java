package com.hsjry.user.facade.pojo.request;

import java.io.Serializable;
import java.util.List;

import com.hsjry.user.facade.pojo.enums.EnumRelationDimension;
import com.hsjry.user.facade.pojo.enums.EnumResourceType;

import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotNull;

public class QueryResourceByDimensionIdRequest implements Serializable {

    /**  */
    private static final long      serialVersionUID = -2500946659171052167L;
    //资源项类型
    @NotNull(errorCode = "000001", message = "资源项类型")
    private List<EnumResourceType> resourceTypeList;
    //关系维度
    @NotNull(errorCode = "000001", message = "关系维度")
    private EnumRelationDimension  dimension;
    //维度ID
    @NotNull(errorCode = "000001", message = "维度ID")
    @NotBlank(errorCode = "000001", message = "维度ID")
    private String                 dimensionId;

    /**
     * Getter method for property <tt>resourceTypeList</tt>.
     * 
     * @return property value of resourceTypeList
     */
    public List<EnumResourceType> getResourceTypeList() {
        return resourceTypeList;
    }

    /**
     * Setter method for property <tt>resourceTypeList</tt>.
     * 
     * @param resourceTypeList value to be assigned to property resourceTypeList
     */
    public void setResourceTypeList(List<EnumResourceType> resourceTypeList) {
        this.resourceTypeList = resourceTypeList;
    }

    /**
     * Getter method for property <tt>dimension</tt>.
     * 
     * @return property value of dimension
     */
    public EnumRelationDimension getDimension() {
        return dimension;
    }

    /**
     * Setter method for property <tt>dimension</tt>.
     * 
     * @param dimension value to be assigned to property dimension
     */
    public void setDimension(EnumRelationDimension dimension) {
        this.dimension = dimension;
    }

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
}
