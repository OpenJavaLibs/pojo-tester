package helpers;

import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mockito.ArgumentMatcher;
import com.java.pojo.api.ClassAndFieldPredicatePair;

@TestInstance(Lifecycle.PER_CLASS)
public class ClassAndFieldPredicatePairArgumentMatcher implements ArgumentMatcher<ClassAndFieldPredicatePair> {
    private final Class<?> clazz;
    private final String fieldName;

    public ClassAndFieldPredicatePairArgumentMatcher(final Class<?> clazz, final String fieldName) {
        this.clazz = clazz;
        this.fieldName = fieldName;
    }

    @Override
    public boolean matches(final ClassAndFieldPredicatePair argument) {
        final boolean classesMatches = argument.getClazz()
                                               .equals(clazz);

        final boolean predicateMatches = argument.getFieldsPredicate()
                                                 .test(fieldName);
        return classesMatches && predicateMatches;
    }
}
