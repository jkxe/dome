package com.hsjry.user.facade.pojo.enums.convertor;

import org.tinygroup.context2object.TypeConverter;

import com.hsjry.user.facade.pojo.enums.EnumLiveCondition;

/**
 * @author hongsj
 */
public class EnumLiveConditionConverter implements TypeConverter<String, EnumLiveCondition> {

    @Override
    public Class<String> getSourceType() {
        return String.class;
    }

    @Override
    public Class<EnumLiveCondition> getDestinationType() {
        return EnumLiveCondition.class;
    }

    @Override
    public EnumLiveCondition getObject(String value) {
        return EnumLiveCondition.find(value);
    }

}
