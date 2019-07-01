package com.hsjry.user.facade.pojo.enums.convertor;

import org.tinygroup.context2object.TypeConverter;

import com.hsjry.user.facade.pojo.enums.EnumBindLoginType;

/**
 * @author hongsj
 */
public class EnumBindLoginTypeConverter implements TypeConverter<String, EnumBindLoginType> {

    @Override
    public Class<String> getSourceType() {
        return String.class;
    }

    @Override
    public Class<EnumBindLoginType> getDestinationType() {
        return EnumBindLoginType.class;
    }

    @Override
    public EnumBindLoginType getObject(String value) {
        return EnumBindLoginType.find(value);
    }

}
