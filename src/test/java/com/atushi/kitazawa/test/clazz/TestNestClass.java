package com.atushi.kitazawa.test.clazz;

public class TestNestClass {
    private String subStr;

    public TestNestClass(String s) {
        this.subStr = s;
    }

    public String getSubStr() {
        return subStr;
    }

    public void setSubStr(String subStr) {
        this.subStr = subStr;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((subStr == null) ? 0 : subStr.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        TestNestClass other = (TestNestClass) obj;
        if (subStr == null) {
            if (other.subStr != null)
                return false;
        } else if (!subStr.equals(other.subStr))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "TestNestClass [subStr=" + subStr + "]";
    }

}
