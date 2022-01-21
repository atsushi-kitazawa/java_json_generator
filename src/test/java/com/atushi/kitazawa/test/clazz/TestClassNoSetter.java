package com.atushi.kitazawa.test.clazz;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class TestClassNoSetter {
    // primitive type
    private int i;

    // Object type
    private String s;
    private Integer intObject;
    private Long longObject;
    private Double doubleObject;

    // Collection
    private List<String> strList;
    private List<Integer> intList;
    private Map<Integer, String> map;
    private Set<Integer> intSet;
    private Set<String> strSet;

    // Nest Custom class
    private TestNestClass nestClass;

    // Collection int Custom class
    private List<TestNestClass> nestClassList;
    private Set<TestNestClass> nestClassSet;

    public int getI() {
        return i;
    }

    public String getS() {
        return s;
    }

    public Integer getIntObject() {
        return intObject;
    }

    public Long getLongObject() {
        return longObject;
    }

    public Double getDoubleObject() {
        return doubleObject;
    }

    public List<String> getStrList() {
        return strList;
    }

    public List<Integer> getIntList() {
        return intList;
    }

    public Map<Integer, String> getMap() {
        return map;
    }

    public Set<Integer> getIntSet() {
        return intSet;
    }

    public Set<String> getStrSet() {
        return strSet;
    }

    public TestNestClass getNestClass() {
        return nestClass;
    }

    public List<TestNestClass> getNestClassList() {
        return nestClassList;
    }

    public Set<TestNestClass> getNestClassSet() {
        return nestClassSet;
    }

}
