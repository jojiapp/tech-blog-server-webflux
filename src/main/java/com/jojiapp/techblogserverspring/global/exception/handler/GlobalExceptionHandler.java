package com.jojiapp.techblogserverspring.global.exception.handler;

import lombok.extern.slf4j.*;
import org.springframework.boot.autoconfigure.web.*;
import org.springframework.boot.autoconfigure.web.reactive.error.*;
import org.springframework.boot.web.error.*;
import org.springframework.boot.web.reactive.error.*;
import org.springframework.context.*;
import org.springframework.http.*;
import org.springframework.http.codec.*;
import org.springframework.stereotype.*;
import org.springframework.web.reactive.function.*;
import org.springframework.web.reactive.function.server.*;
import reactor.core.publisher.*;

import java.util.*;

@Slf4j
@Component
public class GlobalExceptionHandler extends AbstractErrorWebExceptionHandler {

    public GlobalExceptionHandler(final ErrorAttributes errorAttributes,
                                  final WebProperties.Resources resources,
                                  final ApplicationContext applicationContext,
                                  final ServerCodecConfigurer serverCodecConfigurer) {

        super(errorAttributes, resources, applicationContext);
        super.setMessageReaders(serverCodecConfigurer.getReaders());
        super.setMessageWriters(serverCodecConfigurer.getWriters());
    }

    @Override
    protected RouterFunction<ServerResponse> getRoutingFunction(final ErrorAttributes errorAttributes) {

        return RouterFunctions.route(RequestPredicates.all(), this::renderErrorResponse);
    }

    private Mono<ServerResponse> renderErrorResponse(final ServerRequest request) {

        final Map<String, Object> errorProperties = getErrorAttributes(request, ErrorAttributeOptions.defaults());

        return ServerResponse.status(getStatus(errorProperties))
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(errorProperties));
    }

    private static int getStatus(final Map<String, Object> errorProperties) {

        return Integer.parseInt(errorProperties.get("status").toString());
    }
}
