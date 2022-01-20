package com.atushi.kitazawa;

import java.lang.reflect.Constructor;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtConstructor;
import javassist.CtNewConstructor;
import javassist.Loader;

public class InstanceFactory {
    
    public static Object getInstance(Class<?> clazz) {
        Object instance = null;
        try {
            if (hasDefaultConstructor(clazz)) {
                instance = clazz.getDeclaredConstructor().newInstance();
            } else {
                // add default public constructer to clazz
                ClassPool cp = ClassPool.getDefault();
                CtClass cc = cp.get(clazz.getName());
                CtConstructor constructor = CtNewConstructor.defaultConstructor(cc);
                cc.addConstructor(constructor);

                Loader cl = new Loader(cp);
                instance = cl.loadClass(clazz.getName()).getDeclaredConstructor().newInstance();
            }
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
}
