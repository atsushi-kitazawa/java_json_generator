package com.atushi.kitazawa;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        doMain();
    }

    private static void doMain() {
        System.out.println("call doMain");
    }

    public static <T> void setValueToMember(T instance) {
        Field[] fields = instance.getClass().getDeclaredFields();
        for (Field f : fields) {
            switch (f.getType().getName()) {
                case "java.lang.String":
                    // System.out.println("String : " + f.getName());
                    f.setAccessible(true);
                    try {
                        f.set(instance, "aaa");
                    } catch (IllegalAccessException e) {
                        System.err.println("failed to set " + f.getName());
                    }
                    break;
                case "java.lang.Integer":
                    // System.out.println("Integer : " + f.getName());
                    f.setAccessible(true);
                    try {
                        f.set(instance, 10);
                    } catch (IllegalAccessException e) {
                        System.err.println("failed to set " + f.getName());
                    }
                    break;
                case "java.lang.Long":
                    // System.out.println("Long : " + f.getName());
                    f.setAccessible(true);
                    try {
                        f.set(instance, 100l);
                    } catch (IllegalAccessException e) {
                        System.err.println("failed to set " + f.getName());
                    }
                    break;
                case "int":
                    f.setAccessible(true);
                    try {
                        f.set(instance, 0);
                    } catch (IllegalAccessException e) {
                        System.err.println("failed to set " + f.getName());
                    }
                    break;
                case "java.util.List":
                    f.setAccessible(true);
                    switch (((ParameterizedType)f.getGenericType()).getActualTypeArguments()[0].getTypeName()) {
                        case "java.lang.String":
                            try {
                                f.set(instance, Arrays.asList("a", "b", "c"));
                            } catch (IllegalAccessException e) {
                                System.err.println("failed to set " + f.getName());
                            }
                            break;
                        case "java.lang.Integer":
                            try {
                                f.set(instance, Arrays.asList(1, 2, 3));
                            } catch (IllegalAccessException e) {
                                System.err.println("failed to set " + f.getName());
                            }
                            break;
                        default:
                            break;
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
                    // System.out.println(f.getType() + ":" + f.getName());
            }
        }
    }
}
