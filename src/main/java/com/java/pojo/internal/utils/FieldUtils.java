package com.java.pojo.internal.utils;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import org.paukov.combinatorics3.Generator;

import com.java.pojo.internal.GetOrSetValueException;

public final class FieldUtils {

	private FieldUtils() {
	}

	public static List<Field> getAllFields(final Class<?> clazz) {
		return Arrays.stream(clazz.getDeclaredFields()).filter(FieldUtils::isNotSynthetic)
				.filter(FieldUtils::isNotStatic).toList();
	}

	public static List<Field> getAllFieldsExcluding(final Class<?> clazz, final List<String> excludedFields) {
		return getAllFields(clazz).stream().filter(field -> doesNotContain(field, excludedFields)).toList();
	}

	public static List<List<Field>> permutations(final List<Field> fields) {
		// Use the Combinatorics3 library to generate permutations
		return Generator.subset(fields).simple() // Generates simple permutations
				.stream().filter(FieldUtils::excludeEmptySet) // Filter out empty sets (optional)
				.toList();
	}

	public static List<String> getAllFieldNames(final Class<?> clazz) {
		return getAllFields(clazz).stream().map(Field::getName).toList();
	}

	// Method to get the value of a private field using MethodHandles
	public static Object getFieldValue21(Object targetObject, Field field) {
		
		try {
			if(null == targetObject) {
				return field.get(targetObject);
			}
			// Get the class of the object
			Class<?> clazz = targetObject.getClass();

			// Create a VarHandle to access the private field
			VarHandle varHandle = MethodHandles.privateLookupIn(clazz, MethodHandles.lookup()).findVarHandle(clazz, field.getName(),
					field.getType());

			// Return the value of the field for the given object
			return varHandle.get(targetObject);
		} catch (NoSuchFieldException | IllegalAccessException e) {
			throw new GetOrSetValueException(field.getName(), targetObject.getClass(), e);
		}

	}

	// Method to set the value of a private or final field using MethodHandles
	public static void setFieldValue21(Object targetObject, Field field, Object newValue) {
		try {
			if(null == targetObject) {
				field.set(targetObject, newValue);
				return;
			}
			
			// Return if someone tries to modify a final field. 
			if(Modifier.isFinal(field.getModifiers())) {
				return;
			}
			
			// Get the class of the object
			Class<?> clazz = targetObject.getClass();

			// Create a VarHandle to access the private or final field
			VarHandle varHandle = MethodHandles.privateLookupIn(clazz, MethodHandles.lookup()).findVarHandle(clazz, field.getName(),
					field.getType());

			// Set the value of the field for the given object
			varHandle.set(targetObject, newValue);
		} catch (NoSuchFieldException | IllegalAccessException e) {
			throw new GetOrSetValueException(field.getName(), targetObject.getClass(), e);
		}
	}

	public static Object getValue(final Object targetObject, final Field field) {
		try {
			return getFieldValue21(targetObject, field);
		} catch (final SecurityException e) {
			throw new GetOrSetValueException(field.getName(), targetObject.getClass(), e);
		}
	}

	public static void setValue(final Object targetObject, final Field field, final Object value) {
		try {
			setFieldValue21(targetObject, field, value);
		} catch (final SecurityException e) {
			throw new GetOrSetValueException(field.getName(), targetObject.getClass(), e);
		}
	}

	public static List<Field> getFields(final Class<?> testedClass, final Predicate<String> predicate) {
		return getAllFields(testedClass).stream().filter(eachField -> predicate.test(eachField.getName())).toList();
	}

	public static boolean isFinal(final Field field) {
		final int fieldModifiers = field.getModifiers();
		return Modifier.isFinal(fieldModifiers);
	}

	public static List<Field> getSpecifiedFields(final Class<?> clazz, final List<String> names) {
		return names.stream().map(name -> getField(clazz, name)).toList();
	}

	private static boolean excludeEmptySet(final List<Field> fields) {
		return !fields.isEmpty();
	}

	private static boolean isNotSynthetic(final Field field) {
		return !field.isSynthetic();
	}

	private static boolean isNotStatic(final Field field) {
		return !Modifier.isStatic(field.getModifiers());
	}

	private static boolean doesNotContain(final Field field, final List<String> excludedFields) {
		return !excludedFields.contains(field.getName());
	}

	private static Field getField(final Class<?> clazz, final String name) {
		try {
			return clazz.getDeclaredField(name);
		} catch (final java.lang.NoSuchFieldException e) {
			throw new GetOrSetValueException(name, clazz, e);
		}
	}
}
