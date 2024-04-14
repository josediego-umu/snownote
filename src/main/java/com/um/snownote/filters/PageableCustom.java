package com.um.snownote.filters;

import org.springframework.data.domain.Pageable;

public interface PageableCustom<T> {

    Pageable toPageable();
}
