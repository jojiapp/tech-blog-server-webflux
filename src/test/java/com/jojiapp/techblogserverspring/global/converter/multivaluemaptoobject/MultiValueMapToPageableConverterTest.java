package com.jojiapp.techblogserverspring.global.converter.multivaluemaptoobject;

import com.jojiapp.techblogserverspring.global.converter.multivaluemaptoobject.resolver.*;
import com.jojiapp.techblogserverspring.support.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.*;
import org.junit.jupiter.params.provider.*;
import org.springframework.data.domain.*;
import org.springframework.util.*;

import static org.assertj.core.api.Assertions.*;

@TestEnv
class MultiValueMapToPageableConverterTest {

    private final MultiValueMapToPageableConverter multiValueMapToPageableConverter =
            new MultiValueMapToPageableConverter(new MultiValueMapToObjectConverterResolver());

    @Test
    void MultiValueMap을_PageRequest객체로_치환한다() throws Exception {

        // Given
        final MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
        multiValueMap.add("page", "1");
        multiValueMap.add("size", "20");
        multiValueMap.add("sort", "name");
        multiValueMap.add("sort", "created,desc");

        // When
        final Pageable pageable = multiValueMapToPageableConverter.convert(multiValueMap, PageRequest.class);

        // Then
        assertThat(pageable.getPageSize()).isEqualTo(1);
        assertThat(pageable.getPageNumber()).isEqualTo(20);

        assert pageable.getSort().getOrderFor("name") != null;
        final Sort.Direction nameDirection = pageable.getSort().getOrderFor("name").getDirection();
        assertThat(nameDirection).isEqualTo(Sort.Direction.ASC);

        assert pageable.getSort().getOrderFor("created") != null;
        final Sort.Direction createdDirection = pageable.getSort().getOrderFor("created").getDirection();
        assertThat(createdDirection).isEqualTo(Sort.Direction.DESC);
    }

    @ParameterizedTest
    @CsvSource(value = {
            "a,1",
            "1,a"
    })
    void page와_size가_숫자가_아니면_예외가_발생한다(final String page, final String size) throws Exception {

        // Given
        final MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
        multiValueMap.add("page", page);
        multiValueMap.add("size", size);

        // When & Then
        assertThatThrownBy(() -> multiValueMapToPageableConverter.convert(multiValueMap, PageRequest.class))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @ValueSource(strings = {"asc", "qwe", ""})
    void sort정렬_옵션이_DESC가_아니라면_어떤_값이_와도_ASC정렬이다(final String direction) throws Exception {

        // Given
        final MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
        multiValueMap.add("page", "1");
        multiValueMap.add("size", "20");
        multiValueMap.add("sort", "name,%s".formatted(direction));
        multiValueMap.add("sort", "created,desc");

        // When
        final Pageable pageable = multiValueMapToPageableConverter.convert(multiValueMap, PageRequest.class);

        // Then
        assert pageable.getSort().getOrderFor("name") != null;
        final Sort.Direction nameDirection = pageable.getSort().getOrderFor("name").getDirection();
        assertThat(nameDirection).isEqualTo(Sort.Direction.ASC);
    }

    @ParameterizedTest
    @ValueSource(strings = {"DESC", "desc", "DeSc"})
    void Direction값은_대소문자를_구분하지_않는다(final String desc) throws Exception {

        // Given
        final MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
        multiValueMap.add("page", "1");
        multiValueMap.add("size", "20");
        multiValueMap.add("sort", "name,%s".formatted(desc));

        // When
        final Pageable pageable = multiValueMapToPageableConverter.convert(multiValueMap, PageRequest.class);

        // Then
        assert pageable.getSort().getOrderFor("name") != null;
        final Sort.Direction nameDirection = pageable.getSort().getOrderFor("name").getDirection();
        assertThat(nameDirection).isEqualTo(Sort.Direction.DESC);
    }
}
