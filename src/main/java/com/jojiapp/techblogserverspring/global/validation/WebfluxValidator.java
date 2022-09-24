package com.jojiapp.techblogserverspring.global.validation;

import lombok.*;
import org.springframework.stereotype.*;
import reactor.core.publisher.*;

import javax.validation.*;
import java.util.*;

@Component
@RequiredArgsConstructor
public class WebfluxValidator {

    private final Validator validator;
    private final BindingResultCreator bindingResultCreator;

    public <T> Mono<T> valid(final Mono<T> bodyMono) {

        return bodyMono.flatMap(body -> Mono.just(valid(body)));
    }

    public <T> T valid(final T object) {

        final Set<ConstraintViolation<T>> violations = validator.validate(object);

        if (!violations.isEmpty()) {
            throw new BindingException(bindingResultCreator.create(violations));
        }

        return object;
    }
}
