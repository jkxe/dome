package com.hsjry.user.facade.pojo.enums.convertor;

import org.tinygroup.context2object.TypeConverter;

import com.hsjry.lang.common.base.enums.EnumServiceScence;

/**
 * @author hongsj
 */
public class EnumServiceScenceConverter implements TypeConverter<String, EnumServiceScence> {

    @Override
    public Class<String> getSourceType() {
        return String.class;
    }

    @Override
    public Class<EnumServiceScence> getDestinationType() {
        return EnumServiceScence.class;
    }

    @Override
    public EnumServiceScence getObject(String value) {
        return EnumServiceScence.find(value);
    }

}
