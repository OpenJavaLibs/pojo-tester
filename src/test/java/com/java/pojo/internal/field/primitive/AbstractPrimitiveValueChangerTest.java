package com.java.pojo.internal.field.primitive;


import static org.assertj.core.api.Assertions.assertThat;

import java.lang.reflect.Field;
import java.util.List;

import org.junit.jupiter.api.Test;

import lombok.Data;


class AbstractPrimitiveValueChangerTest {

    @Test
    void Should_Return_False_When_Field_Is_Not_Primitive() throws Exception {
        // given
        final Field field = A.class.getDeclaredField("temp");
        final AbstractPrimitiveValueChanger<Object> changerMock = new ImplementationForTest();

        // when
        final boolean result = changerMock.canChange(field.getType());

        // then
        assertThat(result).isFalse();
    }

    private class ImplementationForTest extends AbstractPrimitiveValueChanger<Object> {

        @Override
        protected Object increase(final Object value) {
            return null;
        }
    }
    
    @Data
    private class A {
    	private List<Integer> temp;
    }
}
