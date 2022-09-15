package com.jojiapp.techblogserverspring.global.validation;

import lombok.*;
import org.springframework.stereotype.*;
import org.springframework.util.*;
import org.springframework.validation.*;
import reactor.core.publisher.*;

import javax.validation.*;
import javax.validation.Validator;
import java.util.*;

@Component
@RequiredArgsConstructor
public class RequestValidator {

    private final Validator validator;

    public <BODY> Mono<BODY> body(final Mono<BODY> bodyMono) {
        return bodyMono.flatMap(
                body -> {
                    Set<ConstraintViolation<BODY>> validate = validator.validate(body);
                    if (validate.isEmpty()) {
                        return Mono.just(body);
                    }

                    final String objectName = getObjectName(body);
                    final BindingResult bindingResult = new BeanPropertyBindingResult(body, objectName);

                    validate.forEach(c -> {
                        Object[] validationArgs = c.getConstraintDescriptor()
                                .getAttributes()
                                .entrySet()
                                .stream()
                                .filter(entry -> {
                                            String key = entry.getKey();
                                            return !key.equals("groups") &&
                                                    !key.equals("message") &&
                                                    !key.equals("payload");
                                        }

                                )
                                .map(Map.Entry::getValue)
                                .toArray();
                        bindingResult.addError(
                                new FieldError(objectName, c.getPropertyPath().toString(), c.getMessage())
                        );
                    });

                    return Mono.error(new BindException(bindingResult));
                }
        );
    }

    private <BODY> String getObjectName(BODY body) {
        final String objectFullName = body.getClass().getName();
        return StringUtils.uncapitalize(
                objectFullName.substring(objectFullName.lastIndexOf(".") + 1)
        );
    }

}
