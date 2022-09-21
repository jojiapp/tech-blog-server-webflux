package com.jojiapp.techblogserverspring.global.validation;

import lombok.*;
import org.springframework.stereotype.*;
import org.springframework.util.*;
import org.springframework.validation.*;

import javax.validation.*;
import java.util.*;

@Component
@RequiredArgsConstructor
public class FieldErrorCreator {

    private static final List<String> COMMON_ATTRIBUTES = List.of("groups", "message", "payload");
    private final MessageCodesResolver messageCodesResolver;

    public <T> FieldError create(final ConstraintViolation<T> violation) {
        return new FieldError(
                getObjectName(violation),
                getFieldName(violation),
                violation.getInvalidValue(),
                true,
                getCodes(violation),
                getValidationArgs(violation),
                violation.getMessage()
        );
    }

    private static <T> Object[] getValidationArgs(final ConstraintViolation<T> violation) {
        return violation.getConstraintDescriptor()
                .getAttributes()
                .entrySet()
                .stream()
                .filter(entry -> isValidationArgs(entry.getKey()))
                .map(Map.Entry::getValue)
                .toArray();
    }

    private static boolean isValidationArgs(final String key) {
        return !COMMON_ATTRIBUTES.contains(key);
    }

    private <T> String[] getCodes(final ConstraintViolation<T> violation) {
        return messageCodesResolver.resolveMessageCodes(
                getErrorCode(violation),
                getObjectName(violation),
                getFieldName(violation),
                getFieldType(violation)
        );
    }

    public static <T> String getObjectName(final ConstraintViolation<T> violation) {
        return StringUtils.uncapitalize(
                violation.getRootBeanClass().getSimpleName()
        );
    }

    private static <T> String getFieldName(final ConstraintViolation<T> violation) {
        return violation.getPropertyPath().toString();
    }

    private static <T> Class<?> getFieldType(final ConstraintViolation<T> violation) {
        return violation.getInvalidValue().getClass();
    }

    private static <T> String getErrorCode(final ConstraintViolation<T> violation) {
        return violation.getConstraintDescriptor()
                .getAnnotation()
                .annotationType()
                .getSimpleName();
    }

}
