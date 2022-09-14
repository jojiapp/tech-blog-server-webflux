package com.jojiapp.techblogserverspring.post.router;

import com.jojiapp.techblogserverspring.global.validation.*;
import com.jojiapp.techblogserverspring.post.router.dto.*;
import lombok.*;
import org.springframework.context.annotation.*;
import org.springframework.web.reactive.function.server.*;

@Configuration
@RequiredArgsConstructor
public class PostRouter {

    private final RequestValidator requestValidator;

    @Bean
    protected RouterFunction<ServerResponse> routerExample(final PostHandler postHandler) {
        return RouterFunctions.route()
                .POST("/posts", request ->
                        postHandler.createPost(
                                requestValidator.body(request.bodyToMono(PostCreate.class))
                        )
                )
                .build();
    }

}
