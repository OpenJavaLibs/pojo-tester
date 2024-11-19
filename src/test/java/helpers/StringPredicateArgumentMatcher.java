package helpers;

import java.util.function.Predicate;

import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mockito.ArgumentMatcher;

@TestInstance(Lifecycle.PER_CLASS)
public class StringPredicateArgumentMatcher implements ArgumentMatcher<Predicate<String>> {

    @Override
    public boolean matches(final Predicate<String> argument) {
        return argument.test("a");
    }
}
