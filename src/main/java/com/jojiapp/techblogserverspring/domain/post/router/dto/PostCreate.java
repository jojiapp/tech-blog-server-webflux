package com.jojiapp.techblogserverspring.domain.post.router.dto;

import lombok.*;

import javax.validation.constraints.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class PostCreate {

    @NotBlank
    private String markdown;

}
