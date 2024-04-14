package com.um.snownote.filters;

import org.springframework.data.domain.Sort;

public class OrderBy<T> implements SortCriteria<T> {
    private final String field;
    private final Sort.Direction direction;

    public OrderBy(String field, Sort.Direction direction) {
        this.field = field;
        this.direction = direction;
    }

    @Override
    public Sort toSort() {
        return Sort.by(direction, field);
    }
}
