package com.jojiapp.techblogserverspring.support;

import org.junit.jupiter.api.extension.*;
import org.mockito.junit.jupiter.*;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@ExtendWith(MockitoExtension.class)
@TestEnv
public @interface UnitTest {
}
