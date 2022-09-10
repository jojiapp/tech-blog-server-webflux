package com.jojiapp.techblogserverspring.global.validation;

import lombok.*;
import org.springframework.stereotype.*;
import reactor.core.publisher.*;

import javax.validation.*;

@Component
@RequiredArgsConstructor
public class ValidationHandler {

    private final Validator validator;

    public <BODY> Mono<BODY> requireValidBody(final Mono<BODY> bodyMono) {
        return bodyMono.flatMap(
                body -> {
                    if(!validator.validate(body).isEmpty()) {
                        return Mono.error(new IllegalStateException("바디 유효성 실패"));
                    }
                    return Mono.just(body);
                }
        );
    }

}
