package com.java.pojo.internal.assertion.equals;


import com.java.pojo.internal.assertion.AbstractAssertionError;

public abstract class AbstractEqualsAssertionError extends AbstractAssertionError {


    AbstractEqualsAssertionError(final Class<?> testedCass) {
        super(testedCass);
    }

    @Override
    protected String getErrorPrefix() {
        return String.format("Class %s has bad 'equals' method implementation.", testedCass.getCanonicalName());
    }
}
