package com.jojiapp.techblogserverspring.global.converter.queryparams.resolver;

import com.jojiapp.techblogserverspring.global.converter.queryparams.*;
import org.springframework.stereotype.*;
import org.springframework.util.*;

import java.util.*;

@Component
public class QueryParamsConverterResolver {

    private final Map<Class<?>, AbstractQueryParamsConverter> converterMap = new ConcurrentReferenceHashMap<>();

    public <T> T convert(final MultiValueMap<String, String> queryParams, final Class<T> classType) {

        return getObjectConverter(classType).convert(queryParams, classType);
    }

    private AbstractQueryParamsConverter getObjectConverter(final Class<?> classType) {

        return converterMap.getOrDefault(classType, getDefaultConverterOrThrow());
    }

    private AbstractQueryParamsConverter getDefaultConverterOrThrow() {

        final AbstractQueryParamsConverter defaultConverter = converterMap.get(Object.class);
        if(defaultConverter == null) {
            throw new IllegalStateException("Default QueryParamsConverter가 존재하지 않습니다.");
        }

        return defaultConverter;
    }

    public void addConverter(final Class<?> classType, final AbstractQueryParamsConverter converter) {

        this.converterMap.put(classType, converter);
    }

}
