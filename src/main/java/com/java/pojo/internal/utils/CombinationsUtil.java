package com.java.pojo.internal.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CombinationsUtil {

    // Method to generate all subsets (combinations) of fields
    public static List<List<Field>> combinations(final List<Field> fields) {
        List<List<Field>> result = new ArrayList<>();
        generateCombinations(fields, 0, new ArrayList<>(), result);
        return result.stream()
                     .filter(CombinationsUtil::excludeEmptySet)  // Filter out empty sets
                     .collect(Collectors.toList());
    }

    // Recursive method to generate all combinations
    private static void generateCombinations(List<Field> fields, int index, List<Field> current, List<List<Field>> result) {
        if (index == fields.size()) {
            result.add(new ArrayList<>(current)); // Add the current subset
            return;
        }

        // Include the current field
        current.add(fields.get(index));
        generateCombinations(fields, index + 1, current, result);

        // Exclude the current field (backtrack)
        current.remove(current.size() - 1);
        generateCombinations(fields, index + 1, current, result);
    }
    
    private static boolean excludeEmptySet(final List<Field> fields) {
        return !fields.isEmpty();
    }
}
