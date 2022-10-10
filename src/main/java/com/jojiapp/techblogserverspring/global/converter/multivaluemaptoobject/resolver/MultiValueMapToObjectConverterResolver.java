package com.jojiapp.techblogserverspring.global.converter.multivaluemaptoobject.resolver;

import com.jojiapp.techblogserverspring.global.converter.multivaluemaptoobject.*;
import org.springframework.stereotype.*;
import org.springframework.util.*;

import java.util.*;

@Component
public class MultiValueMapToObjectConverterResolver {

    private final Map<Class<?>, AbstractMultiValueMapToObjectConverter> converterMap = new ConcurrentReferenceHashMap<>();

    public <T> T convert(final MultiValueMap<String, String> multiValueMap, final Class<T> classType) {

        return getObjectConverter(classType).convert(multiValueMap, classType);
    }

    private AbstractMultiValueMapToObjectConverter getObjectConverter(final Class<?> classType) {

        return converterMap.getOrDefault(classType, getDefaultConverterOrThrow());
    }

    private AbstractMultiValueMapToObjectConverter getDefaultConverterOrThrow() {

        final AbstractMultiValueMapToObjectConverter defaultConverter = converterMap.get(Object.class);
        if (defaultConverter == null) {
            throw new IllegalStateException("Default MultiValueMapToObjectConverter가 존재하지 않습니다.");
        }

        return defaultConverter;
    }

    public void addConverter(final Class<?> classType, final AbstractMultiValueMapToObjectConverter converter) {

        this.converterMap.put(classType, converter);
    }

}
