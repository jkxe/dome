package com.hsjry.user.facade.pojo.enums.convertor;

import org.tinygroup.context2object.TypeConverter;

import com.hsjry.user.facade.pojo.enums.EnumGender;

/**
 * @author hongsj
 */
public class EnumGenderConverter implements TypeConverter<String, EnumGender> {

    @Override
    public Class<String> getSourceType() {
        return String.class;
    }

    @Override
    public Class<EnumGender> getDestinationType() {
        return EnumGender.class;
    }

    @Override
    public EnumGender getObject(String value) {
        return EnumGender.find(value);
    }

}
