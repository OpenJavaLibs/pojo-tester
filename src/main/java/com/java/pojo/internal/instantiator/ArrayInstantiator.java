package com.java.pojo.internal.instantiator;

import org.apache.commons.collections4.MultiValuedMap;
import com.java.pojo.api.ConstructorParameters;

import java.lang.reflect.Array;

class ArrayInstantiator extends AbstractObjectInstantiator {

    private static final int DEFAULT_ARRAY_LENGTH = 0;

    ArrayInstantiator(final Class<?> clazz,
                      final MultiValuedMap<Class<?>, ConstructorParameters> constructorParameters) {
        super(clazz, constructorParameters);
    }

    @Override
    public Object instantiate() {
        return Array.newInstance(clazz.getComponentType(), DEFAULT_ARRAY_LENGTH);
    }

    @Override
    public boolean canInstantiate() {
        return clazz.isArray();
    }
}
