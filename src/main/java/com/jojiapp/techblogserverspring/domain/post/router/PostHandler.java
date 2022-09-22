package com.jojiapp.techblogserverspring.domain.post.router;

import com.jojiapp.techblogserverspring.domain.post.router.dto.*;
import lombok.*;
import org.springframework.stereotype.*;
import org.springframework.web.reactive.function.server.*;
import reactor.core.publisher.*;

import java.util.*;

@Component
@RequiredArgsConstructor
public class PostHandler {
    public Mono<ServerResponse> createPost(final Mono<PostCreate> postCreateMono) {

        final Mono<Map<String, String>> markdown = postCreateMono.map(postCreate -> Map.of("markdown", postCreate.getMarkdown()));

        return ServerResponse.ok().body(markdown, Map.class);
    }
}
