package com.java.pojo.api.assertion;

import lombok.Data;
import org.junit.jupiter.api.Test;
import com.java.pojo.api.ClassAndFieldPredicatePair;
import com.java.pojo.internal.tester.EqualsTester;
import com.java.pojo.internal.utils.CollectionUtils;

import static org.mockito.Mockito.*;
import static org.powermock.reflect.Whitebox.setInternalState;


class SingleClassAssetionTest {

    @Test
    void Should_Test_Against_Each_Tester() {
        // given
        final ClassAndFieldPredicatePair classAndFieldPredicatePair = new ClassAndFieldPredicatePair(A.class);
        final ClassAndFieldPredicatePair[] classAndFieldPredicatePairs = {classAndFieldPredicatePair};
        final SingleClassAssertion singleClassAssertion = new SingleClassAssertion(classAndFieldPredicatePair,
                                                                                   classAndFieldPredicatePairs);
        final EqualsTester equalsTester1 = mock(EqualsTester.class);
        final EqualsTester equalsTester2 = mock(EqualsTester.class);
        setInternalState(singleClassAssertion, "testers", CollectionUtils.asSet(equalsTester1, equalsTester2));

        // when
        singleClassAssertion.runAssertions();

        // then
        verify(equalsTester1, only()).test(classAndFieldPredicatePair, classAndFieldPredicatePairs);
        verify(equalsTester2, only()).test(classAndFieldPredicatePair, classAndFieldPredicatePairs);
    }

    @Data
    private class A {
        private int a;
    }

}
