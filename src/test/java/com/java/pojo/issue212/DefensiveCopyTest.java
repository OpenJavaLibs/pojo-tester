package com.java.pojo.issue212;


import org.junit.jupiter.api.Test;
import com.java.pojo.api.assertion.Method;

import static com.java.pojo.api.assertion.Assertions.assertPojoMethodsFor;

class DefensiveCopyTest {

    @Test
    void Should_Test_Getters_And_Setters_In_Defensive_Copy_Class() {
        assertPojoMethodsFor(DefensiveCopy.class)
                .testing(Method.GETTER)
                .testing(Method.SETTER)
                .areWellImplemented();
    }
}

