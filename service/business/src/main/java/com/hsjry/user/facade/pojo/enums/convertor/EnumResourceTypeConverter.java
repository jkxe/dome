package com.hsjry.user.facade.pojo.enums.convertor;

import org.tinygroup.context2object.TypeConverter;

import com.hsjry.user.facade.pojo.enums.EnumResourceType;

/**
 * @author hongsj
 */
public class EnumResourceTypeConverter implements TypeConverter<String, EnumResourceType> {

    @Override
    public Class<String> getSourceType() {
        return String.class;
    }

    @Override
    public Class<EnumResourceType> getDestinationType() {
        return EnumResourceType.class;
    }

    @Override
    public EnumResourceType getObject(String value) {
        return EnumResourceType.find(value);
    }

}
