package com.java.pojo.issue215;

import org.junit.jupiter.api.Test;
import com.java.pojo.api.ConstructorParameters;

import static com.java.pojo.api.assertion.Assertions.assertPojoMethodsFor;
import static com.java.pojo.api.assertion.Method.*;

class ClassAsConstructorParameterTest {

    @Test
    void Should_Test_Class_With_Class_As_Constructor_Parameter() {
        // given
        final Class<?> classUnderTest = ProductConfig.class;
        final Object[] parameters = {Nourriture.class, "Legume", 1d, false};
        final Class<?>[] parameterTypes = {Class.class, String.class, double.class, boolean.class};
        final ConstructorParameters constructorParameters = new ConstructorParameters(parameters, parameterTypes);

        // then
        assertPojoMethodsFor(classUnderTest).create(classUnderTest, constructorParameters)
                                            .testing(GETTER)
                                            .testing(TO_STRING)
                                            .testing(CONSTRUCTOR)
                                            .areWellImplemented();
    }
}
