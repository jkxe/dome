package com.hsjry.user.facade.pojo.enums.convertor;

import org.tinygroup.context2object.TypeConverter;

import com.hsjry.user.facade.pojo.enums.EnumOrganType;

/**
 * @author hongsj
 */
public class EnumOrganTypeConverter implements TypeConverter<String, EnumOrganType> {

    @Override
    public Class<String> getSourceType() {
        return String.class;
    }

    @Override
    public Class<EnumOrganType> getDestinationType() {
        return EnumOrganType.class;
    }

    @Override
    public EnumOrganType getObject(String value) {
        return EnumOrganType.find(value);
    }

}
