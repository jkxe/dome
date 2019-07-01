package com.hsjry.user.facade.pojo.enums.convertor;

import org.tinygroup.context2object.TypeConverter;

import com.hsjry.user.facade.pojo.enums.EnumMenuType;

/**
 * @author hongsj
 */
public class EnumMenuTypeConverter implements TypeConverter<String, EnumMenuType> {

    @Override
    public Class<String> getSourceType() {
        return String.class;
    }

    @Override
    public Class<EnumMenuType> getDestinationType() {
        return EnumMenuType.class;
    }

    @Override
    public EnumMenuType getObject(String value) {
        return EnumMenuType.find(value);
    }

}
