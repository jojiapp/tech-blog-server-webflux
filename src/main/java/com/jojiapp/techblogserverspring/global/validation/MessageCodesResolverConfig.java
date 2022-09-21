package com.jojiapp.techblogserverspring.global.validation;

import org.springframework.context.annotation.*;
import org.springframework.validation.*;

@Configuration
public class MessageCodesResolverConfig {

    @Bean
    public MessageCodesResolver messageCodesResolver() {
        return new DefaultMessageCodesResolver();
    }
}
