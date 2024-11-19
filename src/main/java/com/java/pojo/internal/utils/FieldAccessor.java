package com.java.pojo.internal.utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class FieldAccessor {

	// Method to get the value of a field using its getter method when given a Field
	// object
	public static Object getFieldValue(Object targetObject, Field field) throws SecurityException, ReflectiveOperationException{
		String fieldName = field.getName();

		// Handle boolean-specific getters using a new method
        if (field.getType() == boolean.class || field.getType() == Boolean.class) {
            return getBooleanFieldValue(targetObject, field);
        }

		// Convert field name to getter method convention
		String getterMethodName = "get" + capitalize(fieldName);

		// Get the class of the object
		Class<?> clazz = targetObject.getClass();

		// Find the getter method
		Method getterMethod = clazz.getMethod(getterMethodName);

		// Invoke the getter method and return the value
		return getterMethod.invoke(targetObject);

	}

	// Method to set the value of a field using its setter method when given a Field
	// object
	public static void setFieldValue(Object targetObject, Field field, Object newValue) throws NoSuchMethodException, SecurityException, IllegalAccessException, InvocationTargetException {
		String fieldName = field.getName();

		// Convert field name to setter method convention
		String setterMethodName = "set" + capitalize(fieldName);

		// Get the class of the object
		Class<?> clazz = targetObject.getClass();

		// Find the setter method with the appropriate parameter type
		Method setterMethod = clazz.getMethod(setterMethodName, field.getType());

		// Invoke the setter method to set the value
		setterMethod.invoke(targetObject, newValue);

	}

	   // Method to handle boolean-specific getters (isX(), hasX(), getX())
    private static Object getBooleanFieldValue(Object object, Field field) throws ReflectiveOperationException {
        String fieldName = field.getName();
        String booleanGetterIs = "is" + capitalize(fieldName);
        String booleanGetterHas = "has" + capitalize(fieldName);
        String getterMethodName = "get" + capitalize(fieldName);

        // Get the class of the object
        Class<?> clazz = object.getClass();
        Object a = getValue(object, clazz, booleanGetterIs, false);
        if ( null == a) {
        	a = getValue(object, clazz, booleanGetterHas, false);
        	if (null == a) {
        		return getValue(object, clazz, getterMethodName, true);
        	}
        }
        return a;
    }
    
    private static Object getValue(Object targetObject, Class<?> targetClass, String methodName, boolean throwNow) throws ReflectiveOperationException {
    	try {
            Method isMethod = targetClass.getMethod(methodName);
            return isMethod.invoke(targetObject);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
        	if(throwNow)
        		throw e;
        	return null;
        }
    }
    
	// Helper method to capitalize the first letter of the field name
	private static String capitalize(String fieldName) {
		return fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
	}

	/*
	 * public static void main(String[] args) { try { // Example usage with
	 * EntityClass com.java.pojo.issue187.EntityClass entity = new
	 * com.java.pojo.issue187.EntityClass();
	 * 
	 * // Using setFieldValue to set values setFieldValue(entity, "f1",
	 * "UpdatedValueForF1"); setFieldValue(entity, "f3", 123);
	 * 
	 * // Using getFieldValue to retrieve values Object f1Value =
	 * getFieldValue(entity, "f1"); Object f3Value = getFieldValue(entity, "f3");
	 * 
	 * System.out.println("f1: " + f1Value); System.out.println("f3: " + f3Value); }
	 * catch (Exception e) { e.printStackTrace(); } }
	 */
}
