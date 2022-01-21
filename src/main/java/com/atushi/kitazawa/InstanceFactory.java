package com.atushi.kitazawa;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.List;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtConstructor;
import javassist.CtField;
import javassist.CtMethod;
import javassist.CtNewConstructor;
import javassist.CtNewMethod;
import javassist.Loader;

public class InstanceFactory {

    public static Object getInstance(Class<?> clazz) {
        Object instance = null;
        try {
            ClassPool cp = ClassPool.getDefault();
            CtClass cc = cp.get(clazz.getName());
            cc.defrost();

            // add public default constructor
            if (!hasDefaultConstructor(clazz)) {
                CtConstructor constructor = CtNewConstructor.defaultConstructor(cc);
                cc.addConstructor(constructor);
            }

            // add getter/setter method to each filed
            List<CtMethod> methods = Arrays.asList(cc.getDeclaredMethods());
            for (CtField f : cc.getDeclaredFields()) {
                if (!hasSetterMethod(f, methods)) {
                    addSetterMethod(f, cc);
                }
                if (!hasGetterMethod(f, methods)) {
                    addGetterMethod(f, cc);
                }
            }

            Loader cl = new Loader(cp);
            instance = cl.loadClass(clazz.getName()).getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return instance;
    }

    private static boolean hasDefaultConstructor(Class<?> clazz) {
        Constructor<?>[] constructors = clazz.getDeclaredConstructors();
        for (Constructor<?> c : constructors) {
            if (c.getParameterTypes().length == 0) {
                return true;
            }
        }
        return false;
    }

    private static boolean hasSetterMethod(CtField f, List<CtMethod> list) {
        boolean ret = false;
        for(CtMethod m : list) {
            if(m.getName().equals("set" + f.getName().toUpperCase())) {
                ret = true;
                break;
            }
        }
        return ret;
    }

    private static boolean hasGetterMethod(CtField f, List<CtMethod> list) {
        boolean ret = false;
        for(CtMethod m : list) {
            if(m.getName().equals("get" + f.getName().toUpperCase())) {
                ret = true;
                break;
            }
        }
        return ret;
    }

    private static void addSetterMethod(CtField f, CtClass cc) throws Exception {
        CtMethod method = CtNewMethod.setter("set" + f.getName().toUpperCase(), f);
        cc.addMethod(method);
    }

    private static void addGetterMethod(CtField f, CtClass cc) throws Exception {
        CtMethod method = CtNewMethod.getter("get" + f.getName().toUpperCase(), f);
        cc.addMethod(method);
    }
}
