package com.um.snownote.filters;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class compundFilter<T> implements Filter<T>, SortCriteria<T>, PageableCustom<T> {

    private final List<Filter<T>> criteriaList;
    private List<SortCriteria<T>> sortCriteriaList;
    private Pagination pagination;

    public compundFilter(List<Filter<T>> criteriaList, List<SortCriteria<T>> sortCriteriaList, Pagination pagination) {
        this.criteriaList = criteriaList;
        this.sortCriteriaList = sortCriteriaList;
        this.pagination = pagination;
    }

    public compundFilter(){

        this.criteriaList = new ArrayList<>();
        this.sortCriteriaList = new ArrayList<>();
        this.pagination = new Pagination(0,1);
    }
    public void addCriteria(Filter<T> criteria) {
        criteriaList.add(criteria);
    }

    public void addSortCriteria(SortCriteria<T> sortCriteria) {
        sortCriteriaList.add(sortCriteria);
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }

    public boolean hasPagination() {
        return pagination != null;
    }

    public boolean hasSort() {
        return sortCriteriaList != null && !sortCriteriaList.isEmpty();
    }

    public boolean hasCriteria() {
        return criteriaList != null && !criteriaList.isEmpty();
    }

    @Override
    public Criteria toCriteria() {
        Criteria[] criteriaArray = criteriaList.stream()
                .map(Filter::toCriteria)
                .toArray(Criteria[]::new);

        return new Criteria().andOperator(criteriaArray);
    }

    @Override
    public Sort toSort() {
        List<Sort.Order> orders = sortCriteriaList.stream()
                .flatMap(sort -> sort.toSort().stream())
                .collect(Collectors.toList());

        return Sort.by(orders);
    }
    @Override
    public Pageable toPageable() {
        if (pagination != null) {
            return PageRequest.of(pagination.pageNumber(), pagination.pageSize(), toSort());
        }
        return Pageable.unpaged();
    }


}
