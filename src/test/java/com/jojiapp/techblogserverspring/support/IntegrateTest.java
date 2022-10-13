package com.jojiapp.techblogserverspring.support;

import com.jojiapp.techblogserverspring.global.converter.multivaluemaptoobject.*;
import com.jojiapp.techblogserverspring.global.converter.multivaluemaptoobject.resolver.*;
import com.jojiapp.techblogserverspring.global.exception.*;
import com.jojiapp.techblogserverspring.global.exception.handler.*;
import com.jojiapp.techblogserverspring.global.validation.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.autoconfigure.web.reactive.*;
import org.springframework.test.context.*;
import org.springframework.test.web.reactive.server.*;

@ContextConfiguration(classes = {
        WebfluxValidator.class,
        BindingResultCreator.class,
        FieldErrorCreator.class,
        BindingErrorMessageConverter.class,
        MultiValueMapToObjectConverterResolver.class,
        MultiValueMapToPageableConverter.class,
        MultiValueMapToPageableConverter.class,
        MessageCodesResolverConfig.class,
        GlobalExceptionHandler.class,
        ResourceWebPropertiesConfig.class,
        GlobalErrorAttributes.class
})
@WebFluxTest
@TestEnv
public abstract class IntegrateTest {

    @Autowired
    protected WebTestClient webTestClient;

}
