package artefact.classes.fields.collections.map;


import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

@TestInstance(Lifecycle.PER_CLASS)
public class Maps {

    Hashtable hashtable;
    LinkedHashMap linkedHashMap;
    Map map;
    SortedMap sortedMap;
    TreeMap treeMap;
    HashMap hashMap;
}
