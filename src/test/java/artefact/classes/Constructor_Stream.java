package artefact.classes;

import java.util.stream.Stream;

import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

@TestInstance(Lifecycle.PER_CLASS)
public class Constructor_Stream {

    private final Stream stream;

    public Constructor_Stream(final Stream stream) {
        this.stream = stream;
    }
}
