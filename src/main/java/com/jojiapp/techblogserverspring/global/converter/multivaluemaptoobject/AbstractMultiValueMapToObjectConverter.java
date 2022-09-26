package com.jojiapp.techblogserverspring.global.converter.multivaluemaptoobject;

import com.jojiapp.techblogserverspring.global.converter.multivaluemaptoobject.resolver.*;
import org.springframework.util.*;

public abstract class AbstractMultiValueMapToObjectConverter {

    protected AbstractMultiValueMapToObjectConverter(final Class<?> classType,
                                                     final MultiValueMapToObjectConverterResolver multiValueMapToObjectConverterResolver) {

        multiValueMapToObjectConverterResolver.addConverter(classType, this);
    }

    public abstract <T> T convert(final MultiValueMap<String, String> queryParams, final Class<T> classType);

}
