package com.hsjry.user.facade.pojo.enums.convertor;

import org.tinygroup.context2object.TypeConverter;

import com.hsjry.user.facade.pojo.enums.EnumVerifyStatus;

/**
 * @author hongsj
 */
public class EnumVerifyStatusConverter implements TypeConverter<String, EnumVerifyStatus> {

    @Override
    public Class<String> getSourceType() {
        return String.class;
    }

    @Override
    public Class<EnumVerifyStatus> getDestinationType() {
        return EnumVerifyStatus.class;
    }

    @Override
    public EnumVerifyStatus getObject(String value) {
        return EnumVerifyStatus.find(value);
    }

}
