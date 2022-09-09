package com.jojiapp.techblogserverspring.global.handler;

import lombok.*;
import org.springframework.stereotype.*;
import org.springframework.web.reactive.function.server.*;
import reactor.core.publisher.*;

import javax.validation.*;
import java.util.function.*;

@Component
@RequiredArgsConstructor
public class RequestValidationHandler {

    private final Validator validator;

    public <BODY> Mono<ServerResponse> requireValidBody(
            final Function<Mono<BODY>, Mono<ServerResponse>> block,
            final Mono<BODY> bodyMono
    ) {

        return bodyMono.flatMap(
                body -> validator.validate(body).isEmpty() ?
                        block.apply(Mono.just(body))
                        : ServerResponse.badRequest().build()
        );
    }

}
