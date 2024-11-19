package com.java.pojo.internal.field.primitive;

class IntegerValueChanger extends AbstractPrimitiveValueChanger<Integer> {

    @Override
    protected Integer increase(final Integer value) {
        return value + 1;
    }
}
