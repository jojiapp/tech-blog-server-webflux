package com.jojiapp.techblogserverspring.global.validation;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.springframework.validation.*;

public class BindingErrorMessageConverterTest {

    private BindingErrorMessageConverter bindingErrorMessageConverter =
            new BindingErrorMessageConverter(MessageSourceCreator.messageSource());

    private MessageCodesResolver messageCodesResolver = new DefaultMessageCodesResolver();


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
        Assertions.assertThat(message).isEqualTo("빈값은 안됩니다.");
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
        Assertions.assertThat(message).isEqualTo("NumberDTO의 number는 1 이상만 허용합니다.");
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
        Assertions.assertThat(message).isEqualTo(defaultMessage);
    }

}
