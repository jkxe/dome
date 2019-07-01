package com.hsjry.user.facade.pojo.enums.convertor;

import org.tinygroup.context2object.TypeConverter;

import com.hsjry.user.facade.pojo.enums.EnumChannelType;

/**
 * @author hongsj
 */
public class EnumChannelTypeConverter implements TypeConverter<String, EnumChannelType> {

    @Override
    public Class<String> getSourceType() {
        return String.class;
    }

    @Override
    public Class<EnumChannelType> getDestinationType() {
        return EnumChannelType.class;
    }

    @Override
    public EnumChannelType getObject(String value) {
        return EnumChannelType.find(value);
    }

}
