package com.jojiapp.techblogserverspring.global.validation;

import lombok.*;
import org.springframework.stereotype.*;
import org.springframework.validation.*;

import javax.validation.*;
import java.util.*;

@Component
@RequiredArgsConstructor
public class BindingResultCreator {

    private static final String EMPTY_ERROR_MESSAGE = "유효성에 실패한 요소가 없습니다.";
    private final FieldErrorCreator fieldErrorCreator;

    public <T> BindingResult create(final Set<ConstraintViolation<T>> violations) {

        if (violations.isEmpty()) {
            throw new IllegalArgumentException(EMPTY_ERROR_MESSAGE);
        }

        final BindingResult bindingResult = getBindingResult(violations);
        addFieldErrors(bindingResult, violations);

        return bindingResult;
    }

    private <T> void addFieldErrors(final BindingResult bindingResult,
                                    final Set<ConstraintViolation<T>> violations) {

        violations.forEach(violation ->
                bindingResult.addError(fieldErrorCreator.create(violation))
        );
    }

    private static <T> BindingResult getBindingResult(final Set<ConstraintViolation<T>> violations) {

        return violations.stream()
                .map(BindingResultCreator::createDefaultBindingResult)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(EMPTY_ERROR_MESSAGE));
    }

    private static <T> BeanPropertyBindingResult createDefaultBindingResult(final ConstraintViolation<T> violation) {

        return new BeanPropertyBindingResult(
                violation.getRootBean(),
                FieldErrorCreator.getObjectName(violation)
        );
    }
}
