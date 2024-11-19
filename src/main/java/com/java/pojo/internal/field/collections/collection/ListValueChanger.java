package com.java.pojo.internal.field.collections.collection;

import com.java.pojo.internal.utils.CollectionUtils;

import java.util.List;

class ListValueChanger extends AbstractCollectionFieldValueChanger<List<?>> {

    @Override
    protected List<?> increaseValue(final List<?> value, final Class<?> type) {
        return CollectionUtils.isNotEmpty(value)
               ? null
               : CollectionUtils.asList(new Object());
    }
}
