package com.jojiapp.techblogserverspring.domain.post.router;

import com.jojiapp.techblogserverspring.domain.post.router.dto.*;
import com.jojiapp.techblogserverspring.support.*;
import org.junit.jupiter.api.*;
import org.springframework.context.annotation.*;
import org.springframework.http.*;
import reactor.core.publisher.*;

@Import({PostRouter.class, PostHandler.class})
class PostRouterTest extends IntegrateTest {

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
