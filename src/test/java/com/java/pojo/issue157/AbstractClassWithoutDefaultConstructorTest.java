package com.java.pojo.issue157;

import static com.java.pojo.api.assertion.Assertions.assertPojoMethodsFor;

import org.junit.jupiter.api.Test;

class AbstractClassWithoutDefaultConstructorTest {

	@Test
	void shouldTestOnlyGetterAndSetter() {
		// Test only GETTER and SETTER methods for AbstractClass
		assertPojoMethodsFor(ExtendAbstractClass.class).areWellImplemented();
	}
}
