package artefact.classes;

import java.lang.reflect.Field;

import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

@TestInstance(Lifecycle.PER_CLASS)
public class Constructor_Field {

    private final Field field;

    public Constructor_Field(final Field field) {
        this.field = field;
    }
}
