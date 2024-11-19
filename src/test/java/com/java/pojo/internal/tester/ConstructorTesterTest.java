package com.java.pojo.internal.tester;

import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;
import org.junit.jupiter.api.Test;
import com.java.pojo.api.ConstructorParameters;
import com.java.pojo.api.assertion.Assertions;
import com.java.pojo.internal.assertion.constructor.ConstructorAssertionError;
import com.java.pojo.internal.field.DefaultFieldValueChanger;
import com.java.pojo.internal.field.collections.CollectionsFieldValueChanger;
import com.java.pojo.internal.field.date.DefaultDateAndTimeFieldValueChanger;
import com.java.pojo.internal.instantiator.Instantiable;
import com.java.pojo.internal.preconditions.ParameterPreconditions;
import com.java.pojo.internal.utils.ClassLoader;
import com.java.pojo.internal.utils.CollectionUtils;
import com.java.pojo.internal.utils.ReflectionUtils;
import com.java.pojo.internal.utils.Sublists;

import artefact.classes.ClassWithSyntheticConstructor;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;


class ConstructorTesterTest {

    @Test
    void Should_Pass_All_Constructor_Tests() {
        // given
        final Class[] classesToTest = { Pojo.class,
                ParameterPreconditions.class,
                CollectionsFieldValueChanger.class,
                DefaultFieldValueChanger.class,
                Assertions.class,
                Instantiable.class,
                Sublists.class,
                ReflectionUtils.class,
                DefaultDateAndTimeFieldValueChanger.class,
                CollectionUtils.class,
                ClassLoader.class };
        final ConstructorTester constructorTester = new ConstructorTester(DefaultFieldValueChanger.INSTANCE);

        // when
        final Throwable result = catchThrowable(() -> constructorTester.testAll(classesToTest));

        // then
        assertThat(result).isNull();
    }

    @Test
    void Should_Skip_Constructor_Tests_If_Class_Is_Abstract() {
        // given
        final Class[] classesToTest = { AbstractBadConstructorPojo.class };
        final ConstructorTester constructorTester = new ConstructorTester();

        // when
        final Throwable result = catchThrowable(() -> constructorTester.testAll(classesToTest));

        // then
        assertThat(result).isNull();
    }

    @Test
    void Should_Fail_Constructor_Tests() {
        // given
        final Class[] classesToTest = { BadConstructorPojo.class };
        final ConstructorTester constructorTester = new ConstructorTester();

        // when
        final Throwable result = catchThrowable(() -> constructorTester.testAll(classesToTest));

        // then
        assertThat(result).isInstanceOf(ConstructorAssertionError.class);
    }

    @Test
    void Should_Use_User_Constructor_Parameters() {
        // given
        final Class[] classesToTest = { ClassWithSyntheticConstructor.class };

        final ConstructorParameters parameters = new ConstructorParameters(new Object[]{ "string" },
                                                                           new Class[]{ String.class });
        final MultiValuedMap<Class<?>, ConstructorParameters> constructorParameters = spy(new ArrayListValuedHashMap<>());
        constructorParameters.put(ClassWithSyntheticConstructor.class, parameters);

        final ConstructorTester constructorTester = new ConstructorTester();
        constructorTester.setUserDefinedConstructors(constructorParameters);

        // when
        final Throwable result = catchThrowable(() -> constructorTester.testAll(classesToTest));

        // then
        assertThat(result).isNull();
        verify(constructorParameters).get(ClassWithSyntheticConstructor.class);
    }

    @Test
    void Should_Create_Constructor_Parameters_When_Parameters_Are_Not_Provided() {
        // given
        final Class[] classesToTest = { ClassWithSyntheticConstructor.class };

        final MultiValuedMap<Class<?>, ConstructorParameters> constructorParameters = spy(new ArrayListValuedHashMap<>());

        final ConstructorTester constructorTester = new ConstructorTester();
        constructorTester.setUserDefinedConstructors(constructorParameters);

        // when
        final Throwable result = catchThrowable(() -> constructorTester.testAll(classesToTest));

        // then
        assertThat(result).isNull();
        verify(constructorParameters, never()).get(ClassWithSyntheticConstructor.class);
    }

    @Test
    void Should_Create_Constructor_Parameters_When_Could_Not_Find_Matching_Constructor_Parameters_Types() {
        // given
        final Class[] classesToTest = { ClassWithSyntheticConstructor.class };

        final ConstructorParameters parameters = spy(new ConstructorParameters(new Object[]{ "to",
                "many",
                "parameters" },
                                                                               new Class[]{ String.class,
                                                                                       String.class,
                                                                                       String.class }));
        final MultiValuedMap<Class<?>, ConstructorParameters> constructorParameters = spy(new ArrayListValuedHashMap<>());
        constructorParameters.put(ClassWithSyntheticConstructor.class, parameters);

        final ConstructorTester constructorTester = new ConstructorTester();
        constructorTester.setUserDefinedConstructors(constructorParameters);

        // when
        final Throwable result = catchThrowable(() -> constructorTester.testAll(classesToTest));

        // then
        assertThat(result).isNull();
        verify(parameters, never()).getParameters();
    }

    private static class Pojo {
        Pojo() {
        }

        Pojo(final String x) {
        }

        Pojo(final int y) {
        }
    }

    private static class BadConstructorPojo {
        BadConstructorPojo() {
            throw new RuntimeException("test");
        }
    }

    private abstract static class AbstractBadConstructorPojo {
        AbstractBadConstructorPojo() {
            throw new RuntimeException("test");
        }
    }
}