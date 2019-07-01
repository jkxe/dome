package com.hsjry.user.facade.pojo.enums.convertor;

import org.tinygroup.context2object.TypeConverter;

import com.hsjry.user.facade.pojo.enums.EnumListType;

/**
 * @author hongsj
 */
public class EnumListTypeConverter implements TypeConverter<String, EnumListType> {

    @Override
    public Class<String> getSourceType() {
        return String.class;
    }

    @Override
    public Class<EnumListType> getDestinationType() {
        return EnumListType.class;
    }

    @Override
    public EnumListType getObject(String value) {
        return EnumListType.find(value);
    }

}
