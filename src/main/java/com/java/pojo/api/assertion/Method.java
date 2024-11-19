package com.java.pojo.api.assertion;


import java.lang.reflect.InvocationTargetException;

import com.java.pojo.internal.tester.AbstractTester;
import com.java.pojo.internal.tester.ConstructorTester;
import com.java.pojo.internal.tester.EqualsTester;
import com.java.pojo.internal.tester.GetterTester;
import com.java.pojo.internal.tester.HashCodeTester;
import com.java.pojo.internal.tester.SetterTester;
import com.java.pojo.internal.tester.ToStringTester;

/**
 * Declares methods that can be tested using POJO-TESTER.
 * <p>
 * For more documentation, please refer <a href="http://pojo.pl">POJO-TESTER User Guide documentation</a>
 *
 * @author Piotr Joński
 * @since 0.1.0
 */
public enum Method {
    EQUALS(EqualsTester.class),
    HASH_CODE(HashCodeTester.class),
    SETTER(SetterTester.class),
    GETTER(GetterTester.class),
    TO_STRING(ToStringTester.class),
    CONSTRUCTOR(ConstructorTester.class);

    private final Class<? extends AbstractTester> testerClass;

    Method(final Class<? extends AbstractTester> tester) {
        this.testerClass = tester;
    }

    public AbstractTester getTester() {
        try {
            return testerClass.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
            throw new TesterInstantiationException(e);
        }
    }
}
