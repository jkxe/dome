package com.hsjry.user.facade.pojo.enums.convertor;

import org.tinygroup.context2object.TypeConverter;

import com.hsjry.user.facade.pojo.enums.EnumPurposeCode;

/**
 * @author hongsj
 */
public class EnumPurposeCodeConverter implements TypeConverter<String, EnumPurposeCode> {

    @Override
    public Class<String> getSourceType() {
        return String.class;
    }

    @Override
    public Class<EnumPurposeCode> getDestinationType() {
        return EnumPurposeCode.class;
    }

    @Override
    public EnumPurposeCode getObject(String value) {
        return EnumPurposeCode.find(value);
    }

}
