package helpers;

import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mockito.ArgumentMatcher;
import com.java.pojo.api.ConstructorParameters;

import java.util.Arrays;
import java.util.Map;

@TestInstance(Lifecycle.PER_CLASS)
class MapMatcher implements ArgumentMatcher<Map<Class<?>, ConstructorParameters>> {

    private final Class<?> expectedClass;
    private final ConstructorParameters expectedArguments;

    public MapMatcher(final Class<?> expectedClass, final ConstructorParameters expectedArguments) {
        this.expectedClass = expectedClass;
        this.expectedArguments = expectedArguments;
    }


    @Override
    public boolean matches(final Map<Class<?>, ConstructorParameters> argument) {
        if (!argument.containsKey(expectedClass)) {
            return false;
        }
        final ConstructorParameters actualArguments = argument.get(expectedClass);
        return Arrays.equals(actualArguments.getParameters(), expectedArguments.getParameters()) &&
                Arrays.equals(actualArguments.getParametersTypes(), expectedArguments.getParametersTypes());
    }
}
