package com.jojiapp.techblogserverspring.global.converter.queryparams;

import com.fasterxml.jackson.databind.*;
import com.jojiapp.techblogserverspring.global.converter.queryparams.resolver.*;
import org.springframework.stereotype.*;
import org.springframework.util.*;

import java.util.*;

@Component
public class DefaultQueryParamsObjectConverter extends AbstractQueryParamsConverter {

    private final ObjectMapper objectMapper;

    public DefaultQueryParamsObjectConverter(final QueryParamsConverterResolver queryParamsConverterResolver,
                                             final ObjectMapper objectMapper) {

        super(Object.class, queryParamsConverterResolver);
        this.objectMapper = objectMapper;
    }

    @Override
    public <T> T convert(final MultiValueMap<String, String> queryParams, final Class<T> classType) {

        return objectMapper.convertValue(toMap(queryParams), classType);
    }

    private static Map<String, Object> toMap(final MultiValueMap<String, String> queryParams) {

        final Map<String, Object> result = new HashMap<>();
        queryParams.forEach((key, value) -> {
            if (isList(value)) {
                result.put(key, value);
            } else {
                result.put(key, value.get(0));
            }
        });

        return result;
    }

    private static boolean isList(final List<String> value) {

        return value.size() > 1;
    }

}
