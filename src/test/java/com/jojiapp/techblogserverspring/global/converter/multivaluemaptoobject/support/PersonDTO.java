package com.jojiapp.techblogserverspring.global.converter.multivaluemaptoobject.support;

import lombok.*;

import java.util.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PersonDTO {

    private String name;
    private List<Integer> age;
}
