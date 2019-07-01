package com.hsjry.user.facade.pojo.enums.convertor;

import org.tinygroup.context2object.TypeConverter;

import com.hsjry.user.facade.pojo.enums.EnumRootId;

/**
 * @author hongsj
 */
public class EnumRootIdConverter implements TypeConverter<String, EnumRootId> {

    @Override
    public Class<String> getSourceType() {
        return String.class;
    }

    @Override
    public Class<EnumRootId> getDestinationType() {
        return EnumRootId.class;
    }

    @Override
    public EnumRootId getObject(String value) {
        return EnumRootId.find(value);
    }

}
