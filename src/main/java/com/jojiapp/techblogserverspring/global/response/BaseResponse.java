package com.jojiapp.techblogserverspring.global.response;

import lombok.*;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class BaseResponse<T> {

    private final String status;
    private final String message;
    private final T data;

}
