package com.hsjry.user.facade.pojo.enums.convertor;

import org.tinygroup.context2object.TypeConverter;

import com.hsjry.user.facade.pojo.enums.EnumMarriageStatus;

/**
 * @author hongsj
 */
public class EnumMarriageStatusConverter implements TypeConverter<String, EnumMarriageStatus> {

    @Override
    public Class<String> getSourceType() {
        return String.class;
    }

    @Override
    public Class<EnumMarriageStatus> getDestinationType() {
        return EnumMarriageStatus.class;
    }

    @Override
    public EnumMarriageStatus getObject(String value) {
        return EnumMarriageStatus.find(value);
    }

}
