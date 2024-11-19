package com.java.pojo.issue153;

import static com.java.pojo.api.assertion.Assertions.assertPojoMethodsFor;
import static com.java.pojo.api.assertion.Method.CONSTRUCTOR;
import static com.java.pojo.api.assertion.Method.EQUALS;
import static com.java.pojo.api.assertion.Method.HASH_CODE;
import static com.java.pojo.api.assertion.Method.TO_STRING;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import org.junit.jupiter.api.Test;

import com.java.pojo.internal.assertion.hashcode.EqualHashCodeAssertionError;

class NullOrEmptyCollectionsTest {

    @Test
    void shouldPojoBeWellImplemented() {
        assertPojoMethodsFor(Message.class).testing(TO_STRING, EQUALS, CONSTRUCTOR)
                                           .areWellImplemented();
    }
    
    @Test
    void shouldPojoBeWellImplemented1() {
        ;
        
        // when
        final Throwable result = catchThrowable(() -> assertPojoMethodsFor(Message.class).testing(HASH_CODE)
                .areWellImplemented());
        // then
        assertThat(result).isInstanceOf(EqualHashCodeAssertionError.class);
    }
}