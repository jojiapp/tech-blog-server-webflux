package com.jojiapp.techblogserverspring.domain.post.router;

import com.jojiapp.techblogserverspring.domain.post.router.dto.*;
import com.jojiapp.techblogserverspring.global.validation.*;
import lombok.*;
import org.springframework.context.annotation.*;
import org.springframework.web.reactive.function.server.*;

@Configuration
@RequiredArgsConstructor
public class PostRouter {

    private final WebfluxValidator webfluxValidator;

    @Bean
    protected RouterFunction<ServerResponse> routerExample(final PostHandler postHandler) {
        return RouterFunctions.route()
                .POST("/posts", request ->
                        postHandler.createPost(
                                webfluxValidator.body(request.bodyToMono(PostCreate.class))
                        )
                )
                .build();
    }

}
