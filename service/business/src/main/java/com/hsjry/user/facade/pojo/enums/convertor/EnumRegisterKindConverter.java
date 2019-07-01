package com.hsjry.user.facade.pojo.enums.convertor;

import org.tinygroup.context2object.TypeConverter;

import com.hsjry.user.facade.pojo.enums.EnumRegisterKind;

/**
 * @author hongsj
 */
public class EnumRegisterKindConverter implements TypeConverter<String, EnumRegisterKind> {

    @Override
    public Class<String> getSourceType() {
        return String.class;
    }

    @Override
    public Class<EnumRegisterKind> getDestinationType() {
        return EnumRegisterKind.class;
    }

    @Override
    public EnumRegisterKind getObject(String value) {
        return EnumRegisterKind.find(value);
    }

}
