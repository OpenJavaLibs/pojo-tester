package com.java.pojo.internal.instantiator;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.function.Executable;
import com.java.pojo.api.ConstructorParameters;

import java.util.stream.Stream;

import static helpers.TestHelper.getDefaultDisplayName;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;


class UserDefinedConstructorInstantiatorTest {

    private final MultiValuedMap<Class<?>, ConstructorParameters> constructorParameters = new ArrayListValuedHashMap<>();

    {
        constructorParameters.put(UserDefinedClass.class,
                                  new ConstructorParameters(new Object[]{1, 2}, new Class<?>[]{int.class, int.class}));
        constructorParameters.put(ClassWithPrivateConstructor.class,
                                  new ConstructorParameters(new Object[]{1}, new Class<?>[]{int.class}));
        constructorParameters.put(One_Arg_Constructor_Throws_NPE.class,
                                  new ConstructorParameters(new Object[]{1}, new Class<?>[]{Object.class}));
        constructorParameters.put(No_Args_Constructor_Throws_NPE.class,
                                  new ConstructorParameters(new Object[0], new Class<?>[0]));
        constructorParameters.put(InnerClass.class,
                                  new ConstructorParameters(new Object[]{1}, new Class<?>[]{int.class}));
        constructorParameters.put(NestedClass.class,
                                  new ConstructorParameters(new Object[]{1}, new Class<?>[]{int.class}));
    }

    @Test
    void Should_Create_Object_Using_Private_Constructor() {
        // given
        final Class<ClassWithPrivateConstructor> classToInstantiate = ClassWithPrivateConstructor.class;
        final UserDefinedConstructorInstantiator instantiator = new UserDefinedConstructorInstantiator(
                classToInstantiate,
                constructorParameters);

        // when
        final Object result = instantiator.instantiate();

        // then
        assertThat(result).isInstanceOf(classToInstantiate);
    }

    @Test
    void Should_Create_Object_For_Inner_Class() {
        // given
        final Class<InnerClass> classToInstantiate = InnerClass.class;
        final UserDefinedConstructorInstantiator instantiator = new UserDefinedConstructorInstantiator(
                classToInstantiate,
                constructorParameters);

        // when
        final Object result = instantiator.instantiate();

        // then
        assertThat(result).isInstanceOf(classToInstantiate);
    }

    @Test
    void Should_Create_Object_For_Nested_Class() {
        // given
        final Class<NestedClass> classToInstantiate = NestedClass.class;
        final UserDefinedConstructorInstantiator instantiator = new UserDefinedConstructorInstantiator(
                classToInstantiate,
                constructorParameters);

        // when
        final Object result = instantiator.instantiate();

        // then
        assertThat(result).isInstanceOf(classToInstantiate);
    }

    @TestFactory
    Stream<DynamicTest> Should_Throw_Exception_When_Cannot_Instantiate_Class() {
        return Stream.of(One_Arg_Constructor_Throws_NPE.class,
                         No_Args_Constructor_Throws_NPE.class)
                     .map(value -> dynamicTest(getDefaultDisplayName(value.getName()),
                                               Should_Throw_Exception_When_Cannot_Instantiate_Class(value)));
    }

    private Executable Should_Throw_Exception_When_Cannot_Instantiate_Class(final Class<?> classToInstantiate) {
        return () -> {
            // given
            final UserDefinedConstructorInstantiator instantiator = new UserDefinedConstructorInstantiator(
                    classToInstantiate,
                    constructorParameters);

            // when
            final Throwable result = catchThrowable(instantiator::instantiate);

            // then
            assertThat(result).isInstanceOf(ObjectInstantiationException.class);
        };
    }

    @Data
    @AllArgsConstructor
    private static class NestedClass {
        private int a;
    }

    @Data
    @AllArgsConstructor
    private class InnerClass {
        private int a;
    }

    private class UserDefinedClass {
        private final int a;
        private final int b;

        UserDefinedClass(final int a) {
            throw new RuntimeException("test");
        }

        UserDefinedClass(final int a, final int b) {
            this.a = a;
            this.b = b;
        }
    }

    private class ClassWithPrivateConstructor {
        private ClassWithPrivateConstructor(final int a) {
        }
    }

    private class One_Arg_Constructor_Throws_NPE {
        One_Arg_Constructor_Throws_NPE(final Object o) {
            throw new NullPointerException("test");
        }
    }

    private class No_Args_Constructor_Throws_NPE {

        No_Args_Constructor_Throws_NPE() {
            throw new NullPointerException("test");
        }
    }
}
