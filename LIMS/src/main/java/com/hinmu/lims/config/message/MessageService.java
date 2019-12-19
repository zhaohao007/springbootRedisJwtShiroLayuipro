package com.hinmu.lims.config.message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.stereotype.Service;

import java.util.Locale;

/**
 * Created by hao on 2019/4/28.
 */
@Service
public class MessageService {
    @Qualifier(value="myMessageSource")
    @Autowired
    private MessageSource messageSource;
    public String getMessage(String key) {
        return this.getMessage(key, Locale.getDefault());
    }

    public String getMessage(String key, Locale locale) {
        return this.messageSource.getMessage(key, (Object[])null, locale);
    }

    public String getMessage(String key, String... args) {
        return this.messageSource.getMessage(key, args, Locale.getDefault());
    }

    public String getMessage(String key, Locale locale, String... args) {
        return this.messageSource.getMessage(key, args, locale);
    }

    public String getMessageWithDefault(String key, String defaultValue) {
        try {
            return this.getMessage(key);
        } catch (NoSuchMessageException var4) {
            return defaultValue;
        }
    }
}
