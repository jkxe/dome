package com.hsjry.user.facade.pojo.enums.convertor;

import org.tinygroup.context2object.TypeConverter;

import com.hsjry.user.facade.pojo.enums.EnumActiveStatus;

/**
 * @author hongsj
 */
public class EnumActiveStatusConverter implements TypeConverter<String, EnumActiveStatus> {

    @Override
    public Class<String> getSourceType() {
        return String.class;
    }

    @Override
    public Class<EnumActiveStatus> getDestinationType() {
        return EnumActiveStatus.class;
    }

    @Override
    public EnumActiveStatus getObject(String value) {
        return EnumActiveStatus.find(value);
    }

}
