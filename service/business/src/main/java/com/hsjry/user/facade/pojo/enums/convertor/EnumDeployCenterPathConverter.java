package com.hsjry.user.facade.pojo.enums.convertor;

import org.tinygroup.context2object.TypeConverter;

import com.hsjry.lang.common.base.enums.EnumDeployCenterPath;

/**
 * @author hongsj
 */
public class EnumDeployCenterPathConverter implements TypeConverter<String, EnumDeployCenterPath> {

    @Override
    public Class<String> getSourceType() {
        return String.class;
    }

    @Override
    public Class<EnumDeployCenterPath> getDestinationType() {
        return EnumDeployCenterPath.class;
    }

    @Override
    public EnumDeployCenterPath getObject(String value) {
        return EnumDeployCenterPath.find(value);
    }

}
