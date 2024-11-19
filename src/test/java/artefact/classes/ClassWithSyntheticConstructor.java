package artefact.classes;

import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

@TestInstance(Lifecycle.PER_CLASS)
public class ClassWithSyntheticConstructor {

    private ClassWithSyntheticConstructor(final String parameter) {
    }

    private static class Builder {

        public ClassWithSyntheticConstructor build() {
            return new ClassWithSyntheticConstructor("test");
        }
    }
}
