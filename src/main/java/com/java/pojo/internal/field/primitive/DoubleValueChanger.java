package com.java.pojo.internal.field.primitive;


class DoubleValueChanger extends AbstractPrimitiveValueChanger<Double> {

    @Override
    protected Double increase(final Double value) {
        return 2 * (value + 1);
    }
}
