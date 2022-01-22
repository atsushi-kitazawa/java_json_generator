package com.atushi.kitazawa;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ValueMapping {

    private static final Map<String, Object> typeToValue = new HashMap<>();
    private final static Map<String, Object> fieldToValue = new HashMap<>();
    private static final Set<String> collectionType = new HashSet<>();

    static {
        init();
    }

    private static void init() {
        typeToValue.put("java.lang.String", "aaa");
        typeToValue.put("java.lang.Boolean", false);
        typeToValue.put("java.lang.Integer", 10);
        typeToValue.put("java.lang.Long", 100l);
        typeToValue.put("java.lang.Double", 1.2345D);
        typeToValue.put("int", 1);
        typeToValue.put("long", 2l);
        typeToValue.put("java.util.Date", new Date(System.currentTimeMillis()));

        fieldToValue.put("id", "foobarId");

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

    public static Object getValue(String fieldName, String typeName) {
        if(fieldToValue.keySet().contains(fieldName)) {
            return fieldToValue.get(fieldName);
        }

        return typeToValue.get(typeName);
    }

    public static boolean canMappingType(String type) {
        return typeToValue.keySet().contains(type);
    }
}
