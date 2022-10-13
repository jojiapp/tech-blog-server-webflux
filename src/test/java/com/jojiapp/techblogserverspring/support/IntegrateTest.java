package com.jojiapp.techblogserverspring.support;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;
import org.mockito.junit.jupiter.*;
import org.springframework.boot.test.context.*;
import org.springframework.context.annotation.*;
import org.springframework.test.web.reactive.server.*;
import org.springframework.web.reactive.function.server.*;

//@ContextConfiguration(classes = {
//        WebfluxValidator.class,
//        BindingResultCreator.class,
//        FieldErrorCreator.class,
//        BindingErrorMessageConverter.class,
//        MultiValueMapToObjectConverterResolver.class,
//        MultiValueMapToPageableConverter.class,
//        MultiValueMapToPageableConverter.class,
//        MessageCodesResolverConfig.class,
//        GlobalExceptionHandler.class,
//        ResourceWebPropertiesConfig.class,
//        GlobalErrorAttributes.class
//})
//@WebFluxTest
@SpringBootTest
@ExtendWith(MockitoExtension.class)
@Import(ServerCodecConfigurerConfig.class)
@TestEnv
public abstract class IntegrateTest {

    protected WebTestClient webTestClient;

    protected abstract RouterFunction<ServerResponse> getRouter();

    @BeforeEach
    void setWebTestClient(){
        webTestClient = WebTestClient.bindToRouterFunction(getRouter()).build();
    }

}
