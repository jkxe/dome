package com.hsjry.user.facade.pojo.enums.convertor;

import org.tinygroup.context2object.TypeConverter;

import com.hsjry.user.facade.pojo.enums.EnumMerchantType;

/**
 * @author hongsj
 */
public class EnumMerchantTypeConverter implements TypeConverter<String, EnumMerchantType> {

    @Override
    public Class<String> getSourceType() {
        return String.class;
    }

    @Override
    public Class<EnumMerchantType> getDestinationType() {
        return EnumMerchantType.class;
    }

    @Override
    public EnumMerchantType getObject(String value) {
        return EnumMerchantType.find(value);
    }

}
