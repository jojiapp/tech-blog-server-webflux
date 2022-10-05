package com.jojiapp.techblogserverspring.global.validation;

import com.jojiapp.techblogserverspring.global.validation.support.*;
import com.jojiapp.techblogserverspring.support.*;
import org.junit.jupiter.api.*;
import org.springframework.validation.*;

import static org.assertj.core.api.Assertions.*;

@TestEnv
public class BindingErrorMessageConverterTest {

    private final MessageCodesResolver messageCodesResolver = new DefaultMessageCodesResolver();
    private final BindingErrorMessageConverter bindingErrorMessageConverter =
            new BindingErrorMessageConverter(MessageSourceCreator.messageSource());


    @Test
    void codes와_args를_넣어_해당하는_message를_가져온다() {

        // Given
        final String[] codes = messageCodesResolver.resolveMessageCodes(
                "NotBlank",
                "stringDTO",
                "str",
                String.class
        );

        // When
        final String message = bindingErrorMessageConverter.getMessage(
                codes,
                null,
                "공백일 수 없음"
        );

        // Then
        assertThat(message).isEqualTo("빈값은 안됩니다.");
    }

    @Test
    void 우선순위_높은_code의_메세지를_가져온다() throws Exception {

        // Given
        final String[] codes = messageCodesResolver.resolveMessageCodes(
                "Min",
                "numberDTO",
                "number",
                Integer.class
        );

        // When
        final String message = bindingErrorMessageConverter.getMessage(
                codes,
                new Object[]{1},
                "1보다 커야함"
        );

        // Then
        assertThat(message).isEqualTo("NumberDTO의 number는 1 이상만 허용합니다.");
    }

    @Test
    void 일치하는_code가_존재하지_않으면_defaultMessage를_가져온다() throws Exception {

        // Given
        final String[] codes = messageCodesResolver.resolveMessageCodes(
                "AAA",
                "numberDTO",
                "number",
                Integer.class
        );
        final String defaultMessage = "1보다 커야함";

        // When
        final String message = bindingErrorMessageConverter.getMessage(
                codes,
                new Object[]{1},
                defaultMessage
        );

        // Then
        assertThat(message).isEqualTo(defaultMessage);
    }

}
