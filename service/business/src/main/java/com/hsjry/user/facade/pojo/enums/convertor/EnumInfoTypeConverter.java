package com.hsjry.user.facade.pojo.enums.convertor;

import org.tinygroup.context2object.TypeConverter;

import com.hsjry.user.facade.pojo.enums.EnumInfoType;

/**
 * @author hongsj
 */
public class EnumInfoTypeConverter implements TypeConverter<String, EnumInfoType> {

    @Override
    public Class<String> getSourceType() {
        return String.class;
    }

    @Override
    public Class<EnumInfoType> getDestinationType() {
        return EnumInfoType.class;
    }

    @Override
    public EnumInfoType getObject(String value) {
        return EnumInfoType.find(value);
    }

}
