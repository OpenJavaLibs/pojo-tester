package com.java.pojo.internal.instantiator;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Stream;

import org.apache.commons.collections4.MultiValuedMap;

import com.java.pojo.api.ConstructorParameters;

import lombok.extern.slf4j.Slf4j;

@Slf4j
class UserDefinedConstructorInstantiator extends AbstractObjectInstantiator {


    UserDefinedConstructorInstantiator(final Class<?> clazz,
                                       final MultiValuedMap<Class<?>, ConstructorParameters> constructorParameters) {
        super(clazz, constructorParameters);
    }

    @Override
    public Object instantiate() {
        return constructorParameters.get(clazz)
                                    .stream()
                                    .map(this::createObjectUsingConstructorParameters)
                                    .filter(Objects::nonNull)
                                    .findAny()
                                    .orElseThrow(this::createObjectInstantiationException);
    }

    @Override
    public boolean canInstantiate() {
        return userDefinedConstructorParameters() && !qualifiesForProxy(clazz);
    }

    private boolean userDefinedConstructorParameters() {
        return constructorParameters.containsKey(clazz);
    }

    private boolean qualifiesForProxy(final Class<?> clazz) {
        return clazz.isInterface() || clazz.isAnnotation() || Modifier.isAbstract(clazz.getModifiers());
    }

    private ObjectInstantiationException createObjectInstantiationException() {
        return new ObjectInstantiationException(clazz,
                                                "Could not instantiate object by any user defined constructor types and parameters.");
    }

    private Object createObjectUsingConstructorParametersOld(final ConstructorParameters constructorParameters) {
        try {
            Class<?>[] constructorParametersTypes = constructorParameters.getParametersTypes();
            Object[] arguments = constructorParameters.getParameters();

            if (isInnerClass()) {
                constructorParametersTypes = putEnclosingClassAsFirstParameterType(clazz.getEnclosingClass(),
                                                                                   constructorParametersTypes);
                final Object enclosingClassInstance = instantiateEnclosingClass();
                arguments = putEnclosingClassInstanceAsFirstParameter(enclosingClassInstance, arguments);
            }

            // Use MethodHandles for private constructor access
            MethodHandles.Lookup lookup = MethodHandles.privateLookupIn(clazz, MethodHandles.lookup());
            MethodType methodType = MethodType.methodType(void.class, constructorParametersTypes);
            MethodHandle constructorHandle = lookup.findConstructor(clazz, methodType);
            return constructorHandle.invokeWithArguments(arguments);
        } catch (Throwable e) {
            log.debug("Exception:", e);
            return null;
        }
    }
    
    private Object createObjectUsingConstructorParameters(final ConstructorParameters constructorParameters) {
        try {
            // Check if the class has constructors
            Constructor<?>[] constructors = clazz.getDeclaredConstructors();
            if (constructors.length == 0) {
                throw new ObjectInstantiationException(clazz, "Class has no constructors.");
            }

            // Attempt to find a matching constructor
            Class<?>[] constructorParametersTypes = constructorParameters.getParametersTypes();
            Object[] arguments = constructorParameters.getParameters();

            if (isInnerClass()) {
                constructorParametersTypes = putEnclosingClassAsFirstParameterType(clazz.getEnclosingClass(),
                                                                                   constructorParametersTypes);
                final Object enclosingClassInstance = instantiateEnclosingClass();
                arguments = putEnclosingClassInstanceAsFirstParameter(enclosingClassInstance, arguments);
            }

            // Use MethodHandles to attempt to access a matching constructor
            try {
                MethodHandles.Lookup lookup = MethodHandles.privateLookupIn(clazz, MethodHandles.lookup());
                MethodType methodType = MethodType.methodType(void.class, constructorParametersTypes);
                MethodHandle constructorHandle = lookup.findConstructor(clazz, methodType);
                return constructorHandle.invokeWithArguments(arguments);
            } catch (NoSuchMethodException | IllegalAccessException e) {
                log.warn("No matching accessible constructor found for class: {}. Falling back to default constructor.", clazz.getName());
            }

            // Fallback to default constructor
            Constructor<?> defaultConstructor = clazz.getDeclaredConstructor();
            if (!defaultConstructor.canAccess(null)) {
                defaultConstructor.setAccessible(true);
            }
            return defaultConstructor.newInstance();
        } catch (NoSuchMethodException e) {
            throw new ObjectInstantiationException(clazz, "No accessible constructors, including default constructor.", e);
        } catch (Throwable e) {
            log.debug("Exception while creating object for class: {}", clazz.getName(), e);
            return null;
        }
    }



    private Object instantiateEnclosingClass() {
    	final Class<?> enclosingClass = clazz.getEnclosingClass();
        return Instantiable.forClass(enclosingClass, constructorParameters)
                           .instantiate();
    }

    private Object[] putEnclosingClassInstanceAsFirstParameter(final Object enclosingClassInstance,
                                                               final Object[] arguments) {
        return Stream.concat(Stream.of(enclosingClassInstance), Arrays.stream(arguments))
                     .toArray(Object[]::new);
    }

    private Class[] putEnclosingClassAsFirstParameterType(final Class<?> enclosingClass,
                                                          final Class<?>[] constructorParametersTypes) {
        return Stream.concat(Stream.of(enclosingClass), Arrays.stream(constructorParametersTypes))
                     .toArray(Class[]::new);
    }

    private boolean isInnerClass() {
        return clazz.getEnclosingClass() != null && !Modifier.isStatic(clazz.getModifiers());
    }

}
