package com.java.pojo.internal.field.date;

import com.java.pojo.internal.field.AbstractFieldValueChanger;

import java.time.LocalTime;

class LocalTimeFieldValueChanger extends AbstractFieldValueChanger<LocalTime> {

    @Override
    protected LocalTime increaseValue(final LocalTime value, final Class<?> type) {
        return value.plusHours(1);
    }

    @Override
    protected boolean canChange(final Class<?> type) {
        return type.equals(LocalTime.class);
    }
}
