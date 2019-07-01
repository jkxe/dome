package com.hsjry.user.facade.pojo.enums.convertor;

import org.tinygroup.context2object.TypeConverter;

import com.hsjry.user.facade.pojo.enums.EnumResourceSource;

/**
 * @author hongsj
 */
public class EnumResourceSourceConverter implements TypeConverter<String, EnumResourceSource> {

    @Override
    public Class<String> getSourceType() {
        return String.class;
    }

    @Override
    public Class<EnumResourceSource> getDestinationType() {
        return EnumResourceSource.class;
    }

    @Override
    public EnumResourceSource getObject(String value) {
        return EnumResourceSource.find(value);
    }

}
