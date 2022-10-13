package com.jojiapp.techblogserverspring.support;

import org.springframework.boot.test.context.*;
import org.springframework.context.annotation.*;
import org.springframework.http.codec.*;
import org.springframework.http.codec.support.*;

@TestConfiguration
public class ServerCodecConfigurerConfig {

    @Bean
    public ServerCodecConfigurer serverCodecConfigurer() {

        return new DefaultServerCodecConfigurer();
    }
}
