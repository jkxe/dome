package com.hsjry.user.facade.pojo.enums.convertor;

import org.tinygroup.context2object.TypeConverter;

import com.hsjry.user.facade.pojo.enums.EnumMerchanRoleTypeList;

/**
 * @author hongsj
 */
public class EnumMerchanRoleTypeListConverter implements TypeConverter<String, EnumMerchanRoleTypeList> {

    @Override
    public Class<String> getSourceType() {
        return String.class;
    }

    @Override
    public Class<EnumMerchanRoleTypeList> getDestinationType() {
        return EnumMerchanRoleTypeList.class;
    }

    @Override
    public EnumMerchanRoleTypeList getObject(String value) {
        return EnumMerchanRoleTypeList.find(value);
    }

}
