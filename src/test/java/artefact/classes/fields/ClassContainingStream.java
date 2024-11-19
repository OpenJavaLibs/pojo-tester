package artefact.classes.fields;


import java.util.stream.Stream;

import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

@TestInstance(Lifecycle.PER_CLASS)
public class ClassContainingStream {

    private final Stream<Integer> stream_Integer = Stream.of(1);
    private Stream<String> stream_String;
    private Stream<Object> stream_Object;
    private Stream<A> stream_A;
    private Stream stream;
    private A a;

    private class A {}
}
