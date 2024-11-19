package com.java.pojo.internal.field.collections.map;

import java.util.HashMap;


class HashMapValueChanger extends AbstractMapFieldValueChanger<HashMap<?, ?>> {

    @Override
    protected HashMap<?, ?> increaseValue(final HashMap<?, ?> value, final Class<?> type) {
        return value == null
               ? new HashMap<>()
               : null;
    }
}
