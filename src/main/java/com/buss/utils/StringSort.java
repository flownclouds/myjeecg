package com.buss.utils;

import org.jeecgframework.tag.vo.datatable.SortDirection;

import java.util.Comparator;

/**
 * Created by shilin on 2015/7/20.
 */
public class StringSort implements Comparator<String> {
    private SortDirection sortOrder;

    public StringSort(SortDirection sortDirection) {
        this.sortOrder = sortDirection;
    }

    public int compare(String prev, String next) {
        return sortOrder.equals(SortDirection.asc) ? prev.compareTo(next)
                : next.compareTo(prev);
    }
}
