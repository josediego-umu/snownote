package com.um.snownote.filters;

import org.springframework.data.domain.Sort;

public interface SortCriteria<T> {
    Sort toSort();
}
