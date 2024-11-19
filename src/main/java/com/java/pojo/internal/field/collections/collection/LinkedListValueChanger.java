package com.java.pojo.internal.field.collections.collection;

import com.java.pojo.internal.utils.CollectionUtils;

import java.util.LinkedList;


class LinkedListValueChanger extends AbstractCollectionFieldValueChanger<LinkedList<?>> {


    @Override
    protected LinkedList<?> increaseValue(final LinkedList<?> value, final Class<?> type) {
        return CollectionUtils.isNotEmpty(value)
               ? null
               : new LinkedList<>(CollectionUtils.asList(new Object()));
    }
}
