package com.java.pojo.internal.utils;

class ClassLoadingException extends RuntimeException {

    ClassLoadingException(final String qualifiedClassName, final ClassNotFoundException cause) {
        super(createMessage(qualifiedClassName), cause);
    }

    private static String createMessage(final String qualifiedClassName) {
        return String.format("Unable to load class %s", qualifiedClassName);
    }
}
