package com.um.snownote.filters;

import org.springframework.data.mongodb.core.query.Criteria;

public class GenericFilter<T> implements Filter<T> {
    private String attribute;
    private Object value;

    public GenericFilter(String attribute, Object value) {
        this.attribute = attribute;
        this.value = value;
    }

    @Override
    public Criteria toCriteria() {
        return Criteria.where(attribute).is(value);
    }


}
