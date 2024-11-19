package helpers;

import org.apache.commons.collections4.MultiValuedMap;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mockito.ArgumentMatcher;
import com.java.pojo.api.ConstructorParameters;

import java.util.Arrays;

@TestInstance(Lifecycle.PER_CLASS)
public class MultiValuedMapMatcher implements ArgumentMatcher<MultiValuedMap<Class<?>, ConstructorParameters>> {

    private final Class<?> expectedClass;
    private final ConstructorParameters expectedArguments;

    public MultiValuedMapMatcher(final Class<?> expectedClass, final ConstructorParameters expectedArguments) {
        this.expectedClass = expectedClass;
        this.expectedArguments = expectedArguments;
    }


    @Override
    public boolean matches(final MultiValuedMap<Class<?>, ConstructorParameters> argument) {
        if (!argument.containsKey(expectedClass)) {
            return false;
        }
        return argument.get(expectedClass)
                       .stream()
                       .map(actualArgument -> Arrays.equals(actualArgument.getParameters(),
                                                            expectedArguments.getParameters())
                               &&
                               Arrays.equals(actualArgument.getParametersTypes(),
                                             expectedArguments.getParametersTypes()))
                       .findAny()
                       .isPresent();
    }
}
