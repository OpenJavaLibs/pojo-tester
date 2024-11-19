package com.java.pojo.internal.assertion.tostring;


import com.java.pojo.internal.assertion.AbstractAssertionError;

abstract class AbstractToStringAssertionError extends AbstractAssertionError {

    AbstractToStringAssertionError(final Class<?> testedCass) {
        super(testedCass);
    }

    @Override
    protected String getErrorPrefix() {
        return String.format("Class %s has bad 'toString' method implementation.", testedCass.getCanonicalName());
    }
}
