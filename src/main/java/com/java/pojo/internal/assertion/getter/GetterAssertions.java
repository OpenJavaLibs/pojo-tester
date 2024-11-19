package com.java.pojo.internal.assertion.getter;

import com.java.pojo.internal.GetOrSetValueException;
import com.java.pojo.internal.utils.FieldUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Objects;

public class GetterAssertions {

	private final Object objectUnderAssert;
	private final Class<?> classUnderTest;

	public GetterAssertions(final Object objectUnderAssert) {
		this.objectUnderAssert = objectUnderAssert;
		this.classUnderTest = objectUnderAssert.getClass();
	}

	public void willGetValueFromField(final Method getter, final Field field) {
		try {
			// Validate that the getter matches the field
			if (!getter.getName().toLowerCase().contains(field.getName().toLowerCase())) {
				checkResult(false, new GetterAssertionError(classUnderTest, field,
						"Getter does not match field: " + field.getName(), null));
			}

			// Ensure the getter is accessible
			if (!getter.canAccess(objectUnderAssert)) {
				getter.setAccessible(true);
			}
			final Object valueFromGetter = getter.invoke(objectUnderAssert);

			// Ensure the field is accessible
			if (!field.canAccess(objectUnderAssert)) {
				field.setAccessible(true);
			}
			final Object value = FieldUtils.getValue(objectUnderAssert, field);

			// Compare values
			final boolean result = Objects.deepEquals(value, valueFromGetter);
			checkResult(result, new GetterAssertionError(classUnderTest, field, valueFromGetter, value));
		} catch (IllegalAccessException | InvocationTargetException e) {
			throw new GetOrSetValueException(field.getName(), classUnderTest, e);
		}
	}

	private void checkResult(final boolean pass, final GetterAssertionError errorToThrow) {
		if (!pass) {
			throw errorToThrow;
		}
	}
}
