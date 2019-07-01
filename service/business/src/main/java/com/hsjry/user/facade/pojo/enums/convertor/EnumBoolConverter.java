package com.hsjry.user.facade.pojo.enums.convertor;

import org.tinygroup.context2object.TypeConverter;

import com.hsjry.lang.common.base.enums.EnumBool;

/**
 * @author hongsj
 */
public class EnumBoolConverter implements TypeConverter<String, EnumBool> {

    @Override
    public Class<String> getSourceType() {
        return String.class;
    }

    @Override
    public Class<EnumBool> getDestinationType() {
        return EnumBool.class;
    }

    @Override
    public EnumBool getObject(String value) {
        return EnumBool.find(value);
    }

}
