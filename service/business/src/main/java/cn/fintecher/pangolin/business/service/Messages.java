package cn.fintecher.pangolin.business.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import java.util.Locale;

/**
 * i18n 多语言支持
 * Created by ChenChang on 2017/12/18.
 */
@Component
public class Messages {
    @Autowired
    private MessageSource messageSource;

    public String get(String code) {
        Locale locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage(code, null, locale);
    }
}
