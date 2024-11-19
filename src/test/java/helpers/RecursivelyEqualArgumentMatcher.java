package helpers;

import org.mockito.ArgumentMatcher;
import com.java.pojo.api.ClassAndFieldPredicatePair;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

@TestInstance(Lifecycle.PER_CLASS)
public class RecursivelyEqualArgumentMatcher implements ArgumentMatcher<ClassAndFieldPredicatePair> {
    private final ClassAndFieldPredicatePair expectedParameter;

    public RecursivelyEqualArgumentMatcher(final ClassAndFieldPredicatePair expectedParameter) {
        this.expectedParameter = expectedParameter;
    }

    @Override
    public boolean matches(final ClassAndFieldPredicatePair argument) {
        assertThat(argument).usingRecursiveComparison().isEqualTo(expectedParameter);
        return true;
    }
}
