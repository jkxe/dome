package com.hsjry.user.facade.pojo.enums.convertor;

import org.tinygroup.context2object.TypeConverter;

import com.hsjry.user.facade.pojo.enums.EnumNeedIndex;

/**
 * @author hongsj
 */
public class EnumNeedIndexConverter implements TypeConverter<String, EnumNeedIndex> {

    @Override
    public Class<String> getSourceType() {
        return String.class;
    }

    @Override
    public Class<EnumNeedIndex> getDestinationType() {
        return EnumNeedIndex.class;
    }

    @Override
    public EnumNeedIndex getObject(String value) {
        return EnumNeedIndex.find(value);
    }

}
