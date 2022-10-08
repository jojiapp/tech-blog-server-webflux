package com.jojiapp.techblogserverspring.global.validation;

import com.jojiapp.techblogserverspring.global.validation.support.*;
import com.jojiapp.techblogserverspring.support.*;
import org.junit.jupiter.api.*;
import org.springframework.validation.*;

import javax.validation.Validator;
import javax.validation.*;
import java.util.*;

import static com.jojiapp.techblogserverspring.global.validation.support.ValidationDTO.*;
import static org.assertj.core.api.Assertions.*;

@TestEnv
class FieldErrorCreatorTest {

    private final BindingErrorMessageConverter bindingErrorMessageConverter =
            new BindingErrorMessageConverter(MessageSourceCreator.messageSource());

    private final MessageCodesResolver messageCodesResolver = new DefaultMessageCodesResolver();

    private final FieldErrorCreator fieldErrorCreator = new FieldErrorCreator(
            messageCodesResolver,
            bindingErrorMessageConverter
    );

    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();


    @Test
    void 정상적으로_fieldError를_생성한다() throws Exception {

        // Given
        final ValidationDTO validationDTO = new ValidationDTO(NAME, AGE);

        final Set<ConstraintViolation<ValidationDTO>> violations = validator.validate(validationDTO);

        // When
        final List<FieldError> fieldErrors = violations.stream()
                .map(fieldErrorCreator::create)
                .sorted(Comparator.comparing(FieldError::getField))
                .toList();

        // Then
        final FieldError ageError = fieldErrors.get(0);
        assertThat(ageError.getObjectName()).isEqualTo("validationDTO");
        assertThat(ageError.getField()).isEqualTo("age");
        assertThat(ageError.getRejectedValue()).isEqualTo(AGE);
        assertThat(ageError.getDefaultMessage()).isEqualTo("0 이상만 허용 됩니다.");

        final FieldError nameError = fieldErrors.get(1);
        assertThat(nameError.getObjectName()).isEqualTo("validationDTO");
        assertThat(nameError.getField()).isEqualTo("name");
        assertThat(nameError.getRejectedValue()).isEqualTo(NAME);
        assertThat(nameError.getDefaultMessage()).isEqualTo("빈값은 안됩니다.");
    }

    @Test
    void objectName을_추출한다() throws Exception {

        // Given
        final ValidationDTO validationDTO = new ValidationDTO(NAME, 100);

        final List<ConstraintViolation<ValidationDTO>> constraintViolations = validator.validate(validationDTO)
                .stream()
                .toList();

        // When
        final String objectName = FieldErrorCreator.getObjectName(constraintViolations.get(0));

        // Then
        assertThat(objectName).isEqualTo("validationDTO");
    }

}
