package com.atushi.kitazawa;

import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.Test;

public class JsonMarshallerTest {

    @Test
    public void testMarshall() {
        A instance = new A();
        instance.setA("string value");
        instance.setB(0);
        instance.setC(Arrays.asList("list value1", "list value2", "list value3"));

        String expected = "{\"a\":\"string value\",\"b\":0,\"c\":[\"list value1\",\"list value2\",\"list value3\"]}";
        Marshaller m = MarshallerFactory.getMarshaller("JSON");
        String json = m.marshal(instance);
        assertThat("", json, Matchers.is(expected));
    }

    private class A {
        String a;
        Integer b;
        List<String> c;

        public String getA() {
            return a;
        }

        public void setA(String a) {
            this.a = a;
        }

        public Integer getB() {
            return b;
        }

        public void setB(Integer b) {
            this.b = b;
        }

        public List<String> getC() {
            return c;
        }

        public void setC(List<String> c) {
            this.c = c;
        }

    }
}
