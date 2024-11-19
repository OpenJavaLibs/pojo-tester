package com.java.pojo.issue192;


import org.junit.jupiter.api.Test;

import static com.java.pojo.api.assertion.Assertions.assertPojoMethodsFor;

class ConstructorWithZonedDateTimeTest {

    @Test
    void Should_Create_Instance_That_Have_Zoned_Date_Time_As_Constructor_Parameter() {
        assertPojoMethodsFor(ConstructorWithZonedDateTime.class).areWellImplemented();
    }
}

