package com.java.pojo.internal.instantiator;

import org.apache.commons.collections4.MultiValuedMap;
import com.java.pojo.api.ConstructorParameters;

abstract class AbstractObjectInstantiator {

    protected final Class<?> clazz;
    protected final MultiValuedMap<Class<?>, ConstructorParameters> constructorParameters;

    AbstractObjectInstantiator(final Class<?> clazz,
                               final MultiValuedMap<Class<?>, ConstructorParameters> constructorParameters) {
        this.clazz = clazz;
        this.constructorParameters = constructorParameters;
    }

    public abstract Object instantiate();

    public abstract boolean canInstantiate();
}
