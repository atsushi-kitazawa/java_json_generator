package com.atushi.kitazawa;

import java.util.Arrays;
import java.util.Map;

import org.junit.Test;

import junit.framework.TestCase;

/**
 * Unit test for simple App.
 */
public class MainTest
        extends TestCase {

    @Test
    public void testGetClassMember() {
        TestClass instance = new TestClass();
        Main.setValueToMember(instance);

        assertEquals(0, instance.getI());
        assertEquals("aaa", instance.getS());
        assertEquals(Integer.valueOf(10), instance.getIntObject());
        assertEquals(Long.valueOf(100l), instance.getLongObject());
        assertEquals(Arrays.asList("a", "b", "c"), instance.getStrList());
        assertEquals(Arrays.asList(1, 2, 3), instance.getIntList());
        assertEquals(Map.of(1, "value1", 2, "value2"), instance.getMap());
    }
}
