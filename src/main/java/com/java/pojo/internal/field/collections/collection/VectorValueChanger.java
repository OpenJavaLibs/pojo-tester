package com.java.pojo.internal.field.collections.collection;

import com.java.pojo.internal.utils.CollectionUtils;

import java.util.Vector;

class VectorValueChanger extends AbstractCollectionFieldValueChanger<Vector<?>> {

    @Override
    protected Vector<?> increaseValue(final Vector<?> value, final Class<?> type) {
        return CollectionUtils.isNotEmpty(value)
               ? null
               : new Vector<>(CollectionUtils.asList(new Object()));
    }
}
