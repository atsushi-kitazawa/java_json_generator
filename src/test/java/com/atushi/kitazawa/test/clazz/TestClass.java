package com.atushi.kitazawa.test.clazz;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class TestClass {
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

    public void setI(int i) {
        this.i = i;
    }

    public String getS() {
        return s;
    }

    public void setS(String s) {
        this.s = s;
    }

    public Integer getIntObject() {
        return intObject;
    }

    public void setIntObject(Integer intObject) {
        this.intObject = intObject;
    }

    public Long getLongObject() {
        return longObject;
    }

    public void setLongObject(Long longObject) {
        this.longObject = longObject;
    }

    public List<String> getStrList() {
        return strList;
    }

    public void setStrList(List<String> strList) {
        this.strList = strList;
    }

    public List<Integer> getIntList() {
        return intList;
    }

    public void setIntList(List<Integer> intList) {
        this.intList = intList;
    }

    public Map<Integer, String> getMap() {
        return map;
    }

    public void setMap(Map<Integer, String> map) {
        this.map = map;
    }

    public Set<Integer> getIntSet() {
        return intSet;
    }

    public void setIntSet(Set<Integer> intSet) {
        this.intSet = intSet;
    }

    public Set<String> getStrSet() {
        return strSet;
    }

    public void setStrSet(Set<String> strSet) {
        this.strSet = strSet;
    }

    public Double getDoubleObject() {
        return doubleObject;
    }

    public void setDoubleObject(Double doubleObject) {
        this.doubleObject = doubleObject;
    }

    public TestNestClass getNestClass() {
        return nestClass;
    }

    public void setNestClass(TestNestClass nestClass) {
        this.nestClass = nestClass;
    }

    public List<TestNestClass> getNestClassList() {
        return nestClassList;
    }

    public void setNestClassList(List<TestNestClass> nestClassList) {
        this.nestClassList = nestClassList;
    }

    public Set<TestNestClass> getNestClassSet() {
        return nestClassSet;
    }

    public void setNestClassSet(Set<TestNestClass> nestClassSet) {
        this.nestClassSet = nestClassSet;
    }

}
