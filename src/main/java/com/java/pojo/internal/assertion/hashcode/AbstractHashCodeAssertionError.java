package com.java.pojo.internal.assertion.hashcode;


import com.java.pojo.internal.assertion.AbstractAssertionError;

public abstract class AbstractHashCodeAssertionError extends AbstractAssertionError {


    AbstractHashCodeAssertionError(final Class<?> testedCass) {
        super(testedCass);
    }

    @Override
    protected String getErrorPrefix() {
        return String.format("Class %s has bad 'hashCode' method implementation.", testedCass.getCanonicalName());
    }
}
