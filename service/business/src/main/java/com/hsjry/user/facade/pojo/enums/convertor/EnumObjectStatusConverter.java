package com.hsjry.user.facade.pojo.enums.convertor;

import org.tinygroup.context2object.TypeConverter;

import com.hsjry.user.facade.pojo.enums.EnumObjectStatus;

/**
 * @author hongsj
 */
public class EnumObjectStatusConverter implements TypeConverter<String, EnumObjectStatus> {

    @Override
    public Class<String> getSourceType() {
        return String.class;
    }

    @Override
    public Class<EnumObjectStatus> getDestinationType() {
        return EnumObjectStatus.class;
    }

    @Override
    public EnumObjectStatus getObject(String value) {
        return EnumObjectStatus.find(value);
    }

}
