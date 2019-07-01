package com.hsjry.user.facade.pojo.enums.convertor;

import org.tinygroup.context2object.TypeConverter;

import com.hsjry.user.facade.pojo.enums.EnumRecycleType;

/**
 * @author hongsj
 */
public class EnumRecycleTypeConverter implements TypeConverter<String, EnumRecycleType> {

    @Override
    public Class<String> getSourceType() {
        return String.class;
    }

    @Override
    public Class<EnumRecycleType> getDestinationType() {
        return EnumRecycleType.class;
    }

    @Override
    public EnumRecycleType getObject(String value) {
        return EnumRecycleType.find(value);
    }

}
