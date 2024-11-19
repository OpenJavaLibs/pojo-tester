package artefact.classes;

import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

@TestInstance(Lifecycle.PER_CLASS)
public class PrivateConstructor {

    private int a;
    private int b;
    private int c;
    private Object object;

    private PrivateConstructor() {
    }
}
