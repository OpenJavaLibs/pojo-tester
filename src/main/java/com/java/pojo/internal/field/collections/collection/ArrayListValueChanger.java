package com.java.pojo.internal.field.collections.collection;

import com.java.pojo.internal.utils.CollectionUtils;

import java.util.ArrayList;

class ArrayListValueChanger extends AbstractCollectionFieldValueChanger<ArrayList<?>> {

    @Override
    protected ArrayList<?> increaseValue(final ArrayList<?> value, final Class<?> type) {
        return CollectionUtils.isNotEmpty(value)
               ? null
               : CollectionUtils.asList(new Object());
    }
}
