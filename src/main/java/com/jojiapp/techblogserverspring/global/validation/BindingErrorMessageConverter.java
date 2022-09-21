package com.jojiapp.techblogserverspring.global.validation;

import lombok.*;
import org.springframework.context.*;
import org.springframework.context.i18n.*;
import org.springframework.stereotype.*;
import org.springframework.validation.*;

import java.util.*;

@Component
@RequiredArgsConstructor
public class BindingErrorMessageConverter {

    private final MessageSource messageSource;

    public String getMessage(final FieldError fieldError) {
        return Arrays.stream(Objects.requireNonNull(fieldError.getCodes()))
                .map(code -> createMessageOrNull(fieldError, code))
                .filter(Objects::nonNull)
                .findFirst()
                .orElse(fieldError.getDefaultMessage());
    }

    private String createMessageOrNull(final FieldError fieldError, final String code) {
        try {
            return messageSource.getMessage(
                    code,
                    fieldError.getArguments(),
                    LocaleContextHolder.getLocale()
            );
        } catch (NoSuchMessageException e) {
            return null;
        }
    }
}
