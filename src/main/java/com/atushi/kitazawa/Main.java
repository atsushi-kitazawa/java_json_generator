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
        if (args.length != 2) {
            System.out.println(
                    "Usage: -cp jgenerator-1.0-SNAPSHOT-jar-with-dependencies.jar <target class> <format(JSON/YAML)>");
            System.exit(0);
        }

        String targetClass = args[0];
        String format = args[1];

        if (!"JSON".equals(format) && !"YAML".equals(format)) {
            System.err.println("no support format " + format);
            System.exit(0);
        }

        doMain(targetClass, format);
    }

    private static void doMain(String targetClass, String format) {
        try {
            Object instance = InstanceFactory.getInstance(Class.forName(targetClass));
            setValueToMember(instance);
            Marshaller m = MarshallerFactory.getMarshaller(format);
            System.out.println(m.marshal(instance));
        } catch (ClassNotFoundException e) {
            System.err.println(String.format("not found %s class.", targetClass));
        }
    }

    public static <T> void setValueToMember(T instance) {
        Field[] fields = instance.getClass().getDeclaredFields();
        for (Field f : fields) {
            String type = f.getType().getName();
            if (isCollection(type)) {
                Type[] paramType = ((ParameterizedType) f.getGenericType()).getActualTypeArguments();
                switch (type) {
                    case "java.util.List":
                        if (ValueMapping.canMappingType(paramType[0].getTypeName())) {
                            f.setAccessible(true);
                            try {
                                Object value = ValueMapping.getValue(f.getName(), paramType[0].getTypeName());
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
                        if (ValueMapping.canMappingType(paramType[0].getTypeName())) {
                            f.setAccessible(true);
                            try {
                                Object value = ValueMapping.getValue(f.getName(), paramType[0].getTypeName());
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
                                        Arrays.asList(paramTypeInstance)));
                            } catch (Exception e) {
                                System.err.println("not declared constructor. " + paramType[0].getClass().getName());
                            }
                        }
                        break;
                    case "java.util.Map":
                        f.setAccessible(true);
                        try {
                            Object key = ValueMapping.getValue(f.getName(), paramType[0].getTypeName());
                            Object value = ValueMapping.getValue(f.getName(), paramType[1].getTypeName());
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

            // not collection type.
            if(ValueMapping.canMappingType(type)) {
                f.setAccessible(true);
                try {
                    f.set(instance, ValueMapping.getValue(f.getName(), type));
                } catch (IllegalAccessException e) {
                    System.err.println("failed to set " + f.getName());
                }
            } else {
                try {
                    // Object nestClassInstance = f.getType().getDeclaredConstructor().newInstance();
                    Object nestClassInstance = InstanceFactory.getInstance(f.getType());
                    setValueToMember(nestClassInstance);
                    f.setAccessible(true);
                    f.set(instance, nestClassInstance);
                } catch (Exception e) {
                    System.err.println("not declared constructor. " + type);
                    e.printStackTrace();
                }
            }
        }
    }

    private static boolean isCollection(String type) {
        return ValueMapping.getCollectionType().contains(type);
    }
}
