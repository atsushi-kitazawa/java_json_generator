package com.atushi.kitazawa.marshaller;

import static org.junit.Assert.assertThat;

import org.hamcrest.Matchers;
import org.junit.Test;

public class MarshallerFactoryTest {

    @Test
    public void testGetMarshaller() {
        Marshaller m = MarshallerFactory.getMarshaller("JSON");
        assertThat("type is JsonMarshaller", m.getClass().getSimpleName(), Matchers.is("JsonMarshaller"));

        m = MarshallerFactory.getMarshaller("YAML");
        assertThat("type is YamlMarshaller", m.getClass().getSimpleName(), Matchers.is("YamlMarshaller"));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testUnsupportFormat() {
        MarshallerFactory.getMarshaller("INVALID_FORMAT");
    }
}
