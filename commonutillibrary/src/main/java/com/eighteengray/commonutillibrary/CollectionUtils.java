package com.eighteengray.commonutillibrary;

import android.util.SparseArray;

import java.util.Collection;
import java.util.Map;

/**
 * Created by yudunfu on 16-6-15.
 */
public final class CollectionUtils {

    public static boolean valid(Collection<?> collection) {
        return collection != null && !collection.isEmpty();
    }

    public static boolean isNullOrEmpty(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    public static boolean isNullOrEmpty(SparseArray array) {
        return array == null || array.size() == 0;
    }

    public static boolean isNullOrEmpty(Object[] array) {
        return array == null || array.length == 0;
    }

    public static boolean isNullOrEmpty(Map<?, ?> map) {
        return map == null || map.isEmpty();
    }

    public static boolean equalSize(Collection<?> collection, int size) {
        if (collection == null || size < 0) {
            return false;
        }
        return size == collection.size();
    }

    public static boolean moreThanSize(Collection<?> collection, int size) {
        if (collection == null || size < 0) {
            return false;
        }
        return size < collection.size();
    }

    public static boolean equalOrMoreThanSize(Collection<?> collection, int size) {
        if (collection == null || size < 0) {
            return false;
        }
        return size <= collection.size();
    }

    public static int size(Collection<?> collection) {
        if (collection == null) {
            return 0;
        }

        return collection.size();
    }

}
