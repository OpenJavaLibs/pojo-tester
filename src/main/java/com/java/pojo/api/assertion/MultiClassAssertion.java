package com.java.pojo.api.assertion;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.java.pojo.api.ClassAndFieldPredicatePair;

import java.util.List;


class MultiClassAssertion extends AbstractAssertion {

    private static final Logger LOGGER = LoggerFactory.getLogger(MultiClassAssertion.class);

    private final List<ClassAndFieldPredicatePair> classAndFieldPredicatePairs;

    MultiClassAssertion(final List<ClassAndFieldPredicatePair> classAndFieldPredicatePairs) {
        super();
        this.classAndFieldPredicatePairs = classAndFieldPredicatePairs;
    }

    @Override
    protected void runAssertions() {
        final ClassAndFieldPredicatePair[] classes = classAndFieldPredicatePairs.toArray(
                new ClassAndFieldPredicatePair[classAndFieldPredicatePairs.size()]);
        logTestersAndClasses(LOGGER, classes);
        testers.forEach(tester -> tester.testAll(classes));
    }

}
