package com.java.pojo.internal.instantiator;

import lombok.Data;
import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;
import org.junit.jupiter.api.Test;
import com.java.pojo.api.ConstructorParameters;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Constructor;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;


class AbstractMultiConstructorInstantiatorTest {

    @Test
    void Should_Create_Object_Using_User_Parameters() {
        // given
        final ArrayListValuedHashMap<Class<?>, ConstructorParameters> constructorParameters = new ArrayListValuedHashMap<>();
        final Class<?> clazz = A.class;
        constructorParameters.put(clazz, new ConstructorParameters(new Object[]{ 12345 }, new Class[]{ int.class }));
        final AbstractMultiConstructorInstantiator instantiator = new MockMultiConstructorInstantiator(clazz,
                                                                                                       constructorParameters);
        final A expectedResult = new A(12345);

        // when
        final Object result = instantiator.instantiateUsingUserParameters();

        // then
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void Should_Return_Null_If_Parameters_For_This_Class_Are_Empty() {
        // given
        final ArrayListValuedHashMap<Class<?>, ConstructorParameters> constructorParameters = new ArrayListValuedHashMap<>();
        final Class<?> clazz = A.class;
        final AbstractMultiConstructorInstantiator instantiator = new MockMultiConstructorInstantiator(clazz,
                                                                                                       constructorParameters);

        // when
        final Object result = instantiator.instantiateUsingUserParameters();

        // then
        assertThat(result).isNull();
    }

    @Test
    void Should_Throw_Exception_If_Constructor_Throws_Exception() {
        // given
        final ArrayListValuedHashMap<Class<?>, ConstructorParameters> constructorParameters = new ArrayListValuedHashMap<>();
        final Class<?> clazz = B.class;
        final AbstractMultiConstructorInstantiator instantiator = new MockMultiConstructorInstantiator(clazz,
                                                                                                       constructorParameters);
        final Throwable expectedResult = new ObjectInstantiationException(B.class, "msg", null);

        // when
        final Throwable result = catchThrowable(instantiator::createFindingBestConstructor);

        // then
        assertThat(result)
            .usingRecursiveComparison()
            .isEqualTo(expectedResult);
    }

    static class MockMultiConstructorInstantiator extends AbstractMultiConstructorInstantiator {
        MockMultiConstructorInstantiator(final Class<?> clazz,
                                         final MultiValuedMap<Class<?>, ConstructorParameters> constructorParameters) {
            super(clazz, constructorParameters);
        }

        @Override
        public Object instantiate() {
            return null;
        }

        @Override
        public boolean canInstantiate() {
            return true;
        }

        @Override
        protected Object createObjectFromArgsConstructor(final Class<?>[] parameterTypes,
                                                         final Object[] parameters) throws ObjectInstantiationException {
            try {
                // Retrieve the declared constructor
                final Constructor<?> declaredConstructor = clazz.getDeclaredConstructor(parameterTypes);

                // Check if the constructor is accessible
                if (!declaredConstructor.canAccess(null)) {
                    // Use MethodHandles for private constructor access
                    MethodHandles.Lookup lookup = MethodHandles.privateLookupIn(clazz, MethodHandles.lookup());
                    MethodType methodType = MethodType.methodType(void.class, parameterTypes);
                    MethodHandle constructorHandle = lookup.findConstructor(clazz, methodType);

                    // Invoke the constructor with parameters
                    return constructorHandle.invokeWithArguments(parameters);
                } else {
                    // Use reflection for accessible constructors
                    return declaredConstructor.newInstance(parameters);
                }
            } catch (Throwable e) {
                // Catch all exceptions, including those from MethodHandles
                throw new ObjectInstantiationException(clazz, "Failed to instantiate object", e);
            }
        }

        
        @Override
        protected Object createObjectFromNoArgsConstructor(final Constructor<?> constructor) {
            return null;
        }

        @Override
        protected ObjectInstantiationException createObjectInstantiationException() {
            return new ObjectInstantiationException(B.class, "msg", null);
        }
    }

    @Data
    private class A {
        private final int b;
    }

    private class B {

        B(final int a) {
            throw new RuntimeException("eee");
        }
    }
}