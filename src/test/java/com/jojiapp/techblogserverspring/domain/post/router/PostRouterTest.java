package com.jojiapp.techblogserverspring.domain.post.router;

import com.jojiapp.techblogserverspring.domain.post.router.dto.*;
import com.jojiapp.techblogserverspring.support.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.web.reactive.function.server.*;
import reactor.core.publisher.*;

class PostRouterTest extends IntegrateTest {

    @Autowired
    private PostHandler postHandler;

    @Override
    protected RouterFunction<ServerResponse> getRouter() {
        return RouterFunctions.route(
                RequestPredicates.POST("/posts"),
                request -> postHandler.createPost(request.bodyToMono(PostCreate.class))
        );
    }

    @Test
    void 포스팅을_등록한다() throws Exception {

        // Given
        final PostCreate postCreate = new PostCreate("""
                # 제목

                내용
                """);

        // When & Then
        webTestClient.post()
                .uri("/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(postCreate), PostCreate.class)
                .exchange()
                .expectStatus().isOk();
    }

}
