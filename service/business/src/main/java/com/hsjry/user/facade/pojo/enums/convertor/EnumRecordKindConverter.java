package com.hsjry.user.facade.pojo.enums.convertor;

import org.tinygroup.context2object.TypeConverter;

import com.hsjry.user.facade.pojo.enums.EnumRecordKind;

/**
 * @author hongsj
 */
public class EnumRecordKindConverter implements TypeConverter<String, EnumRecordKind> {

    @Override
    public Class<String> getSourceType() {
        return String.class;
    }

    @Override
    public Class<EnumRecordKind> getDestinationType() {
        return EnumRecordKind.class;
    }

    @Override
    public EnumRecordKind getObject(String value) {
        return EnumRecordKind.find(value);
    }

}
