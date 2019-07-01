package com.hsjry.user.facade.pojo.enums.convertor;

import org.tinygroup.context2object.TypeConverter;

import com.hsjry.user.facade.pojo.enums.EnumDimension;

/**
 * @author hongsj
 */
public class EnumDimensionConverter implements TypeConverter<String, EnumDimension> {

    @Override
    public Class<String> getSourceType() {
        return String.class;
    }

    @Override
    public Class<EnumDimension> getDestinationType() {
        return EnumDimension.class;
    }

    @Override
    public EnumDimension getObject(String value) {
        return EnumDimension.find(value);
    }

}
