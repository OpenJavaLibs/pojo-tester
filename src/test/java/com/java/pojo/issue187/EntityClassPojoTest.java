package com.java.pojo.issue187;

import static com.java.pojo.api.assertion.Assertions.assertPojoMethodsFor;

import org.junit.jupiter.api.Test;

class EntityClassPojoTest {

	@Test
	void Should_Test_Entity_Class() {
		assertPojoMethodsFor(EntityClass.class).areWellImplemented();
	}

}
