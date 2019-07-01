package com.hsjry.user.facade.pojo.enums.convertor;

import org.tinygroup.context2object.TypeConverter;

import com.hsjry.user.facade.pojo.enums.EnumIdentifyType;

/**
 * @author hongsj
 */
public class EnumIdentifyTypeConverter implements TypeConverter<String, EnumIdentifyType> {

    @Override
    public Class<String> getSourceType() {
        return String.class;
    }

    @Override
    public Class<EnumIdentifyType> getDestinationType() {
        return EnumIdentifyType.class;
    }

    @Override
    public EnumIdentifyType getObject(String value) {
        return EnumIdentifyType.find(value);
    }

}
