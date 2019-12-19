package com.hinmu.lims.config.message;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;


/**
 * Created by hao on 2019/4/28.
 */
@Configuration
public class MessageSourceConfig {
    @Value("${spring.messages.basename}")
    private String basename;
    @Value("${spring.messages.cache-second}")
    private long cacheMillis;
    @Value("${spring.messages.encoding}")
    private String encoding;
    /**
     * 初始化
     * @return
     */
    @Primary
    @Bean("myMessageSource")
    public MessageSource initMessageSource() {
        ReloadableResourceBundleMessageSource messageSource =
                new ReloadableResourceBundleMessageSource();
        messageSource.setBasename(basename);
        messageSource.setDefaultEncoding(encoding);
        messageSource.setCacheMillis(cacheMillis);
        return messageSource;
    }
}
