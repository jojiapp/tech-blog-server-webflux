package com.jojiapp.techblogserverspring.global.router;

import com.jojiapp.techblogserverspring.global.validation.*;
import lombok.*;
import org.springframework.web.reactive.function.server.*;
import reactor.core.publisher.*;

@AllArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class AbstractRouter {

    private final ValidationHandler validationHandler;

    protected <T> Mono<T> validationBody(ServerRequest request, Class<T> bodyType) {
        return validationHandler.requireValidBody(
                request.bodyToMono(bodyType)
        );
    }
}
