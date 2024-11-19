package artefact.classes;

import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

@TestInstance(Lifecycle.PER_CLASS)
public class Constructors_First_Throws_Exception {
    private Constructors_First_Throws_Exception() {
        throw new NullPointerException("test");
    }

    private Constructors_First_Throws_Exception(final Object o) { }
}