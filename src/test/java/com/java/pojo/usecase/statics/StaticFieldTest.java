package com.java.pojo.usecase.statics;


import org.junit.jupiter.api.Test;

import static com.java.pojo.api.FieldPredicate.exclude;
import static com.java.pojo.api.FieldPredicate.include;
import static com.java.pojo.api.assertion.Assertions.assertPojoMethodsFor;

class StaticFieldTest {

    @Test
    void Should_Test_Class_With_Static_Fields() {
        // given

        // when

        // then
        assertPojoMethodsFor(ClassWithStaticField.class).areWellImplemented();
    }

    @Test
    void Should_Test_Class_With_Excluded_Static_Fields() {
        // given

        // when

        // then
        assertPojoMethodsFor(ClassWithStaticField.class, exclude("STATIC_FINAL")).areWellImplemented();
    }

    @Test
    void Should_Test_Class_With_Included_Non_Static_Fields() {
        // given

        // when

        // then
        assertPojoMethodsFor(ClassWithStaticField.class, include("a", "b")).areWellImplemented();
    }
}
