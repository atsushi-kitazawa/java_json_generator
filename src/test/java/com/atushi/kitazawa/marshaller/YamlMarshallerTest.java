package com.atushi.kitazawa.marshaller;

import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.Test;

public class YamlMarshallerTest {

    @Test
    public void test() {
        A instance = new A();
        instance.setA("string value");
        instance.setB(0);
        instance.setC(Arrays.asList("list value1", "list value2", "list value3"));
        instance.setD(Arrays.asList(new SubA("AAA"), new SubA("BBB"), new SubA("CCC")));

        String expected = "!!com.atushi.kitazawa.YamlMarshallerTest$A\n" +
                "a: string value\n" +
                "b: 0\n" +
                "c: [list value1, list value2, list value3]\n" +
                "d:\n" +
                "- {aa: AAA}\n" +
                "- {aa: BBB}\n" +
                "- {aa: CCC}\n";
        Marshaller m = MarshallerFactory.getMarshaller("YAML");
        String yaml = m.marshal(instance);
        assertThat("", yaml, Matchers.is(expected));
    }

    private class A {
        String a;
        Integer b;
        List<String> c;
        List<SubA> d;

        public String getA() {
            return a;
        }

        public Integer getB() {
            return b;
        }

        public List<String> getC() {
            return c;
        }

        public List<SubA> getD() {
            return d;
        }

        public void setA(String a) {
            this.a = a;
        }

        public void setB(Integer b) {
            this.b = b;
        }

        public void setC(List<String> c) {
            this.c = c;
        }

        public void setD(List<SubA> d) {
            this.d = d;
        }

        @Override
        public String toString() {
            return "A [a=" + a + ", b=" + b + ", c=" + c + ", d=" + d + "]";
        }
    }

    private class SubA {
        String aa;

        public SubA(String aa) {
            this.aa = aa;
        }

        public String getAa() {
            return aa;
        }

        public void setAa(String aa) {
            this.aa = aa;
        }

        @Override
        public String toString() {
            return "SubA [aa=" + aa + "]";
        }
    }
}
