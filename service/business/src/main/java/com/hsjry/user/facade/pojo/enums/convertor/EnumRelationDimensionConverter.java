package com.hsjry.user.facade.pojo.enums.convertor;

import org.tinygroup.context2object.TypeConverter;

import com.hsjry.user.facade.pojo.enums.EnumRelationDimension;

/**
 * @author hongsj
 */
public class EnumRelationDimensionConverter implements TypeConverter<String, EnumRelationDimension> {

    @Override
    public Class<String> getSourceType() {
        return String.class;
    }

    @Override
    public Class<EnumRelationDimension> getDestinationType() {
        return EnumRelationDimension.class;
    }

    @Override
    public EnumRelationDimension getObject(String value) {
        return EnumRelationDimension.find(value);
    }

}
