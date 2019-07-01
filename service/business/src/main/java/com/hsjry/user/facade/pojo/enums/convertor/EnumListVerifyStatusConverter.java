package com.hsjry.user.facade.pojo.enums.convertor;

import org.tinygroup.context2object.TypeConverter;

import com.hsjry.user.facade.pojo.enums.EnumListVerifyStatus;

/**
 * @author hongsj
 */
public class EnumListVerifyStatusConverter implements TypeConverter<String, EnumListVerifyStatus> {

    @Override
    public Class<String> getSourceType() {
        return String.class;
    }

    @Override
    public Class<EnumListVerifyStatus> getDestinationType() {
        return EnumListVerifyStatus.class;
    }

    @Override
    public EnumListVerifyStatus getObject(String value) {
        return EnumListVerifyStatus.find(value);
    }

}
