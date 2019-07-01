package com.hsjry.user.facade.pojo.enums.convertor;

import org.tinygroup.context2object.TypeConverter;

import com.hsjry.user.facade.pojo.enums.EnumContactStationTypeCode;

/**
 * @author hongsj
 */
public class EnumContactStationTypeCodeConverter implements TypeConverter<String, EnumContactStationTypeCode> {

    @Override
    public Class<String> getSourceType() {
        return String.class;
    }

    @Override
    public Class<EnumContactStationTypeCode> getDestinationType() {
        return EnumContactStationTypeCode.class;
    }

    @Override
    public EnumContactStationTypeCode getObject(String value) {
        return EnumContactStationTypeCode.find(value);
    }

}
