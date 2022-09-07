package com.jojiapp.techblogserverspring.post;

import org.springframework.stereotype.*;
import org.springframework.web.reactive.function.server.*;
import reactor.core.publisher.*;

@Component
public class PostHandler {

    public Mono<ServerResponse> getPosts(final ServerRequest request) {
        return ServerResponse.ok().bodyValue("hello");
    }
}
