package com.hsjry.user.facade.pojo.enums.convertor;

import org.tinygroup.context2object.TypeConverter;

import com.hsjry.lang.common.base.enums.user.EnumUserType;

/**
 * @author hongsj
 */
public class EnumUserTypeConverter implements TypeConverter<String, EnumUserType> {

    @Override
    public Class<String> getSourceType() {
        return String.class;
    }

    @Override
    public Class<EnumUserType> getDestinationType() {
        return EnumUserType.class;
    }

    @Override
    public EnumUserType getObject(String value) {
        return EnumUserType.find(value);
    }

}
