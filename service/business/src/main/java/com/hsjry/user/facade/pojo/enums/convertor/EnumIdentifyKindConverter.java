package com.hsjry.user.facade.pojo.enums.convertor;

import org.tinygroup.context2object.TypeConverter;

import com.hsjry.user.facade.pojo.enums.EnumIdentifyKind;

/**
 * @author hongsj
 */
public class EnumIdentifyKindConverter implements TypeConverter<String, EnumIdentifyKind> {

    @Override
    public Class<String> getSourceType() {
        return String.class;
    }

    @Override
    public Class<EnumIdentifyKind> getDestinationType() {
        return EnumIdentifyKind.class;
    }

    @Override
    public EnumIdentifyKind getObject(String value) {
        return EnumIdentifyKind.find(value);
    }

}
