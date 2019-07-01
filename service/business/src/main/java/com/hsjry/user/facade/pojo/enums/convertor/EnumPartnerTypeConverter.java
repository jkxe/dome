package com.hsjry.user.facade.pojo.enums.convertor;

import org.tinygroup.context2object.TypeConverter;

import com.hsjry.user.facade.pojo.enums.EnumPartnerType;

/**
 * @author hongsj
 */
public class EnumPartnerTypeConverter implements TypeConverter<String, EnumPartnerType> {

    @Override
    public Class<String> getSourceType() {
        return String.class;
    }

    @Override
    public Class<EnumPartnerType> getDestinationType() {
        return EnumPartnerType.class;
    }

    @Override
    public EnumPartnerType getObject(String value) {
        return EnumPartnerType.find(value);
    }

}
