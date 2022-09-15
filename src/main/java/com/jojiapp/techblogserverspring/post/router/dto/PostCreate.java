package com.jojiapp.techblogserverspring.post.router.dto;

import lombok.*;

import javax.validation.constraints.*;
import java.util.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class PostCreate {

    @NotBlank
    private String markdown;

    @Size(min = 100, max = 1000)
    private List<Integer> number;
}
