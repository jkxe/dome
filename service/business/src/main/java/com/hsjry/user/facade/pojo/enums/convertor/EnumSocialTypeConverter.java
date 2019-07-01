package com.hsjry.user.facade.pojo.enums.convertor;

import org.tinygroup.context2object.TypeConverter;

import com.hsjry.user.facade.pojo.enums.EnumSocialType;

/**
 * @author hongsj
 */
public class EnumSocialTypeConverter implements TypeConverter<String, EnumSocialType> {

    @Override
    public Class<String> getSourceType() {
        return String.class;
    }

    @Override
    public Class<EnumSocialType> getDestinationType() {
        return EnumSocialType.class;
    }

    @Override
    public EnumSocialType getObject(String value) {
        return EnumSocialType.find(value);
    }

}
