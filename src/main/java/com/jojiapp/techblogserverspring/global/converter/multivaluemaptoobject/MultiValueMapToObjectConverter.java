package com.jojiapp.techblogserverspring.global.converter.multivaluemaptoobject;

import com.fasterxml.jackson.databind.*;
import com.jojiapp.techblogserverspring.global.converter.multivaluemaptoobject.resolver.*;
import org.springframework.stereotype.*;
import org.springframework.util.*;

import java.util.*;

@Component
public class MultiValueMapToObjectConverter extends AbstractMultiValueMapToObjectConverter {

    private final ObjectMapper objectMapper;

    public MultiValueMapToObjectConverter(final MultiValueMapToObjectConverterResolver multiValueMapToObjectConverterResolver,
                                          final ObjectMapper objectMapper) {

        super(Object.class, multiValueMapToObjectConverterResolver);
        this.objectMapper = objectMapper;
    }

    @Override
    public <T> T convert(final MultiValueMap<String, String> multiValueMap, final Class<T> classType) {

        return objectMapper.convertValue(toMap(multiValueMap), classType);
    }

    private static Map<String, Object> toMap(final MultiValueMap<String, String> multiValueMap) {

        final Map<String, Object> result = new HashMap<>();
        multiValueMap.forEach((key, value) -> result.put(key, isList(value) ? value : value.get(0)));

        return result;
    }

    private static boolean isList(final List<String> value) {

        return value.size() > 1;
    }

}
