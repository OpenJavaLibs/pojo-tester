package com.java.pojo.api.assertion;

import helpers.ClassAndFieldPredicatePairArgumentMatcher;
import lombok.Data;
import org.junit.jupiter.api.Test;
import com.java.pojo.api.ClassAndFieldPredicatePair;
import com.java.pojo.internal.tester.EqualsTester;
import com.java.pojo.internal.utils.CollectionUtils;

import static org.mockito.Mockito.*;
import static org.powermock.reflect.Whitebox.setInternalState;


class MultiClassAssertionTest {

    @Test
    void Should_Test_Against_Each_Tester() {
        // given
        final ClassAndFieldPredicatePair caf = new ClassAndFieldPredicatePair(A.class);
        final MultiClassAssertion multiClassAssertion = new MultiClassAssertion(CollectionUtils.asList(caf));
        final EqualsTester equalsTester1 = mock(EqualsTester.class);
        final EqualsTester equalsTester2 = mock(EqualsTester.class);
        setInternalState(multiClassAssertion, "testers", CollectionUtils.asSet(equalsTester1, equalsTester2));

        // when
        multiClassAssertion.runAssertions();

        // then
        verify(equalsTester1, only()).testAll(argThat(new ClassAndFieldPredicatePairArgumentMatcher(A.class, "a")));
        verify(equalsTester2, only()).testAll(argThat(new ClassAndFieldPredicatePairArgumentMatcher(A.class, "a")));
    }

    @Data
    private class A {
        private int a;
    }
}
