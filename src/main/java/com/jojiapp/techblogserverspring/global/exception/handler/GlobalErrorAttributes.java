package com.jojiapp.techblogserverspring.global.exception.handler;

import com.fasterxml.jackson.core.type.*;
import com.fasterxml.jackson.databind.*;
import com.jojiapp.techblogserverspring.global.response.*;
import lombok.*;
import org.springframework.boot.web.error.*;
import org.springframework.boot.web.reactive.error.*;
import org.springframework.http.*;
import org.springframework.stereotype.*;
import org.springframework.validation.*;
import org.springframework.web.reactive.function.server.*;

import java.util.*;
import java.util.stream.*;

import static org.springframework.http.HttpStatus.*;

@Component
@RequiredArgsConstructor
public class GlobalErrorAttributes extends DefaultErrorAttributes {

    private final ObjectMapper objectMapper;
//    private final BindingErrorConvertor bindingErrorConvertor;

    @Override
    public Map<String, Object> getErrorAttributes(final ServerRequest request, final ErrorAttributeOptions options) {
        final Throwable error = getError(request);

        if(error instanceof IllegalStateException) {
            return getResponse(error.getMessage(), BAD_REQUEST);
        }
        if(error instanceof BindException) {
            final String message = ((BindException) error).getFieldErrors()
                    .stream()
                    .map(GlobalErrorAttributes::getBindingErrorMessage)
                    .collect(Collectors.joining(", "));
            return getResponse(message, BAD_REQUEST);
        }
        return getResponse(error.getMessage(), INTERNAL_SERVER_ERROR);
    }

    private static String getBindingErrorMessage(final FieldError fieldError) {
        return "%s: %s".formatted(
                fieldError.getField(),
                fieldError.getDefaultMessage()
        );
    }

    private Map<String, Object> getResponse(
            final String message,
            final HttpStatus httpStatus
    ) {
        final BaseResponse<Object> response = BaseResponse.builder()
                .status(String.valueOf(httpStatus.value()))
                .message(message)
                .build();
        return objectMapper.convertValue(response, new TypeReference<>() {});
    }

}
