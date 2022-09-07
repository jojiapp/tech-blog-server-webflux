package com.jojiapp.techblogserverspring.post;

import org.springframework.context.annotation.*;
import org.springframework.web.reactive.function.server.*;

@Configuration
public class PostRouterConfig {

    @Bean
    protected RouterFunction<ServerResponse> routerExample(PostHandler postHandler) {
        return RouterFunctions.route()
                .GET("/posts", postHandler::getPosts)
                .build();
    }
}
