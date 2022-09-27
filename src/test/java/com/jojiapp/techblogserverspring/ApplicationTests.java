package com.jojiapp.techblogserverspring;

import com.jojiapp.techblogserverspring.config.*;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.*;
import org.springframework.context.annotation.*;

@SpringBootTest
@Import(ServerCodecConfigurerConfig.class)
class ApplicationTests {

    @Test
    void contextLoads() {
    }

}
