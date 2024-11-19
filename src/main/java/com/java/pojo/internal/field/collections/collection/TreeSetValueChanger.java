package com.java.pojo.internal.field.collections.collection;

import com.java.pojo.internal.utils.CollectionUtils;

import java.util.TreeSet;

class TreeSetValueChanger extends AbstractCollectionFieldValueChanger<TreeSet<?>> {

    @Override
    protected TreeSet<?> increaseValue(final TreeSet<?> value, final Class<?> type) {
        return CollectionUtils.isNotEmpty(value)
               ? null
               : createTreeSet();
    }

    private TreeSet<Object> createTreeSet() {
        final TreeSet<Object> objects = new TreeSet<>();
        objects.add((Comparable<Object>) o -> 0);
        return objects;
    }
}
