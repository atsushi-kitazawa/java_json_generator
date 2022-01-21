package com.atushi.kitazawa;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;

import com.atushi.kitazawa.marshaller.Marshaller;
import com.atushi.kitazawa.marshaller.MarshallerFactory;

public class Main {

    public static void main(String[] args) {
        doMain(args[0]);
    }

    private static void doMain(String format) {
        Object instance = InstanceFactory.getInstance(A.class);
        setValueToMember(instance);
        Marshaller m = MarshallerFactory.getMarshaller(format);
        System.out.println(m.marshal(instance));
    }

    public static <T> void setValueToMember(T instance) {
        Map<String, Object> typeToValue = TypeValueMapping.getTypeToValue();
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

    private static boolean isCollection(String type) {
        return TypeValueMapping.getCollectionType().contains(type);
    }
}
