package com.hsjry.user.facade.pojo.enums.convertor;

import org.tinygroup.context2object.TypeConverter;

import com.hsjry.user.facade.pojo.enums.EnumManagerType;

/**
 * @author hongsj
 */
public class EnumManagerTypeConverter implements TypeConverter<String, EnumManagerType> {

    @Override
    public Class<String> getSourceType() {
        return String.class;
    }

    @Override
    public Class<EnumManagerType> getDestinationType() {
        return EnumManagerType.class;
    }

    @Override
    public EnumManagerType getObject(String value) {
        return EnumManagerType.find(value);
    }

}
