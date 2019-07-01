package com.hsjry.user.facade.pojo.enums.convertor;

import org.tinygroup.context2object.TypeConverter;

import com.hsjry.user.facade.pojo.enums.EnumMarketingCues;

/**
 * @author hongsj
 */
public class EnumMarketingCuesConverter implements TypeConverter<String, EnumMarketingCues> {

    @Override
    public Class<String> getSourceType() {
        return String.class;
    }

    @Override
    public Class<EnumMarketingCues> getDestinationType() {
        return EnumMarketingCues.class;
    }

    @Override
    public EnumMarketingCues getObject(String value) {
        return EnumMarketingCues.find(value);
    }

}
