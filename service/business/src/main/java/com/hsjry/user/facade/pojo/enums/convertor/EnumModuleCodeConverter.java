package com.hsjry.user.facade.pojo.enums.convertor;

import org.tinygroup.context2object.TypeConverter;

import com.hsjry.lang.common.base.enums.EnumModuleCode;

/**
 * @author hongsj
 */
public class EnumModuleCodeConverter implements TypeConverter<String, EnumModuleCode> {

    @Override
    public Class<String> getSourceType() {
        return String.class;
    }

    @Override
    public Class<EnumModuleCode> getDestinationType() {
        return EnumModuleCode.class;
    }

    @Override
    public EnumModuleCode getObject(String value) {
        return EnumModuleCode.find(value);
    }

}
