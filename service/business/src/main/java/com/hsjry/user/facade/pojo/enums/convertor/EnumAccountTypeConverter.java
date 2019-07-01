package com.hsjry.user.facade.pojo.enums.convertor;

import org.tinygroup.context2object.TypeConverter;

import com.hsjry.user.facade.pojo.enums.EnumAccountType;

/**
 * @author hongsj
 */
public class EnumAccountTypeConverter implements TypeConverter<String, EnumAccountType> {

    @Override
    public Class<String> getSourceType() {
        return String.class;
    }

    @Override
    public Class<EnumAccountType> getDestinationType() {
        return EnumAccountType.class;
    }

    @Override
    public EnumAccountType getObject(String value) {
        return EnumAccountType.find(value);
    }

}
