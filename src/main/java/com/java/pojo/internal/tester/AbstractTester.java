package com.java.pojo.internal.tester;

import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import com.java.pojo.api.ClassAndFieldPredicatePair;
import com.java.pojo.api.ConstructorParameters;
import com.java.pojo.api.FieldPredicate;
import com.java.pojo.internal.assertion.TestAssertions;
import com.java.pojo.internal.field.AbstractFieldValueChanger;
import com.java.pojo.internal.field.DefaultFieldValueChanger;
import com.java.pojo.internal.instantiator.ObjectGenerator;
import com.java.pojo.internal.utils.Permutator;
import com.java.pojo.internal.utils.ThoroughFieldPermutator;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;


public abstract class AbstractTester {

    final TestAssertions testAssertions = new TestAssertions();
    ObjectGenerator objectGenerator;
    private MultiValuedMap<Class<?>, ConstructorParameters> constructorParameters = new ArrayListValuedHashMap<>();
    private AbstractFieldValueChanger fieldValuesChanger = DefaultFieldValueChanger.INSTANCE;
    private Permutator permutator = new ThoroughFieldPermutator();

    public AbstractTester() {
        this(DefaultFieldValueChanger.INSTANCE);
    }

    public AbstractTester(final AbstractFieldValueChanger abstractFieldValueChanger) {
        objectGenerator = new ObjectGenerator(abstractFieldValueChanger, constructorParameters, permutator);
    }

    public void test(final Class<?> clazz) {
        final Predicate<String> predicateAcceptingAllFields = FieldPredicate.includeAllFields(clazz);
        test(clazz, predicateAcceptingAllFields);
    }

    public void test(final Class<?> clazz, final Predicate<String> fieldPredicate) {
        final ClassAndFieldPredicatePair classAndFieldPredicatePair = new ClassAndFieldPredicatePair(clazz,
                                                                                                     fieldPredicate);
        test(classAndFieldPredicatePair);
    }

    public abstract void test(final ClassAndFieldPredicatePair baseClassAndFieldPredicatePair, final ClassAndFieldPredicatePair... classAndFieldPredicatePairs);

    public void testAll(final Class... classes) {
        final ClassAndFieldPredicatePair[] classesAndFieldPredicatesPairs = Arrays.stream(classes)
                                                                                  .map(ClassAndFieldPredicatePair::new)
                                                                                  .toArray(ClassAndFieldPredicatePair[]::new);
        testAll(classesAndFieldPredicatesPairs);

    }

    public void testAll(final ClassAndFieldPredicatePair... classesAndFieldPredicatesPairs) {
        final List<ClassAndFieldPredicatePair> classAndFieldPredicatePairs = Arrays.asList(
                classesAndFieldPredicatesPairs);
        classAndFieldPredicatePairs.forEach(base -> test(base, classesAndFieldPredicatesPairs));
    }

    public void setFieldValuesChanger(final AbstractFieldValueChanger fieldValuesChanger) {
        this.fieldValuesChanger = fieldValuesChanger;
        objectGenerator = new ObjectGenerator(fieldValuesChanger, constructorParameters, permutator);
    }

    public void setUserDefinedConstructors(final MultiValuedMap<Class<?>, ConstructorParameters> constructorParameters) {
        this.constructorParameters = constructorParameters;
        objectGenerator = new ObjectGenerator(fieldValuesChanger, constructorParameters, permutator);
    }

    @Override
    public boolean equals(final Object otherObject) {
        if (this == otherObject) {
            return true;
        }

        if (otherObject == null || getClass() != otherObject.getClass()) {
            return false;
        }

        final AbstractTester that = (AbstractTester) otherObject;

        return new EqualsBuilder().append(objectGenerator, that.objectGenerator)
                                  .append(testAssertions, that.testAssertions)
                                  .append(constructorParameters, that.constructorParameters)
                                  .append(fieldValuesChanger, that.fieldValuesChanger)
                                  .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(objectGenerator)
                                    .append(testAssertions)
                                    .append(constructorParameters)
                                    .append(fieldValuesChanger)
                                    .toHashCode();
    }

    protected MultiValuedMap<Class<?>, ConstructorParameters> getConstructorParameters() {
        return constructorParameters;
    }

    @Override
    public String toString() {
        return this.getClass()
                   .getSimpleName();
    }

    public void setPermutator(final Permutator permutator) {
        this.permutator = permutator;
    }
}
