package com.hsjry.user.facade.pojo.enums.convertor;

import org.tinygroup.context2object.TypeConverter;

import com.hsjry.lang.common.base.enums.user.EnumSystemRoleType;

/**
 * @author hongsj
 */
public class EnumSystemRoleTypeConverter implements TypeConverter<String, EnumSystemRoleType> {

    @Override
    public Class<String> getSourceType() {
        return String.class;
    }

    @Override
    public Class<EnumSystemRoleType> getDestinationType() {
        return EnumSystemRoleType.class;
    }

    @Override
    public EnumSystemRoleType getObject(String value) {
        return EnumSystemRoleType.find(value);
    }

}
