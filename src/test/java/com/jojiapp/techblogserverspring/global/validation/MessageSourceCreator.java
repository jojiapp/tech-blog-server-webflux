package com.jojiapp.techblogserverspring.global.validation;


import org.springframework.context.*;
import org.springframework.context.support.*;

public class MessageSourceCreator {

    public static MessageSource messageSource() {
        final ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasenames("classpath:/messages-test");
        messageSource.setDefaultEncoding("UTF-8");
        messageSource.setFallbackToSystemLocale(false);
        return messageSource;
    }
}
