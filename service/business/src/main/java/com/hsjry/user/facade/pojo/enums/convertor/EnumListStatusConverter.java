package com.hsjry.user.facade.pojo.enums.convertor;

import org.tinygroup.context2object.TypeConverter;

import com.hsjry.user.facade.pojo.enums.EnumListStatus;

/**
 * @author hongsj
 */
public class EnumListStatusConverter implements TypeConverter<String, EnumListStatus> {

    @Override
    public Class<String> getSourceType() {
        return String.class;
    }

    @Override
    public Class<EnumListStatus> getDestinationType() {
        return EnumListStatus.class;
    }

    @Override
    public EnumListStatus getObject(String value) {
        return EnumListStatus.find(value);
    }

}
