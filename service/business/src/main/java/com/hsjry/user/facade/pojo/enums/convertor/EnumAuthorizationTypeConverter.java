package com.hsjry.user.facade.pojo.enums.convertor;

import org.tinygroup.context2object.TypeConverter;

import com.hsjry.user.facade.pojo.enums.EnumAuthorizationType;

/**
 * @author hongsj
 */
public class EnumAuthorizationTypeConverter implements TypeConverter<String, EnumAuthorizationType> {

    @Override
    public Class<String> getSourceType() {
        return String.class;
    }

    @Override
    public Class<EnumAuthorizationType> getDestinationType() {
        return EnumAuthorizationType.class;
    }

    @Override
    public EnumAuthorizationType getObject(String value) {
        return EnumAuthorizationType.find(value);
    }

}
