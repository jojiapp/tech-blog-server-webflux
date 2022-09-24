package com.jojiapp.techblogserverspring.global.exception.handler;

import com.fasterxml.jackson.core.type.*;
import com.fasterxml.jackson.databind.*;
import com.jojiapp.techblogserverspring.global.response.*;
import com.jojiapp.techblogserverspring.global.validation.*;
import lombok.*;
import org.springframework.boot.web.error.*;
import org.springframework.boot.web.reactive.error.*;
import org.springframework.http.*;
import org.springframework.stereotype.*;
import org.springframework.validation.*;
import org.springframework.web.reactive.function.server.*;

import java.util.*;

import static java.util.stream.Collectors.*;
import static org.springframework.http.HttpStatus.*;

@Component
@RequiredArgsConstructor
public class GlobalErrorAttributes extends DefaultErrorAttributes {

    private static final String BINDING_ERROR_MESSAGE_SEP = ", ";
    private final ObjectMapper objectMapper;
    @Override
    public Map<String, Object> getErrorAttributes(final ServerRequest request,
                                                  final ErrorAttributeOptions options) {

        final Throwable error = getError(request);

        if(error instanceof IllegalStateException e) {
            return getResponse(BAD_REQUEST, e.getMessage());
        }

        if(error instanceof BindingException e) {
            return getResponse(BAD_REQUEST, getBindingErrorMessage(e.getFieldErrors()));
        }

        return getResponse(INTERNAL_SERVER_ERROR, error.getMessage());
    }

    private String getBindingErrorMessage(final List<FieldError> fieldErrors) {

        return fieldErrors
                .stream()
                .map(this::getBindingErrorMessage)
                .collect(joining(BINDING_ERROR_MESSAGE_SEP));
    }

    private String getBindingErrorMessage(final FieldError fieldError) {

        return "%s: %s".formatted(
                fieldError.getField(),
                fieldError.getDefaultMessage()
        );
    }

    private Map<String, Object> getResponse(final HttpStatus httpStatus,
                                            final String message) {

        final BaseResponse<Object> response = BaseResponse.builder()
                .status(String.valueOf(httpStatus.value()))
                .message(message)
                .build();

        return objectMapper.convertValue(response, new TypeReference<>() {});
    }
}
