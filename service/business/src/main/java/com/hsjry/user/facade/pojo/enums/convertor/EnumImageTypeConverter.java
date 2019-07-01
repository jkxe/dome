package com.hsjry.user.facade.pojo.enums.convertor;

import org.tinygroup.context2object.TypeConverter;

import com.hsjry.user.facade.pojo.enums.EnumImageType;

/**
 * @author hongsj
 */
public class EnumImageTypeConverter implements TypeConverter<String, EnumImageType> {

    @Override
    public Class<String> getSourceType() {
        return String.class;
    }

    @Override
    public Class<EnumImageType> getDestinationType() {
        return EnumImageType.class;
    }

    @Override
    public EnumImageType getObject(String value) {
        return EnumImageType.find(value);
    }

}
