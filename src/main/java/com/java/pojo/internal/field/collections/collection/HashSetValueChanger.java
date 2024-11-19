package com.java.pojo.internal.field.collections.collection;

import com.java.pojo.internal.utils.CollectionUtils;

import java.util.HashSet;

class HashSetValueChanger extends AbstractCollectionFieldValueChanger<HashSet<?>> {


    @Override
    protected HashSet<?> increaseValue(final HashSet<?> value, final Class<?> type) {
        return CollectionUtils.isNotEmpty(value)
               ? null
               : new HashSet<>(CollectionUtils.asList(new Object()));
    }
}
