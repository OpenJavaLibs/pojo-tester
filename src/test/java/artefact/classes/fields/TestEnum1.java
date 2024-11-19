package artefact.classes.fields;

import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

@TestInstance(Lifecycle.PER_CLASS)
public enum TestEnum1 {
    ENUM1,
    ENUM2,
}
