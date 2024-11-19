package com.java.pojo.internal.assertion.constructor;


import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class ConstructorAssertions {

    private final Constructor<?> constructorUnderAssert;
    private final Class<?> classUnderTest;

    public ConstructorAssertions(final Constructor<?> constructorUnderAssert) {
        this.constructorUnderAssert = constructorUnderAssert;
        this.classUnderTest = constructorUnderAssert.getDeclaringClass();
    }

    public void willInstantiateClassUsing(final Object... constructorParameters) {
        try {
        	// Check if the constructor is private
            if (!constructorUnderAssert.canAccess(null)) {
                // Use MethodHandles to access the constructor
                MethodHandles.Lookup lookup = MethodHandles.privateLookupIn(classUnderTest, MethodHandles.lookup());
                MethodType methodType = MethodType.methodType(void.class, constructorUnderAssert.getParameterTypes());
                lookup.findConstructor(classUnderTest, methodType).invokeWithArguments(constructorParameters);
            } else {
                // Use reflection for accessible constructors
                constructorUnderAssert.newInstance(constructorParameters);
            }
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new ConstructorAssertionError(classUnderTest, constructorUnderAssert, constructorParameters, e);
        } catch (Throwable e) {
        	throw new ConstructorAssertionError(classUnderTest, constructorUnderAssert, constructorParameters, new ReflectiveOperationException(e));
		}

    }

}
