package com.hsjry.user.facade.pojo.enums.convertor;

import org.tinygroup.context2object.TypeConverter;

import com.hsjry.lang.common.base.enums.EnumErrorType;

/**
 * @author hongsj
 */
public class EnumErrorTypeConverter implements TypeConverter<String, EnumErrorType> {

    @Override
    public Class<String> getSourceType() {
        return String.class;
    }

    @Override
    public Class<EnumErrorType> getDestinationType() {
        return EnumErrorType.class;
    }

    @Override
    public EnumErrorType getObject(String value) {
        return EnumErrorType.find(value);
    }

}
