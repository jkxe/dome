package com.hsjry.user.facade.pojo.enums.convertor;

import org.tinygroup.context2object.TypeConverter;

import com.hsjry.user.facade.pojo.enums.EnumIdKind;

/**
 * @author hongsj
 */
public class EnumIdKindConverter implements TypeConverter<String, EnumIdKind> {

    @Override
    public Class<String> getSourceType() {
        return String.class;
    }

    @Override
    public Class<EnumIdKind> getDestinationType() {
        return EnumIdKind.class;
    }

    @Override
    public EnumIdKind getObject(String value) {
        return EnumIdKind.find(value);
    }

}
