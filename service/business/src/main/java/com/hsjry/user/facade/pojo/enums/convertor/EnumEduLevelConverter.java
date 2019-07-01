package com.hsjry.user.facade.pojo.enums.convertor;

import org.tinygroup.context2object.TypeConverter;

import com.hsjry.user.facade.pojo.enums.EnumEduLevel;

/**
 * @author hongsj
 */
public class EnumEduLevelConverter implements TypeConverter<String, EnumEduLevel> {

    @Override
    public Class<String> getSourceType() {
        return String.class;
    }

    @Override
    public Class<EnumEduLevel> getDestinationType() {
        return EnumEduLevel.class;
    }

    @Override
    public EnumEduLevel getObject(String value) {
        return EnumEduLevel.find(value);
    }

}
