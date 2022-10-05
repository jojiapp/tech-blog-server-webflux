package com.jojiapp.techblogserverspring.global.validation.support;

import lombok.*;

import javax.validation.constraints.*;

@AllArgsConstructor
public class ValidationDTO {

    public static final String NAME = "   ";
    public static final int AGE = -1;

    @NotBlank
    private final String name;

    @Min(0)
    private final int age;
}
