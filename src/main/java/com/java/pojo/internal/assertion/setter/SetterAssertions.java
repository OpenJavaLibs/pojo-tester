package com.java.pojo.internal.assertion.setter;

import com.java.pojo.internal.GetOrSetValueException;
import com.java.pojo.internal.utils.FieldUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Objects;

public class SetterAssertions {

	private final Object objectUnderAssert;
	private final Class<?> classUnderTest;

	public SetterAssertions(final Object objectUnderAssert) {
		this.objectUnderAssert = objectUnderAssert;
		this.classUnderTest = objectUnderAssert.getClass();
	}

	public void willSetValueOnField(final Method setter, final Field field, final Object expectedValue) {
		try {
			// Validate that the setter matches the field
			if (!setter.getName().toLowerCase().contains(field.getName().toLowerCase())) {
				checkResult(false, new SetterAssertionError(classUnderTest, field, expectedValue,
						"Setter does not match field: " + field.getName()));
			}

			// Ensure the setter is accessible
			if (!setter.canAccess(objectUnderAssert)) {
				setter.setAccessible(true);
			}

			// Invoke the setter
			setter.invoke(objectUnderAssert, expectedValue);

			// Ensure the field is accessible
			if (!field.canAccess(objectUnderAssert)) {
				field.setAccessible(true);
			}

			// Get the field value
			final Object value = FieldUtils.getValue(objectUnderAssert, field);

			// Compare values
			final boolean result = Objects.deepEquals(value, expectedValue);
			checkResult(result, new SetterAssertionError(classUnderTest, field, expectedValue, value));
		} catch (IllegalAccessException | InvocationTargetException e) {
			throw new GetOrSetValueException(field.getName(), classUnderTest, e);
		}
	}

	private void checkResult(final boolean pass, final SetterAssertionError errorToThrow) {
		if (!pass) {
			throw errorToThrow;
		}
	}
}
