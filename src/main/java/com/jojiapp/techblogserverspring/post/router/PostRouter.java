package com.jojiapp.techblogserverspring.post.router;

import com.jojiapp.techblogserverspring.global.router.*;
import com.jojiapp.techblogserverspring.global.validation.*;
import com.jojiapp.techblogserverspring.post.router.dto.*;
import org.springframework.context.annotation.*;
import org.springframework.web.reactive.function.server.*;

@Configuration
public class PostRouter extends AbstractRouter {

    protected PostRouter(final ValidationHandler validationHandler) {
        super(validationHandler);
    }

    @Bean
    protected RouterFunction<ServerResponse> routerExample(final PostHandler postHandler) {
        return RouterFunctions.route()
                .POST("/posts", request ->
                        postHandler.createPost(validationBody(request, PostCreate.class))
                )
                .build();
    }

}
