package com.hsjry.user.facade.pojo.enums.convertor;

import org.tinygroup.context2object.TypeConverter;

import com.hsjry.user.facade.pojo.enums.EnumTelephoneClassCode;

/**
 * @author hongsj
 */
public class EnumTelephoneClassCodeConverter implements TypeConverter<String, EnumTelephoneClassCode> {

    @Override
    public Class<String> getSourceType() {
        return String.class;
    }

    @Override
    public Class<EnumTelephoneClassCode> getDestinationType() {
        return EnumTelephoneClassCode.class;
    }

    @Override
    public EnumTelephoneClassCode getObject(String value) {
        return EnumTelephoneClassCode.find(value);
    }

}
