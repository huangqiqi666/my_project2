package com.hikvision.pbg.sitecodeprj.utils;

import org.apache.commons.collections4.CollectionUtils;

import java.util.List;

public class ListUtils {

    //获取两个集合并集（自动去重）
    public static <T> List<T> getUnion(List<T> list1, List<T> list2) {
        return (List<T>) CollectionUtils.union(list1, list2);
    }

    //获取两个集合交集
    public static <T> List<T> getIntersection(List<T> list1, List<T> list2) {
        return (List<T>) CollectionUtils.intersection(list1, list2);
    }

    //获取两个集合交集的补集 即 list1 + list2 - 交集
    public static <T> List<T> getDisjunction(List<T> list1, List<T> list2) {
        return (List<T>) CollectionUtils.disjunction(list1, list2);
    }

    //获取两个集合的差集 list1 - 交集
    public static <T> List<T> getSubtract(List<T> list1, List<T> list2) {
        return (List<T>) CollectionUtils.subtract(list1, list2);
    }
}