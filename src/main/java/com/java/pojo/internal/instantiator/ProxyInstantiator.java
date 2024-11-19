package com.java.pojo.internal.instantiator;


import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Proxy;

import org.apache.commons.collections4.MultiValuedMap;

import com.java.pojo.api.ConstructorParameters;

import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.implementation.SuperMethodCall;
import net.bytebuddy.matcher.ElementMatchers;

@Slf4j
class ProxyInstantiator extends AbstractMultiConstructorInstantiator {

	ProxyInstantiator(final Class<?> clazz,
                      final MultiValuedMap<Class<?>, ConstructorParameters> constructorParameters) {
        super(clazz, constructorParameters);
    }

    @Override
    public Object instantiate() {
        Object result = instantiateUsingUserParameters();
        if (result == null) {
            if (clazz.isAnnotation() || clazz.isInterface()) {
                result = proxyByJava();
            } else if (qualifiesForProxy(clazz)) {
            	result = createAbstractClassInstance();
            } else {
                result = createFindingBestConstructor();
            }
        }
        return result;
    }

    @Override
    public boolean canInstantiate() {
        return qualifiesForProxy(clazz);
    }
    
    private Object createAbstractClassInstance() {
    	try {
			return new ByteBuddy()
			        .subclass(clazz)
			        .method(ElementMatchers.isDeclaredBy(clazz))
			        .intercept(MethodDelegation.to(new MethodHandler()))
			        .method(ElementMatchers.not(ElementMatchers.isAbstract()))
			        .intercept(SuperMethodCall.INSTANCE)
			        .make()
			        .load(clazz.getClassLoader())
			        .getLoaded()
			        .getConstructor()
			        .newInstance();
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			log.error("Failed to create proxy for abstract class: {}", clazz.getName(), e);
            return null;
		}
    }

    private boolean qualifiesForProxy(final Class<?> clazz) {
        return clazz.isInterface() || clazz.isAnnotation() || Modifier.isAbstract(clazz.getModifiers());
    }
    
    private Object proxyByJava() {
        try {
            return Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, this::createInvocationHandler);
        } catch (IllegalArgumentException | UnsupportedOperationException e) {
            log.error("Failed to create proxy for interface/annotation: {}", clazz.getName(), e);
            return null;
        }
    }

    @Override
	protected Object createObjectFromArgsConstructor(final Class<?>[] parameterTypes, final Object[] parameters) {
	    try {
	        final Constructor<?> declaredConstructor = clazz.getDeclaredConstructor(parameterTypes);

	        if (!declaredConstructor.canAccess(null)) {
	            // Use MethodHandles for private constructor access
	            MethodHandles.Lookup lookup = MethodHandles.privateLookupIn(clazz, MethodHandles.lookup());
	            MethodType methodType = MethodType.methodType(void.class, parameterTypes);
	            MethodHandle constructorHandle = lookup.findConstructor(clazz, methodType);
	            return constructorHandle.invokeWithArguments(parameters);
	        } else {
	            // Use reflection for accessible constructors
	            return declaredConstructor.newInstance(parameters);
	        }
	    } catch (Throwable e) {
	        throw new ObjectInstantiationException(clazz, "Could not create object from args constructor",
	                parameterTypes, parameters, e);
	    }
	}

	@Override
	protected Object createObjectFromNoArgsConstructor(final Constructor<?> constructor) {
		try {
			if (!constructor.canAccess(null)) {
				// Use MethodHandles for private constructor access
				MethodHandles.Lookup lookup = MethodHandles.privateLookupIn(constructor.getDeclaringClass(),
						MethodHandles.lookup());
				MethodHandle constructorHandle = lookup.findConstructor(constructor.getDeclaringClass(),
						MethodType.methodType(void.class));
				return constructorHandle.invoke();
			} else {
				// Use reflection for accessible constructors
				return constructor.newInstance();
			}
		} catch (Throwable e) {
			log.debug("Exception:", e);
			// Ignore, we want to try all constructors
			// If all constructors fail, it will be handled by the caller
			return null;
		}
	}

    private Object createInvocationHandler(final Object proxy, final Method method, final Object[] args) {
        try {
            return method.invoke(proxy, args);
        } catch (IllegalAccessException | InvocationTargetException e) {
            log.debug("Failed to invoke method: {} on proxy: {}", method.getName(), clazz.getName(), e);
            final Class<?> returnType = method.getReturnType();

            if (returnType.equals(boolean.class) || returnType.equals(Boolean.class)) {
                return true;
            } else if (returnType.equals(String.class)) {
                return "string";
            } else if (returnType.equals(char.class) || returnType.equals(Character.class)) {
                return '\u0000'; // Default char
            } else if (returnType.equals(double.class) || returnType.equals(Double.class)) {
                return 0.0;
            } else if (returnType.equals(float.class) || returnType.equals(Float.class)) {
                return 0.0f;
            } else if (returnType.equals(void.class)) {
                return null; // void methods do not return a value
            } else if (returnType.isEnum()) {
                // For enums, return the first constant or null if no constants exist
                Object[] enumConstants = returnType.getEnumConstants();
                return enumConstants.length > 0 ? enumConstants[0] : null;
            } else {
                return 0; // Default for int, short, byte, long, and other primitives
            }
        }
    }


    @Override
    protected ObjectInstantiationException createObjectInstantiationException() {
        return new ObjectInstantiationException(clazz,
                                                "Class could not be created by any constructor (using ProxyInstantiator).");
    }
    
    public static class MethodHandler {
        public void intercept() {
            System.out.println("Abstract method intercepted!");
        }
    }

}
