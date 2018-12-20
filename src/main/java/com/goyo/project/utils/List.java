package com.goyo.project.utils;

import java.util.Collection;
import java.util.Comparator;

public interface List<E> extends java.util.List<E>{
	  /**
     * 按条件排序或分组
     * @param datas
     * @param c
     * @return List<List<T>>
     */
	@SuppressWarnings("hiding")
	default <E> java.util.List<java.util.List<E>> divider(Collection<E> datas, Comparator<? super E> c) {
		java.util.List<java.util.List<E>> result = new java.util.ArrayList<java.util.List<E>>();
        for (E t : datas) {
            boolean isSameGroup = false;
            for (int j = 0; j < result.size(); j++) {
                if (c.compare(t, result.get(j).get(0)) == 0) {
                    isSameGroup = true;
                    result.get(j).add(t);
                    break;
                }
            }
            if (!isSameGroup) {
                // 创建
            	java.util.List<E> innerList = new java.util.ArrayList<E>();
                result.add(innerList);
                innerList.add(t);
            }
        }
        return result;
    }
}
