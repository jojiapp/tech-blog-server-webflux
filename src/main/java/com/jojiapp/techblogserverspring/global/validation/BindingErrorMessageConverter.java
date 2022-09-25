package com.jojiapp.techblogserverspring.global.validation;

import lombok.*;
import org.springframework.context.*;
import org.springframework.context.i18n.*;
import org.springframework.stereotype.*;

import java.util.*;

@Component
@RequiredArgsConstructor
public class BindingErrorMessageConverter {

    private final MessageSource messageSource;

    public String getMessage(final String[] codes,
                             final Object[] args,
                             final String defaultMessage) {

        return Arrays.stream(codes)
                .map(code -> createMessageOrNull(code, args))
                .filter(Objects::nonNull)
                .findFirst()
                .orElse(defaultMessage);
    }

    private String createMessageOrNull(final String code, final Object[] args) {

        try {
            return messageSource.getMessage(
                    code,
                    args,
                    LocaleContextHolder.getLocale()
            );
        } catch (NoSuchMessageException e) {
            return null;
        }
    }
}
