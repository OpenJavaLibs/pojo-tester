package com.java.pojo.internal.assertion;


import com.java.pojo.internal.assertion.constructor.ConstructorAssertions;
import com.java.pojo.internal.assertion.equals.EqualAssertions;
import com.java.pojo.internal.assertion.getter.GetterAssertions;
import com.java.pojo.internal.assertion.hashcode.HashCodeAssertions;
import com.java.pojo.internal.assertion.setter.SetterAssertions;
import com.java.pojo.internal.assertion.tostring.ToStringAssertions;

import java.lang.reflect.Constructor;

public class TestAssertions {

    public EqualAssertions assertThatEqualsMethodFor(final Object objectUnderAssert) {
        return new EqualAssertions(objectUnderAssert);
    }

    public HashCodeAssertions assertThatHashCodeMethodFor(final Object objectUnderAssert) {
        return new HashCodeAssertions(objectUnderAssert);
    }

    public ToStringAssertions assertThatToStringMethodFor(final Object objectUnderAssert) {
        return new ToStringAssertions(objectUnderAssert);
    }

    public SetterAssertions assertThatSetMethodFor(final Object objectUnderAssert) {
        return new SetterAssertions(objectUnderAssert);
    }

    public GetterAssertions assertThatGetMethodFor(final Object objectUnderAssert) {
        return new GetterAssertions(objectUnderAssert);
    }

    public ConstructorAssertions assertThatConstructor(final Constructor<?> constructorUnderAssert) {
        return new ConstructorAssertions(constructorUnderAssert);
    }
}
