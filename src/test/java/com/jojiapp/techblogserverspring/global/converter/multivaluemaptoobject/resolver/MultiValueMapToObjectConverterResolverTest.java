package com.jojiapp.techblogserverspring.global.converter.multivaluemaptoobject.resolver;

import com.fasterxml.jackson.databind.*;
import com.jojiapp.techblogserverspring.global.converter.multivaluemaptoobject.*;
import com.jojiapp.techblogserverspring.global.converter.multivaluemaptoobject.support.*;
import com.jojiapp.techblogserverspring.support.*;
import org.junit.jupiter.api.*;
import org.springframework.data.domain.*;
import org.springframework.util.*;

import static org.assertj.core.api.Assertions.*;

@TestEnv
class MultiValueMapToObjectConverterResolverTest {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private MultiValueMapToObjectConverterResolver multiValueMapToObjectConverterResolver;


    @BeforeEach
    void setUp() {
        multiValueMapToObjectConverterResolver = new MultiValueMapToObjectConverterResolver();
    }

    @Test
    void 객체타입을_지원하는_MultiValueMapToObjectConverter가_우선_선택된다() throws Exception {

        // Given
        new MultiValueMapToPageableConverter(multiValueMapToObjectConverterResolver);
        new MultiValueMapToObjectConverter(multiValueMapToObjectConverterResolver, objectMapper);

        final MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
        multiValueMap.add("page", "1");
        multiValueMap.add("size", "20");
        multiValueMap.add("sort", "name");
        multiValueMap.add("sort", "createdAt,desc");

        // When
        final Pageable pageable = multiValueMapToObjectConverterResolver.convert(multiValueMap, PageRequest.class);

        // Then
        assertThat(pageable.getPageSize()).isEqualTo(1);
        assertThat(pageable.getPageNumber()).isEqualTo(20);

        assert pageable.getSort().getOrderFor("name") != null;
        assertThat(pageable.getSort().getOrderFor("name").getDirection()).isEqualTo(Sort.Direction.ASC);

        assert pageable.getSort().getOrderFor("createdAt") != null;
        assertThat(pageable.getSort().getOrderFor("createdAt").getDirection()).isEqualTo(Sort.Direction.DESC);
    }

    @Test
    void 객체타입을_지원하는_MultiValueMapToObjectConverter가_없다면_MultiValueMapToObjectConverter를_사용한다() {

        // Given
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

    @Test
    void 기본_MultiValueMapToObjectConverter가_없다면_예외가_발생한다() {

        // Given
        final MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
        multiValueMap.add("name", "이름");
        multiValueMap.add("age", "1");
        multiValueMap.add("age", "2");

        // When & Then
        assertThatThrownBy(() -> multiValueMapToObjectConverterResolver.convert(multiValueMap, PersonDTO.class))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("Default MultiValueMapToObjectConverter가 존재하지 않습니다.");
    }

}
