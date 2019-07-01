package com.hsjry.user.facade.pojo.enums.convertor;

import org.tinygroup.context2object.TypeConverter;

import com.hsjry.lang.common.base.enums.user.EnumImageKind;

/**
 * @author hongsj
 */
public class EnumImageKindConverter implements TypeConverter<String, EnumImageKind> {

    @Override
    public Class<String> getSourceType() {
        return String.class;
    }

    @Override
    public Class<EnumImageKind> getDestinationType() {
        return EnumImageKind.class;
    }

    @Override
    public EnumImageKind getObject(String value) {
        return EnumImageKind.find(value);
    }

}
