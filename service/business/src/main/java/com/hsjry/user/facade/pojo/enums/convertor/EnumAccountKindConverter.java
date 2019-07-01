package com.hsjry.user.facade.pojo.enums.convertor;

import org.tinygroup.context2object.TypeConverter;

import com.hsjry.lang.common.base.enums.user.EnumAccountKind;

/**
 * @author hongsj
 */
public class EnumAccountKindConverter implements TypeConverter<String, EnumAccountKind> {

    @Override
    public Class<String> getSourceType() {
        return String.class;
    }

    @Override
    public Class<EnumAccountKind> getDestinationType() {
        return EnumAccountKind.class;
    }

    @Override
    public EnumAccountKind getObject(String value) {
        return EnumAccountKind.find(value);
    }

}
