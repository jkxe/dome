package com.hsjry.user.facade.pojo.enums.convertor;

import org.tinygroup.context2object.TypeConverter;

import com.hsjry.lang.common.base.enums.user.EnumCertificateKind;

/**
 * @author hongsj
 */
public class EnumCertificateKindConverter implements TypeConverter<String, EnumCertificateKind> {

    @Override
    public Class<String> getSourceType() {
        return String.class;
    }

    @Override
    public Class<EnumCertificateKind> getDestinationType() {
        return EnumCertificateKind.class;
    }

    @Override
    public EnumCertificateKind getObject(String value) {
        return EnumCertificateKind.find(value);
    }

}
