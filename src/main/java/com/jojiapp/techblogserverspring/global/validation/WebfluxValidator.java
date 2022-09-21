package com.jojiapp.techblogserverspring.global.validation;

import lombok.*;
import org.springframework.stereotype.*;
import org.springframework.validation.*;
import reactor.core.publisher.*;

import javax.validation.Validator;
import javax.validation.*;
import java.util.*;

@Component
@RequiredArgsConstructor
public class WebfluxValidator {

    private final Validator validator;
    private final BindingResultCreator bindingResultCreator;

    public <BODY> Mono<BODY> body(final Mono<BODY> bodyMono) {
        return bodyMono.flatMap(
                body -> {
                    final Set<ConstraintViolation<BODY>> validate = validator.validate(body);
                    if (validate.isEmpty()) {
                        return Mono.just(body);
                    }
                    return Mono.error(
                            new BindException(bindingResultCreator.create(validate))
                    );
                }
        );
    }
}
