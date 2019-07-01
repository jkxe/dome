package com.hsjry.user.facade.pojo.enums.convertor;

import org.tinygroup.context2object.TypeConverter;

import com.hsjry.user.facade.pojo.enums.EnumEmailClassCode;

/**
 * @author hongsj
 */
public class EnumEmailClassCodeConverter implements TypeConverter<String, EnumEmailClassCode> {

    @Override
    public Class<String> getSourceType() {
        return String.class;
    }

    @Override
    public Class<EnumEmailClassCode> getDestinationType() {
        return EnumEmailClassCode.class;
    }

    @Override
    public EnumEmailClassCode getObject(String value) {
        return EnumEmailClassCode.find(value);
    }

}
