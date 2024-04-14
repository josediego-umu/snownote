package com.um.snownote.filters;

import org.springframework.data.mongodb.core.query.Criteria;

public interface Filter<T> {
    Criteria toCriteria();
}
