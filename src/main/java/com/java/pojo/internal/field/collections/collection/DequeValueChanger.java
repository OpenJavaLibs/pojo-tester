package com.java.pojo.internal.field.collections.collection;

import com.java.pojo.internal.utils.CollectionUtils;

import java.util.Deque;
import java.util.LinkedList;

class DequeValueChanger extends AbstractCollectionFieldValueChanger<Deque<?>> {

    @Override
    protected Deque<?> increaseValue(final Deque<?> value, final Class<?> type) {
        return CollectionUtils.isNotEmpty(value)
               ? null
               : new LinkedList<>(CollectionUtils.asList(new Object()));
    }
}
