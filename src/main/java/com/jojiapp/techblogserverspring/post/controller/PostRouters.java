package com.jojiapp.techblogserverspring.post.controller;

import com.jojiapp.techblogserverspring.global.handler.*;
import com.jojiapp.techblogserverspring.post.controller.dto.*;
import lombok.*;
import org.springframework.context.annotation.*;
import org.springframework.web.reactive.function.server.*;

@Configuration
@RequiredArgsConstructor
public class PostRouters {

    private final RequestValidationHandler requestValidationHandler;

    @Bean
    protected RouterFunction<ServerResponse> routerExample(final PostHandler postHandler) {
        return RouterFunctions.route()
                .POST("/posts", request -> requestValidationHandler.requireValidBody(
                        postHandler::createPost,
                        request.bodyToMono(PostCreate.class)
                ))
                .build();
    }
}
