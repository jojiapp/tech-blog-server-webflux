package com.jojiapp.techblogserverspring.global.converter.multivaluemaptoobject;

import com.fasterxml.jackson.databind.*;
import com.jojiapp.techblogserverspring.global.converter.multivaluemaptoobject.resolver.*;
import com.jojiapp.techblogserverspring.global.converter.multivaluemaptoobject.support.*;
import com.jojiapp.techblogserverspring.support.*;
import org.junit.jupiter.api.*;
import org.springframework.util.*;

import static org.assertj.core.api.Assertions.*;

@TestEnv
class AbstractMultiValueMapToObjectConverterTest {

    private final MultiValueMapToObjectConverterResolver multiValueMapToObjectConverterResolver =
            new MultiValueMapToObjectConverterResolver();

    @Test
    void AbstractMultiValueMapToObjectConverter를_상속받은_객체를_생성하면_MultiValueMapToObjectConverterResolver에_등록된다() {

        // Given
        final ObjectMapper objectMapper = new ObjectMapper();
        new MultiValueMapToObjectConverter(multiValueMapToObjectConverterResolver, objectMapper);

        final MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
        multiValueMap.add("name", "이름");
        multiValueMap.add("age", "1");
        multiValueMap.add("age", "2");

        // When
        final PersonDTO personDTO = multiValueMapToObjectConverterResolver.convert(multiValueMap, PersonDTO.class);

        // Then
        assertThat(personDTO.getName()).isEqualTo("이름");
        assertThat(personDTO.getAge()).contains(1, 2);
    }

}
