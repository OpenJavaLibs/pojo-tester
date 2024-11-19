package com.java.pojo.internal.field.collections.map;

import java.util.Collections;
import java.util.Map;

class MapValueChanger extends AbstractMapFieldValueChanger<Map<?, ?>> {

    @Override
    protected Map<?, ?> increaseValue(final Map<?, ?> value, final Class<?> type) {
        return value == null
               ? Collections.emptyMap()
               : null;
    }
}
