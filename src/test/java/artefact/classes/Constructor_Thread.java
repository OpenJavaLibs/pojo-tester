package artefact.classes;

import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

@TestInstance(Lifecycle.PER_CLASS)
public class Constructor_Thread {

    private final Thread thread;

    public Constructor_Thread(final Thread thread) {
        this.thread = thread;
    }
}
