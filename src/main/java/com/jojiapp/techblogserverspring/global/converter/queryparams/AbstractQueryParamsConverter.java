package com.jojiapp.techblogserverspring.global.converter.queryparams;

import com.jojiapp.techblogserverspring.global.converter.queryparams.resolver.*;
import org.springframework.util.*;

public abstract class AbstractQueryParamsConverter {

    protected AbstractQueryParamsConverter(final Class<?> classType,
                                           final QueryParamsConverterResolver queryParamsConverterResolver) {

        queryParamsConverterResolver.addConverter(classType, this);
    }

    public abstract <T> T convert(final MultiValueMap<String, String> queryParams, final Class<T> classType);

}
