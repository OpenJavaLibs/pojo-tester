package artefact.classes.fields.collections.iterators;


import java.util.Iterator;

import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

@TestInstance(Lifecycle.PER_CLASS)
public class Iterators {

    Iterator iterator;
    Iterable iterable;
}
