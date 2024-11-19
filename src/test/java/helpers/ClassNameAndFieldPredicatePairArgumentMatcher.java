package helpers;

import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mockito.ArgumentMatcher;
import com.java.pojo.api.ClassAndFieldPredicatePair;

@TestInstance(Lifecycle.PER_CLASS)
class ClassNameAndFieldPredicatePairArgumentMatcher implements ArgumentMatcher<ClassAndFieldPredicatePair> {
    private final String className;
    private final String fieldName;

    public ClassNameAndFieldPredicatePairArgumentMatcher(final String className, final String fieldName) {
        this.className = className;
        this.fieldName = fieldName;
    }

    @Override
    public boolean matches(final ClassAndFieldPredicatePair argument) {
        final boolean classesMatches = argument.getClazz()
                                               .getName()
                                               .equals(className);

        final boolean predicateMatches = argument.getFieldsPredicate()
                                                 .test(fieldName);
        return classesMatches && predicateMatches;
    }
}
