package com.java.pojo.internal.field.collections.map;

import java.util.Hashtable;


class HashtableValueChanger extends AbstractMapFieldValueChanger<Hashtable<?, ?>> {

    @Override
    protected Hashtable<?, ?> increaseValue(final Hashtable<?, ?> value, final Class<?> type) {
        return value == null
               ? new Hashtable<>()
               : null;
    }
}
