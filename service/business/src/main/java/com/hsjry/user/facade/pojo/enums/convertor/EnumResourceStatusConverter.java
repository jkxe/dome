package com.hsjry.user.facade.pojo.enums.convertor;

import org.tinygroup.context2object.TypeConverter;

import com.hsjry.user.facade.pojo.enums.EnumResourceStatus;

/**
 * @author hongsj
 */
public class EnumResourceStatusConverter implements TypeConverter<String, EnumResourceStatus> {

    @Override
    public Class<String> getSourceType() {
        return String.class;
    }

    @Override
    public Class<EnumResourceStatus> getDestinationType() {
        return EnumResourceStatus.class;
    }

    @Override
    public EnumResourceStatus getObject(String value) {
        return EnumResourceStatus.find(value);
    }

}
