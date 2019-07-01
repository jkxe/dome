package com.hsjry.user.facade.pojo.enums.convertor;

import org.tinygroup.context2object.TypeConverter;

import com.hsjry.user.facade.pojo.enums.EnumRelationType;

/**
 * @author hongsj
 */
public class EnumRelationTypeConverter implements TypeConverter<String, EnumRelationType> {

    @Override
    public Class<String> getSourceType() {
        return String.class;
    }

    @Override
    public Class<EnumRelationType> getDestinationType() {
        return EnumRelationType.class;
    }

    @Override
    public EnumRelationType getObject(String value) {
        return EnumRelationType.find(value);
    }

}
