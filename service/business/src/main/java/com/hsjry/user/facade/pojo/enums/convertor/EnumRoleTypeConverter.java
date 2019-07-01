package com.hsjry.user.facade.pojo.enums.convertor;

import org.tinygroup.context2object.TypeConverter;

import com.hsjry.user.facade.pojo.enums.EnumRoleType;

/**
 * @author hongsj
 */
public class EnumRoleTypeConverter implements TypeConverter<String, EnumRoleType> {

    @Override
    public Class<String> getSourceType() {
        return String.class;
    }

    @Override
    public Class<EnumRoleType> getDestinationType() {
        return EnumRoleType.class;
    }

    @Override
    public EnumRoleType getObject(String value) {
        return EnumRoleType.find(value);
    }

}
