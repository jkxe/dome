package com.hsjry.user.facade.pojo.enums.convertor;

import org.tinygroup.context2object.TypeConverter;

import com.hsjry.lang.common.base.enums.user.EnumSmsType;

/**
 * @author hongsj
 */
public class EnumSmsTypeConverter implements TypeConverter<String, EnumSmsType> {

    @Override
    public Class<String> getSourceType() {
        return String.class;
    }

    @Override
    public Class<EnumSmsType> getDestinationType() {
        return EnumSmsType.class;
    }

    @Override
    public EnumSmsType getObject(String value) {
        return EnumSmsType.find(value);
    }

}
