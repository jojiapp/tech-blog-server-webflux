package com.jojiapp.techblogserverspring.global.config;

import org.springframework.boot.autoconfigure.web.*;
import org.springframework.context.annotation.*;

@Configuration
public class ResourceWebPropertiesConfig {

    @Bean
    public WebProperties.Resources resources() {
        return new WebProperties.Resources();
    }

}
