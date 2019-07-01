package com.hsjry.user.facade.pojo.enums.convertor;

import org.tinygroup.context2object.TypeConverter;

import com.hsjry.user.facade.pojo.enums.EnumSurvivalStatus;

/**
 * @author hongsj
 */
public class EnumSurvivalStatusConverter implements TypeConverter<String, EnumSurvivalStatus> {

    @Override
    public Class<String> getSourceType() {
        return String.class;
    }

    @Override
    public Class<EnumSurvivalStatus> getDestinationType() {
        return EnumSurvivalStatus.class;
    }

    @Override
    public EnumSurvivalStatus getObject(String value) {
        return EnumSurvivalStatus.find(value);
    }

}
