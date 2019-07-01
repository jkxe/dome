package com.hsjry.user.facade.pojo.enums.convertor;

import org.tinygroup.context2object.TypeConverter;

import com.hsjry.user.facade.pojo.enums.EnumIndustryType;

/**
 * @author hongsj
 */
public class EnumIndustryTypeConverter implements TypeConverter<String, EnumIndustryType> {

    @Override
    public Class<String> getSourceType() {
        return String.class;
    }

    @Override
    public Class<EnumIndustryType> getDestinationType() {
        return EnumIndustryType.class;
    }

    @Override
    public EnumIndustryType getObject(String value) {
        return EnumIndustryType.find(value);
    }

}
