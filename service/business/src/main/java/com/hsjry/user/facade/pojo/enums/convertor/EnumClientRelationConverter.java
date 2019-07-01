package com.hsjry.user.facade.pojo.enums.convertor;

import org.tinygroup.context2object.TypeConverter;

import com.hsjry.user.facade.pojo.enums.EnumClientRelation;

/**
 * @author hongsj
 */
public class EnumClientRelationConverter implements TypeConverter<String, EnumClientRelation> {

    @Override
    public Class<String> getSourceType() {
        return String.class;
    }

    @Override
    public Class<EnumClientRelation> getDestinationType() {
        return EnumClientRelation.class;
    }

    @Override
    public EnumClientRelation getObject(String value) {
        return EnumClientRelation.find(value);
    }

}
