package com.atushi.kitazawa;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
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
        doMain(args[0]);
    }

    private static void doMain(String format) {
        A instance = (A) InstanceFactory.getInstance(A.class);
        setValueToMember(instance);
        Marshaller m = MarshallerFactory.getMarshaller(format);
        System.out.println(m.marshal(instance));
    }

    public static <T> void setValueToMember(T instance) {
        Field[] fields = instance.getClass().getDeclaredFields();
        for (Field f : fields) {
            String type = f.getType().getName();
            if (isCollection(type)) {
                Type[] paramType = ((ParameterizedType) f.getGenericType()).getActualTypeArguments();
                switch (type) {
                    case "java.util.List":
                        if (typeToValue.keySet().contains(paramType[0].getTypeName())) {
                            f.setAccessible(true);
                            try {
                                Object value = typeToValue.get(paramType[0].getTypeName());
                                f.set(instance, Arrays.asList(value, value, value));
                            } catch (IllegalAccessException e) {
                                System.err.println("failed to set " + f.getName());
                            }
                        } else {
                            try {
                                Object paramTypeInstance = Class.forName(paramType[0].getTypeName())
                                        .getDeclaredConstructor()
                                        .newInstance();
                                setValueToMember(paramTypeInstance);
                                f.setAccessible(true);
                                f.set(instance, Arrays.asList(paramTypeInstance, paramTypeInstance, paramTypeInstance));
                            } catch (Exception e) {
                                System.err.println("not declared constructor. " + paramType[0].getClass().getName());
                            }
                        }
                        break;
                    case "java.util.Set":
                        if (typeToValue.keySet().contains(paramType[0].getTypeName())) {
                            f.setAccessible(true);
                            try {
                                Object value = typeToValue.get(paramType[0].getTypeName());
                                f.set(instance, new HashSet<>(Arrays.asList(value, value, value)));
                            } catch (IllegalAccessException e) {
                                System.err.println("failed to set " + f.getName());
                            }
                        } else {
                            try {
                                Object paramTypeInstance = InstanceFactory
                                        .getInstance(Class.forName(paramType[0].getTypeName()));
                                setValueToMember(paramTypeInstance);
                                f.setAccessible(true);
                                f.set(instance, new HashSet<>(
                                        Arrays.asList(paramTypeInstance, paramTypeInstance, paramTypeInstance)));
                            } catch (Exception e) {
                                System.err.println("not declared constructor. " + paramType[0].getClass().getName());
                            }
                        }
                        break;
                    case "java.util.Map":
                        f.setAccessible(true);
                        try {
                            Object key = typeToValue.get(paramType[0].getTypeName());
                            Object value = typeToValue.get(paramType[1].getTypeName());
                            f.set(instance, Map.of(key, value));
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
