package com.hsjry.user.facade.pojo.enums.convertor;

import org.tinygroup.context2object.TypeConverter;

import com.hsjry.user.facade.pojo.enums.EnumIndustryLabel;

/**
 * @author hongsj
 */
public class EnumIndustryLabelConverter implements TypeConverter<String, EnumIndustryLabel> {

    @Override
    public Class<String> getSourceType() {
        return String.class;
    }

    @Override
    public Class<EnumIndustryLabel> getDestinationType() {
        return EnumIndustryLabel.class;
    }

    @Override
    public EnumIndustryLabel getObject(String value) {
        return EnumIndustryLabel.find(value);
    }

}
