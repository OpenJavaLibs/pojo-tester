package com.java.pojo.internal.instantiator;


import org.apache.commons.collections4.MultiValuedMap;
import com.java.pojo.api.ConstructorParameters;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public final class Instantiable {

    private static final List<Class<? extends AbstractObjectInstantiator>> INSTANTIATORS;

    static {
        INSTANTIATORS = new LinkedList<>();
        INSTANTIATORS.add(UserDefinedConstructorInstantiator.class);
        INSTANTIATORS.add(JavaTypeInstantiator.class);
        INSTANTIATORS.add(CollectionInstantiator.class);
        INSTANTIATORS.add(DefaultConstructorInstantiator.class);
        INSTANTIATORS.add(EnumInstantiator.class);
        INSTANTIATORS.add(ArrayInstantiator.class);
        INSTANTIATORS.add(ProxyInstantiator.class);
        INSTANTIATORS.add(BestConstructorInstantiator.class);
    }

    private Instantiable() {
    }

    static Object[] instantiateClasses(final Class<?>[] classes,
                                       final MultiValuedMap<Class<?>, ConstructorParameters> constructorParameters) {
        return Arrays.stream(classes)
                     .map(clazz -> Instantiable.forClass(clazz, constructorParameters))
                     .map(AbstractObjectInstantiator::instantiate)
                     .toArray();
    }

    static AbstractObjectInstantiator forClass(final Class<?> clazz,
                                               final MultiValuedMap<Class<?>, ConstructorParameters> constructorParameters) {
        return instantiateInstantiators(clazz, constructorParameters).stream()
                                                                     .filter(AbstractObjectInstantiator::canInstantiate)
                                                                     .findAny()
                                                                     .get();
    }

    private static List<AbstractObjectInstantiator> instantiateInstantiators(
            final Class<?> clazz,
            final MultiValuedMap<Class<?>, ConstructorParameters> constructorParameters) {
        final List<AbstractObjectInstantiator> instantiators = new ArrayList<>();
        try {
            for (final Class<? extends AbstractObjectInstantiator> instantiator : INSTANTIATORS) {
                Constructor<? extends AbstractObjectInstantiator> constructor = instantiator.getDeclaredConstructor(Class.class, MultiValuedMap.class);

                if (!constructor.canAccess(null)) {
                    // Use MethodHandles for private constructor access
                    MethodHandles.Lookup lookup = MethodHandles.privateLookupIn(instantiator, MethodHandles.lookup());
                    MethodHandle constructorHandle = lookup.findConstructor(instantiator,
                            MethodType.methodType(void.class, Class.class, MultiValuedMap.class));
                    AbstractObjectInstantiator abstractObjectInstantiator = (AbstractObjectInstantiator) constructorHandle.invoke(clazz, constructorParameters);
                    instantiators.add(abstractObjectInstantiator);
                } else {
                    // Use reflection for accessible constructors
                    instantiators.add(constructor.newInstance(clazz, constructorParameters));
                }
            }
        } catch (final Throwable e) {
            throw new RuntimeException("Cannot load instantiators from com.java.pojo.internal.instantiator package.", e);
        }
        return instantiators;
    }


}
