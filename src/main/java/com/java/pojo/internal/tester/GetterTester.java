package com.java.pojo.internal.tester;


import com.java.pojo.api.ClassAndFieldPredicatePair;
import com.java.pojo.internal.field.AbstractFieldValueChanger;
import com.java.pojo.internal.utils.FieldUtils;
import com.java.pojo.internal.utils.MethodUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class GetterTester extends AbstractTester {

    public GetterTester() {
        super();
    }

    public GetterTester(final AbstractFieldValueChanger abstractFieldValueChanger) {
        super(abstractFieldValueChanger);
    }

    @Override
    public void test(final ClassAndFieldPredicatePair baseClassAndFieldPredicatePair,
                     final ClassAndFieldPredicatePair... classAndFieldPredicatePairs) {
        final Class testedClass = baseClassAndFieldPredicatePair.getClazz();
        final List<Field> fields = FieldUtils.getFields(testedClass,
                                                        baseClassAndFieldPredicatePair.getFieldsPredicate());
        final List<GetterAndFieldPair> getterAndFieldPairs = findGettersForFields(testedClass, fields);
        final Object instance = objectGenerator.createNewInstance(testedClass);

        IntStream.range(0, getterAndFieldPairs.size()).forEach(i -> testGetter(getterAndFieldPairs.get(i), instance, i));
    }

    private void testGetter(final GetterAndFieldPair eachPair, final Object instance, final int fieldIndex)) {
        final Method getter = eachPair.getGetter();
        final Field field = eachPair.getField();
        // Set a unique value for this field before invoking the getter. Using fieldIndex to
        // differentiate fields of the same type (e.g. two String fields), which prevents false
        // positives caused by copy-paste getter errors returning the wrong field.
        final Object uniqueValue = objectGenerator.createUniqueInstance(field.getType(), fieldIndex);
        FieldUtils.setValue(instance, field, uniqueValue);
        testAssertions.assertThatGetMethodFor(instance)
                      .willGetValueFromField(getter, field);
    }

    private List<GetterAndFieldPair> findGettersForFields(final Class<?> testedClass, final List<Field> fields) {
        return fields.stream()
                     .map(fieldName -> findSetterAndGetterPairForField(testedClass, fieldName))
                     .collect(Collectors.toList());
    }

    private GetterAndFieldPair findSetterAndGetterPairForField(final Class<?> testedClass, final Field field) {
        final Method getter = MethodUtils.findGetterFor(testedClass, field);
        return new GetterAndFieldPair(getter, field);
    }

    private class GetterAndFieldPair {
        private final Method getter;
        private final Field field;

        public GetterAndFieldPair(final Method getter, final Field field) {
            this.getter = getter;
            this.field = field;
        }

        public Method getGetter() {
            return getter;
        }

        public Field getField() {
            return field;
        }
    }
}
