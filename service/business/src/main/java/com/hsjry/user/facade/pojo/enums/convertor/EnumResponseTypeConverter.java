package com.hsjry.user.facade.pojo.enums.convertor;

import org.tinygroup.context2object.TypeConverter;

import com.hsjry.lang.common.base.enums.EnumResponseType;

/**
 * @author hongsj
 */
public class EnumResponseTypeConverter implements TypeConverter<String, EnumResponseType> {

    @Override
    public Class<String> getSourceType() {
        return String.class;
    }

    @Override
    public Class<EnumResponseType> getDestinationType() {
        return EnumResponseType.class;
    }

    @Override
    public EnumResponseType getObject(String value) {
        return EnumResponseType.find(value);
    }

}
