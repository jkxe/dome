package com.hsjry.user.facade.pojo.enums.convertor;

import org.tinygroup.context2object.TypeConverter;

import com.hsjry.lang.common.base.enums.EnumCommonErrMsg;

/**
 * @author hongsj
 */
public class EnumCommonErrMsgConverter implements TypeConverter<String, EnumCommonErrMsg> {

    @Override
    public Class<String> getSourceType() {
        return String.class;
    }

    @Override
    public Class<EnumCommonErrMsg> getDestinationType() {
        return EnumCommonErrMsg.class;
    }

    @Override
    public EnumCommonErrMsg getObject(String value) {
        return EnumCommonErrMsg.find(value);
    }

}
