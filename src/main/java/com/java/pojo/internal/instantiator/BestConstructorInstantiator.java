package com.java.pojo.internal.instantiator;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Constructor;

import org.apache.commons.collections4.MultiValuedMap;

import com.java.pojo.api.ConstructorParameters;

import lombok.extern.slf4j.Slf4j;

@Slf4j
class BestConstructorInstantiator extends AbstractMultiConstructorInstantiator {

	BestConstructorInstantiator(final Class<?> clazz,
			final MultiValuedMap<Class<?>, ConstructorParameters> constructorParameters) {
		super(clazz, constructorParameters);
	}

	@Override
	public Object instantiate() {
		Object result = instantiateUsingUserParameters();
		if (result == null) {
			result = createFindingBestConstructor();
		}
		return result;
	}

	@Override
	public boolean canInstantiate() {
		return true;
	}

	@Override
	protected ObjectInstantiationException createObjectInstantiationException() {
		return new ObjectInstantiationException(clazz,
				"Class could not be created by any constructor (using BestConstructorInstantiator).");
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
}
