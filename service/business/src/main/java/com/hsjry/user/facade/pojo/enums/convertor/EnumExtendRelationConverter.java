package com.hsjry.user.facade.pojo.enums.convertor;

import org.tinygroup.context2object.TypeConverter;

import com.hsjry.user.facade.pojo.enums.EnumExtendRelation;

/**
 * @author hongsj
 */
public class EnumExtendRelationConverter implements TypeConverter<String, EnumExtendRelation> {

    @Override
    public Class<String> getSourceType() {
        return String.class;
    }

    @Override
    public Class<EnumExtendRelation> getDestinationType() {
        return EnumExtendRelation.class;
    }

    @Override
    public EnumExtendRelation getObject(String value) {
        return EnumExtendRelation.find(value);
    }

}
