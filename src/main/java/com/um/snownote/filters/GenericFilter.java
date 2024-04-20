package com.um.snownote.filters;

import org.springframework.data.mongodb.core.query.Criteria;

import java.util.regex.Pattern;

public class GenericFilter<T> implements Filter<T> {
    private String attribute;
    private Object value;

    public GenericFilter(String attribute, Object value) {
        this.attribute = attribute;
        this.value = value;
    }

    @Override
    public Criteria toCriteria() {
        Pattern pattern = Pattern.compile(".*" + value + ".*", Pattern.CASE_INSENSITIVE);
        return Criteria.where(attribute).regex(pattern);
    }


}
