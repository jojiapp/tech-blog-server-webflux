package com.jojiapp.techblogserverspring.global.converter.multivaluemaptoobject;

import com.fasterxml.jackson.databind.*;
import com.jojiapp.techblogserverspring.global.converter.multivaluemaptoobject.resolver.*;
import com.jojiapp.techblogserverspring.global.converter.multivaluemaptoobject.support.*;
import com.jojiapp.techblogserverspring.support.*;
import org.junit.jupiter.api.*;
import org.springframework.util.*;

import static org.assertj.core.api.Assertions.*;

@TestEnv
class MultiValueMapToObjectConverterTest {

    private final MultiValueMapToObjectConverter multiValueMapToObjectConverter =
            new MultiValueMapToObjectConverter(new MultiValueMapToObjectConverterResolver(), new ObjectMapper());

    @Test
    void MultiValueMap을_Object로_치환한다() throws Exception {

        // Given
        final MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
        multiValueMap.add("name", "이름");
        multiValueMap.add("age", "1");
        multiValueMap.add("age", "2");

        // When
        final PersonDTO personDTO = multiValueMapToObjectConverter.convert(multiValueMap, PersonDTO.class);

        // Then
        assertThat(personDTO.getName()).isEqualTo("이름");
        assertThat(personDTO.getAge().size()).isEqualTo(2);
        assertThat(personDTO.getAge().get(0)).isEqualTo(1);
        assertThat(personDTO.getAge().get(1)).isEqualTo(2);
    }

}
