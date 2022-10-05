package com.jojiapp.techblogserverspring.global.validation;

import com.jojiapp.techblogserverspring.global.validation.support.*;
import com.jojiapp.techblogserverspring.support.*;
import org.junit.jupiter.api.*;
import org.springframework.validation.*;

import javax.validation.Validator;
import javax.validation.*;

import static com.jojiapp.techblogserverspring.global.validation.support.ValidationDTO.*;
import static org.assertj.core.api.Assertions.*;

@TestEnv
class BindingExceptionTest {

    private final BindingErrorMessageConverter bindingErrorMessageConverter =
            new BindingErrorMessageConverter(MessageSourceCreator.messageSource());

    private final MessageCodesResolver messageCodesResolver = new DefaultMessageCodesResolver();

    private final FieldErrorCreator fieldErrorCreator = new FieldErrorCreator(
            messageCodesResolver,
            bindingErrorMessageConverter
    );

    private final BindingResultCreator bindingResultCreator = new BindingResultCreator(fieldErrorCreator);

    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
    
    @Test
    void fieldErrors를_정상적으로_조회한다() throws Exception {
        
        // Given
        final ValidationDTO validationDTO = new ValidationDTO(NAME, AGE);
        final BindingResult bindingResult = bindingResultCreator.create(validator.validate(validationDTO));

        // When
        final BindingException bindingException = new BindingException(bindingResult);

        // Then
        assertThat(bindingException.getFieldErrors().size()).isEqualTo(2);
    }

}
