package com.jojiapp.techblogserverspring.global.validation;

import lombok.*;
import org.springframework.validation.*;

import java.util.*;

@AllArgsConstructor
public class BindingException extends RuntimeException{

    private final BindingResult bindingResult;

    public List<FieldError> getFieldErrors() {

        return bindingResult.getFieldErrors();
    }
}
