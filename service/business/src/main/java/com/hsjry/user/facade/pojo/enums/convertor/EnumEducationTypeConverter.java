package com.hsjry.user.facade.pojo.enums.convertor;

import org.tinygroup.context2object.TypeConverter;

import com.hsjry.user.facade.pojo.enums.EnumEducationType;

/**
 * @author hongsj
 */
public class EnumEducationTypeConverter implements TypeConverter<String, EnumEducationType> {

    @Override
    public Class<String> getSourceType() {
        return String.class;
    }

    @Override
    public Class<EnumEducationType> getDestinationType() {
        return EnumEducationType.class;
    }

    @Override
    public EnumEducationType getObject(String value) {
        return EnumEducationType.find(value);
    }

}
