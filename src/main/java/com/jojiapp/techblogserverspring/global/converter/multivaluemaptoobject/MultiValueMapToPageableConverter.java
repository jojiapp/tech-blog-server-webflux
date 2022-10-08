package com.jojiapp.techblogserverspring.global.converter.multivaluemaptoobject;

import com.jojiapp.techblogserverspring.global.converter.multivaluemaptoobject.resolver.*;
import org.springframework.data.domain.*;
import org.springframework.stereotype.*;
import org.springframework.util.*;

import java.util.*;

@Component
public class MultiValueMapToPageableConverter extends AbstractMultiValueMapToObjectConverter {

    private static final String SIZE = "size";
    private static final String PAGE = "page";
    private static final String SORT = "sort";

    public MultiValueMapToPageableConverter(final MultiValueMapToObjectConverterResolver multiValueMapToObjectConverterResolver) {

        super(PageRequest.class, multiValueMapToObjectConverterResolver);
    }

    @Override
    public <T> T convert(final MultiValueMap<String, String> multiValueMap, final Class<T> classType) {

        return (T) PageRequest.of(
                getSize(multiValueMap),
                getPage(multiValueMap),
                Sort.by(getOrders(multiValueMap))
        );
    }

    private static int getSize(final MultiValueMap<String, String> multiValueMap) {

        return parseInt(multiValueMap, SIZE);
    }

    private static int getPage(final MultiValueMap<String, String> multiValueMap) {

        return parseInt(multiValueMap, PAGE);
    }

    private static int parseInt(final MultiValueMap<String, String> multiValueMap, final String key) {

        try {
            return Integer.parseInt(multiValueMap.get(key).get(0));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("%s: 숫자가 아닙니다.".formatted(key), e);
        }
    }

    private static List<Sort.Order> getOrders(final MultiValueMap<String, String> multiValueMap) {

        return multiValueMap.get(SORT)
                .stream()
                .map(MultiValueMapToPageableConverter::createOrder)
                .toList();
    }

    private static Sort.Order createOrder(final String sort) {

        return getOrder(sort).equalsIgnoreCase(Sort.Direction.DESC.name()) ?
                Sort.Order.desc(getProperty(sort)) :
                Sort.Order.asc(getProperty(sort));
    }

    private static String getOrder(final String sort) {

        try {
            return sort.split(",")[1];
        } catch (ArrayIndexOutOfBoundsException e) {
            return Sort.Direction.ASC.name();
        }
    }

    private static String getProperty(final String sort) {

        return sort.split(",")[0];
    }

}
