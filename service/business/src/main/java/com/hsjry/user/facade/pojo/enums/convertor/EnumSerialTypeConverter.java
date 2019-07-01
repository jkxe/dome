package com.hsjry.user.facade.pojo.enums.convertor;

import org.tinygroup.context2object.TypeConverter;

import com.hsjry.lang.common.base.enums.EnumSerialType;

/**
 * @author hongsj
 */
public class EnumSerialTypeConverter implements TypeConverter<String, EnumSerialType> {

    @Override
    public Class<String> getSourceType() {
        return String.class;
    }

    @Override
    public Class<EnumSerialType> getDestinationType() {
        return EnumSerialType.class;
    }

    @Override
    public EnumSerialType getObject(String value) {
        return EnumSerialType.find(value);
    }

}
