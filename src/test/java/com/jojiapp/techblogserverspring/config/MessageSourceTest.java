package com.jojiapp.techblogserverspring.config;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.*;
import org.springframework.context.*;
import org.springframework.context.annotation.*;

import java.util.*;

@SpringBootTest
@Import(ServerCodecConfigurerConfig.class)
public class MessageSourceTest {

    @Autowired
    private MessageSource messageSource;

    @Test
    void messageProperties의_값을_정상적으로_읽어온다() {
        final String message = messageSource.getMessage(
                "NotBlank",
                null,
                "빈값은 안돼요",
                Locale.getDefault()
        );

        Assertions.assertThat(message).isEqualTo("공백 일 수 없습니다.");
    }

}
