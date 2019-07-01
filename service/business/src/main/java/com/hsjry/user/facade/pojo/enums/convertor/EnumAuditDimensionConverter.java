package com.hsjry.user.facade.pojo.enums.convertor;

import org.tinygroup.context2object.TypeConverter;

import com.hsjry.user.facade.pojo.enums.EnumAuditDimension;

/**
 * @author hongsj
 */
public class EnumAuditDimensionConverter implements TypeConverter<String, EnumAuditDimension> {

    @Override
    public Class<String> getSourceType() {
        return String.class;
    }

    @Override
    public Class<EnumAuditDimension> getDestinationType() {
        return EnumAuditDimension.class;
    }

    @Override
    public EnumAuditDimension getObject(String value) {
        return EnumAuditDimension.find(value);
    }

}
