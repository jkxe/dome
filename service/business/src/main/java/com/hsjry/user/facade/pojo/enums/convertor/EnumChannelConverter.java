package com.hsjry.user.facade.pojo.enums.convertor;

import org.tinygroup.context2object.TypeConverter;

import com.hsjry.lang.common.base.enums.EnumChannel;

/**
 * @author hongsj
 */
public class EnumChannelConverter implements TypeConverter<String, EnumChannel> {

    @Override
    public Class<String> getSourceType() {
        return String.class;
    }

    @Override
    public Class<EnumChannel> getDestinationType() {
        return EnumChannel.class;
    }

    @Override
    public EnumChannel getObject(String value) {
        return EnumChannel.find(value);
    }

}
