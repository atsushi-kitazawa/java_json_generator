package com.atushi.kitazawa;

import java.util.Arrays;
import java.util.HashSet;
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

        assertEquals(1, instance.getI());
        assertEquals("aaa", instance.getS());
        assertEquals(Integer.valueOf(10), instance.getIntObject());
        assertEquals(Long.valueOf(100l), instance.getLongObject());
        assertEquals(Arrays.asList("aaa", "aaa", "aaa"), instance.getStrList());
        assertEquals(Arrays.asList(10, 10, 10), instance.getIntList());
        assertEquals(Map.of(1, "value1", 2, "value2"), instance.getMap());
        assertEquals(new HashSet<>(Arrays.asList(10, 10, 10)), instance.getIntSet());
        assertEquals(new HashSet<>(Arrays.asList("aaa", "aaa", "aaa")), instance.getStrSet());
    }
}
