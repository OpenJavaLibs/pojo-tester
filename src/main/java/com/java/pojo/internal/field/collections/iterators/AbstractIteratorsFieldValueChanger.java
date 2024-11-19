package com.java.pojo.internal.field.collections.iterators;


import com.java.pojo.internal.field.AbstractFieldValueChanger;

public abstract class AbstractIteratorsFieldValueChanger<T> extends AbstractFieldValueChanger<T> {

    public static final AbstractFieldValueChanger INSTANCE = new IteratorValueChanger().attachNext(new IterableValueChanger());


    @Override
    protected boolean canChange(final Class<?> type) {
        return type.isAssignableFrom(getGenericTypeClass());
    }

}
