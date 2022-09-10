package com.jojiapp.techblogserverspring.global.exception;

import com.fasterxml.jackson.databind.*;
import com.jojiapp.techblogserverspring.global.response.*;
import lombok.*;
import org.springframework.boot.web.error.*;
import org.springframework.boot.web.reactive.error.*;
import org.springframework.http.*;
import org.springframework.stereotype.*;
import org.springframework.web.reactive.function.server.*;

import java.util.*;

import static org.springframework.http.HttpStatus.*;

@Component
@RequiredArgsConstructor
public class GlobalErrorAttributes extends DefaultErrorAttributes {

    private final ObjectMapper objectMapper;

    @Override
    public Map<String, Object> getErrorAttributes(final ServerRequest request, final ErrorAttributeOptions options) {
        final Throwable error = getError(request);
        final BaseResponse.BaseResponseBuilder<Object> responseBuilder = BaseResponse.builder()
                .message(error.getMessage());

        return objectMapper.convertValue(getResponse(responseBuilder, INTERNAL_SERVER_ERROR), Map.class);
    }

    private static BaseResponse<Object> getResponse(
            final BaseResponse.BaseResponseBuilder<Object> responseBuilder,
            final HttpStatus httpStatus
    ) {
        return responseBuilder
                .status(String.valueOf(httpStatus.value()))
                .build();
    }
}
