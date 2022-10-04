package com.jojiapp.techblogserverspring.support;

import com.jojiapp.techblogserverspring.config.*;
import org.springframework.boot.test.context.*;
import org.springframework.context.annotation.*;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@SpringBootTest
@Import(ServerCodecConfigurerConfig.class)
@TestEnv
public @interface IntergrateTest {
}
