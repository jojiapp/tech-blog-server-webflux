package com.jojiapp.techblogserverspring.post.controller.dto;

import lombok.*;

import javax.validation.constraints.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class PostCreate {

    @NotBlank
    private String markdown;
}
