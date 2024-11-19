package com.java.pojo.internal.instantiator;


import org.apache.commons.collections4.MultiValuedMap;
import com.java.pojo.api.ConstructorParameters;

import java.util.Random;

class EnumInstantiator extends AbstractObjectInstantiator {

    EnumInstantiator(final Class<?> clazz,
                     final MultiValuedMap<Class<?>, ConstructorParameters> constructorParameters) {
        super(clazz, constructorParameters);
    }

    @Override
    public Object instantiate() {
        final Object[] enumConstants = clazz.getEnumConstants();
        final int length = enumConstants.length;

        if (length != 0) {
            final int random = new Random().nextInt(length);
            return enumConstants[random];
        }
        return null;
    }

    @Override
    public boolean canInstantiate() {
        return clazz.isEnum();
    }
}
