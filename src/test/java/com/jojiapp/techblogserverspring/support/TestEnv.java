package com.jojiapp.techblogserverspring.support;

import org.junit.jupiter.api.*;
import org.springframework.test.context.*;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@ActiveProfiles("test")
public @interface TestEnv {
}
