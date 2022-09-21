package com.jojiapp.techblogserverspring.global.exception;

import org.springframework.boot.autoconfigure.web.*;
import org.springframework.context.annotation.*;

@Configuration
public class ResourceWebPropertiesConfig {

    @Bean
    public WebProperties.Resources resources() {
        return new WebProperties.Resources();
    }

}
