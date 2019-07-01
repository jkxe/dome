package com.hsjry.user.facade.pojo.enums.convertor;

import org.tinygroup.context2object.TypeConverter;

import com.hsjry.user.facade.pojo.enums.EnumAddressClassCode;

/**
 * @author hongsj
 */
public class EnumAddressClassCodeConverter implements TypeConverter<String, EnumAddressClassCode> {

    @Override
    public Class<String> getSourceType() {
        return String.class;
    }

    @Override
    public Class<EnumAddressClassCode> getDestinationType() {
        return EnumAddressClassCode.class;
    }

    @Override
    public EnumAddressClassCode getObject(String value) {
        return EnumAddressClassCode.find(value);
    }

}
