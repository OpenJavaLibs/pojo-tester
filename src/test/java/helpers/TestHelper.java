package helpers;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

@TestInstance(Lifecycle.PER_CLASS)
public class TestHelper {

	public static List<Field> getAllFieldsExceptDummyJacocoField(final Class<?> clazz) {
		return Arrays.stream(clazz.getDeclaredFields()).filter(field -> !Modifier.isStatic(field.getModifiers()))
				.filter(field -> !field.getName().equals("$jacocoData")).collect(Collectors.toList());
	}

	public static String getDefaultDisplayName(final Object value) {
		return "------> " + String.valueOf(value) + " <------";
	}
}
