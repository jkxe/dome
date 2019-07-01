package com.hsjry.user.facade.pojo.enums.convertor;

import org.tinygroup.context2object.TypeConverter;

import com.hsjry.lang.common.base.enums.EnumContext;

/**
 * @author hongsj
 */
public class EnumContextConverter implements TypeConverter<String, EnumContext> {

    @Override
    public Class<String> getSourceType() {
        return String.class;
    }

    @Override
    public Class<EnumContext> getDestinationType() {
        return EnumContext.class;
    }

    @Override
    public EnumContext getObject(String value) {
        return EnumContext.find(value);
    }

}
