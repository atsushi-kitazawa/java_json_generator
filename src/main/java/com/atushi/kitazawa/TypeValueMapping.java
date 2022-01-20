package com.atushi.kitazawa;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class TypeValueMapping {
    
    private static final Map<String, Object> typeToValue = new HashMap<>();
    private static final Set<String> collectionType = new HashSet<>();

    static {
        init();
    }

    private static void init() {
        typeToValue.put("java.lang.String", "aaa");
        typeToValue.put("java.lang.Integer", 10);
        typeToValue.put("java.lang.Long", 100l);
        typeToValue.put("int", 1);
        typeToValue.put("long", 2l);

        collectionType.add("java.util.Map");
        collectionType.add("java.util.List");
        collectionType.add("java.util.Set");
    }

    public static Map<String, Object> getTypeToValue() {
        return Collections.unmodifiableMap(typeToValue);
    }

    public static Set<String> getCollectionType() {
        return Collections.unmodifiableSet(collectionType);
    }
}
