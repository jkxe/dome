package com.hsjry.user.facade.pojo.enums.convertor;

import org.tinygroup.context2object.TypeConverter;

import com.hsjry.user.facade.pojo.enums.EnumCreditType;

/**
 * @author hongsj
 */
public class EnumCreditTypeConverter implements TypeConverter<String, EnumCreditType> {

    @Override
    public Class<String> getSourceType() {
        return String.class;
    }

    @Override
    public Class<EnumCreditType> getDestinationType() {
        return EnumCreditType.class;
    }

    @Override
    public EnumCreditType getObject(String value) {
        return EnumCreditType.find(value);
    }

}
