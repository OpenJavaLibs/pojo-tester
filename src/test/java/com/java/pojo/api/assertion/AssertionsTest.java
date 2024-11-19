package com.java.pojo.api.assertion;

import lombok.Data;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import com.java.pojo.api.ClassAndFieldPredicatePair;
import com.java.pojo.api.DefaultPackageFilter;

import artefact.classes.packages.filter.next.D;
import artefact.classes.packages.filter.next.E;

import java.util.List;
import java.util.function.Predicate;

import static org.assertj.core.api.Assertions.assertThat;
import org.powermock.reflect.Whitebox;

class AssertionsTest {

	@Test
	void Should_Create_Expected_Single_Class_Assertion_Using_Class() {
		final Class<A> expectedClass = A.class;
		final SingleClassAssertion expectedResult = new SingleClassAssertion(
				new ClassAndFieldPredicatePair(expectedClass), new ClassAndFieldPredicatePair[] {});
		// when
		final AbstractAssertion result = Assertions.assertPojoMethodsFor(expectedClass);

		// then
	    assertThat(result)
	        .usingRecursiveComparison()
	        .withComparatorForFields((o1, o2) -> 0, "baseClassAndFieldPredicatePair.fieldsPredicate.predicate")
	        .isEqualTo(expectedResult);
	}

	@Test
	void Should_Create_Expected_Single_Class_Assertion_Using_Class_Name() throws ClassNotFoundException {
	    // given
	    final Class<A> expectedClass = A.class;
	    final String expectedClassName = expectedClass.getName();
	    final SingleClassAssertion expectedResult = new SingleClassAssertion(
	    		new ClassAndFieldPredicatePair(expectedClass), new ClassAndFieldPredicatePair[] {});

	    // when
	    final AbstractAssertion result = Assertions.assertPojoMethodsFor(expectedClassName);

	    // then
	    assertThat(result)
	        .usingRecursiveComparison()
	        .withComparatorForFields((o1, o2) -> 0, "baseClassAndFieldPredicatePair.fieldsPredicate.predicate")
	        .isEqualTo(expectedResult);
	}


	@Test
	void Should_Create_Expected_Single_Class_Assertion_Using_Class_Name_And_Field_Predicate() {
		// given
		final Class<A> expectedClass = A.class;
		final String fieldName = "a";
		final String expectedClassName = expectedClass.getName();
		final Predicate<String> predicate = name -> name.equals(fieldName);
		final SingleClassAssertion expectedResult = new SingleClassAssertion(
				new ClassAndFieldPredicatePair(expectedClass), new ClassAndFieldPredicatePair[] {});
		// when
		final AbstractAssertion result = Assertions.assertPojoMethodsFor(expectedClassName, predicate);

		// then
		assertThat(result).usingRecursiveComparison().isEqualTo(expectedResult);
	}

	@Test
	void Should_Create_Expected_Single_Class_Assertion_Using_Class_And_Field_Predicate() {
		// given
		final Class<A> expectedClass = A.class;
		final String fieldName = "a";
		final Predicate<String> predicate = name -> name.equals(fieldName);
		final SingleClassAssertion expectedResult = new SingleClassAssertion(
				new ClassAndFieldPredicatePair(expectedClass), new ClassAndFieldPredicatePair[] {});
		// when
		final AbstractAssertion result = Assertions.assertPojoMethodsFor(expectedClass, predicate);

		// then
		assertThat(result).usingRecursiveComparison().isEqualTo(expectedResult);
	}

	@Test
	void Should_Create_Expected_Single_Class_Assertion_Using_Class_And_Field_Predicates() {
		// given
		final ClassAndFieldPredicatePair[] classAndFieldPredicatePairs = { new ClassAndFieldPredicatePair(A.class) };
		final SingleClassAssertion expectedResult = new SingleClassAssertion(classAndFieldPredicatePairs[0],
				classAndFieldPredicatePairs);
		// when
		final AbstractAssertion result = Assertions.assertPojoMethodsFor(classAndFieldPredicatePairs[0],
				classAndFieldPredicatePairs[0]);
		// then
		assertThat(result).usingRecursiveComparison().isEqualTo(expectedResult);
	}

	@Test
	@Disabled
	void Should_Create_Expected_Multi_Class_Assertion_Using_Package() {
		// given
		final DefaultPackageFilter packageFilter = DefaultPackageFilter
				.forPackage("artefact.classes.packages.filter.next");

		// when
		final AbstractAssertion result = Assertions.assertPojoMethodsForAll(packageFilter);
		final List<ClassAndFieldPredicatePair> pairs = Whitebox.getInternalState(result, "classAndFieldPredicatePairs");

		// then
		assertThat(pairs).usingRecursiveFieldByFieldElementComparator().containsExactlyInAnyOrder(
				new ClassAndFieldPredicatePair(D.class), new ClassAndFieldPredicatePair(E.class));
	}

	@Test
	@Disabled
	void Should_Create_Expected_Multi_Class_Assertion_Using_Classes() {
		// given
		final Class<A> expectedClass1 = A.class;
		final Class<B> expectedClass2 = B.class;

		// when
		final AbstractAssertion result = Assertions.assertPojoMethodsForAll(expectedClass1, expectedClass2);
		final List<ClassAndFieldPredicatePair> pairs = Whitebox.getInternalState(result, "classAndFieldPredicatePairs");

		// then
		assertThat(pairs).usingRecursiveFieldByFieldElementComparator().containsExactlyInAnyOrder(
				new ClassAndFieldPredicatePair(A.class), new ClassAndFieldPredicatePair(B.class));
	}

	@Test
	@Disabled
	void Should_Create_Expected_Multi_Class_Assertion_Using_Classes_Names() {
		// given
		final String expectedClass1Name = A.class.getName();
		final String expectedClass2Name = B.class.getName();

		// when
		final AbstractAssertion result = Assertions.assertPojoMethodsForAll(expectedClass1Name, expectedClass2Name);
		final List<ClassAndFieldPredicatePair> pairs = Whitebox.getInternalState(result, "classAndFieldPredicatePairs");

		// then
		assertThat(pairs).usingRecursiveFieldByFieldElementComparator().containsExactlyInAnyOrder(
				new ClassAndFieldPredicatePair(A.class), new ClassAndFieldPredicatePair(B.class));
	}

	@Test
	@Disabled
	void Should_Create_Expected_Multi_Class_Assertion_Using_Class_And_Field_Predicate_Pairs() {
		// given
		final Class<A> expectedClass1 = A.class;
		final Class<B> expectedClass2 = B.class;
		final ClassAndFieldPredicatePair pair1 = new ClassAndFieldPredicatePair(expectedClass1);
		final ClassAndFieldPredicatePair pair2 = new ClassAndFieldPredicatePair(expectedClass2);

		// when
		final AbstractAssertion result = Assertions.assertPojoMethodsForAll(pair1, pair2, pair2);
		final List<ClassAndFieldPredicatePair> pairs = Whitebox.getInternalState(result, "classAndFieldPredicatePairs");

		// then
		assertThat(pairs).usingRecursiveFieldByFieldElementComparator().containsExactlyInAnyOrder(
				new ClassAndFieldPredicatePair(A.class), new ClassAndFieldPredicatePair(B.class),
				new ClassAndFieldPredicatePair(B.class));
	}

	@Data
	private class A {
		private int a;
	}

	@Data
	private class B {
		private int b;
	}

}
