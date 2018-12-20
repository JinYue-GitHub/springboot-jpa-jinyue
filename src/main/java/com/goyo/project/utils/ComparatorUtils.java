package com.goyo.project.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
/**
 * @author JinYue
 * @ClassName ComparatorUtils
 * @Description 按条件排序或分组
 */
public class ComparatorUtils {
    /**
     * 按条件排序或分组
     * @param datas
     * @param c
     * @return List<List<T>>
     */
    public static <T> List<List<T>> divider(Collection<T> datas, Comparator<? super T> c) {
        List<List<T>> result = new ArrayList<List<T>>();
        for (T t : datas) {
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
                List<T> innerList = new ArrayList<T>();
                result.add(innerList);
                innerList.add(t);
            }
        }
        return result;
    }
}