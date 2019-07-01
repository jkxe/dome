package com.hsjry.user.facade.pojo.enums.convertor;

import org.tinygroup.context2object.TypeConverter;

import com.hsjry.user.facade.pojo.enums.EnumResidenceType;

/**
 * @author huangbb
 */
public class EnumResidenceTypeConverter implements TypeConverter<String, EnumResidenceType> {

    @Override
    public Class<String> getSourceType() {
        return String.class;
    }

    @Override
    public Class<EnumResidenceType> getDestinationType() {
        return EnumResidenceType.class;
    }

    @Override
    public EnumResidenceType getObject(String value) {
        return EnumResidenceType.find(value);
    }

}
