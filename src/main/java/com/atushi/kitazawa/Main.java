package com.atushi.kitazawa;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Main {

    private static final Map<String, Object> typeToValue = new HashMap<>();
    private static final Set<String> collectionType = new HashSet<>();

    static {
        init();
    }

    public static void main(String[] args) {
        doMain("JSON");
    }

    private static void doMain(String format) {
        A instance = new A();
        setValueToMember(instance);
        Marshaller m = MarshallerFactory.getMarshaller(format);
        System.out.println(m.marshal(instance));
    }

    public static <T> void setValueToMember(T instance) {
        Field[] fields = instance.getClass().getDeclaredFields();
        for (Field f : fields) {
            String type = f.getType().getName();
            if (isCollection(type)) {
                switch (type) {
                    case "java.util.List":
                        String paramType = ((ParameterizedType) f.getGenericType()).getActualTypeArguments()[0]
                                .getTypeName();
                        f.setAccessible(true);
                        try {
                            Object value = typeToValue.get(paramType);
                            f.set(instance, Arrays.asList(value, value, value));
                        } catch (IllegalAccessException e) {
                            System.err.println("failed to set " + f.getName());
                        }
                        break;
                    case "java.util.Set":
                        String paramType1 = ((ParameterizedType) f.getGenericType()).getActualTypeArguments()[0]
                                .getTypeName();
                        f.setAccessible(true);
                        try {
                            Object value = typeToValue.get(paramType1);
                            f.set(instance, new HashSet<>(Arrays.asList(value, value, value)));
                        } catch (IllegalAccessException e) {
                            System.err.println("failed to set " + f.getName());
                        }
                        break;
                    case "java.util.Map":
                        f.setAccessible(true);
                        try {
                            f.set(instance, Map.of(1, "value1", 2, "value2"));
                        } catch (IllegalAccessException e) {
                            System.err.println("failed to set " + f.getName());
                        }
                        break;
                    default:
                        System.out.println(f.getType() + ":" + f.getName());
                }
                continue;
            }

            if (typeToValue.keySet().contains(type)) {
                f.setAccessible(true);
                try {
                    f.set(instance, typeToValue.get(type));
                } catch (IllegalAccessException e) {
                    System.err.println("failed to set " + f.getName());
                }
            } else {
                try {
                    Object nestClassInstance = f.getType().getDeclaredConstructor().newInstance();
                    setValueToMember(nestClassInstance);
                    f.setAccessible(true);
                    f.set(instance, nestClassInstance);
                } catch (Exception e) {
                    System.err.println("not declared constructor. " + type);
                }
            }
        }
    }

    private static void init() {
        typeToValue.put("java.lang.String", "aaa");
        typeToValue.put("java.lang.Integer", 10);
        typeToValue.put("java.lang.Long", 100l);
        typeToValue.put("int", 1);

        collectionType.add("java.util.Map");
        collectionType.add("java.util.List");
        collectionType.add("java.util.Set");
    }

    private static boolean isCollection(String type) {
        return collectionType.contains(type);
    }
}
