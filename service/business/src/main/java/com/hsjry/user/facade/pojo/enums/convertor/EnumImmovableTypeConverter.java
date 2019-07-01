package com.hsjry.user.facade.pojo.enums.convertor;

import org.tinygroup.context2object.TypeConverter;

import com.hsjry.user.facade.pojo.enums.EnumImmovableType;

/**
 * @author hongsj
 */
public class EnumImmovableTypeConverter implements TypeConverter<String, EnumImmovableType> {

    @Override
    public Class<String> getSourceType() {
        return String.class;
    }

    @Override
    public Class<EnumImmovableType> getDestinationType() {
        return EnumImmovableType.class;
    }

    @Override
    public EnumImmovableType getObject(String value) {
        return EnumImmovableType.find(value);
    }

}
