package com.atushi.kitazawa;

import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import com.atushi.kitazawa.test.clazz.HasDefaultConstructor;
import com.atushi.kitazawa.test.clazz.NoHasDefaultConstructor;

import org.hamcrest.Matchers;
import org.junit.Test;

public class InstanceFactoryTest {
    @Test
    public void testHasDefaultConstructor() throws Exception {
        Object instance = InstanceFactory.getInstance(HasDefaultConstructor.class);
        assertThat(instance.getClass().getName(), Matchers.is(HasDefaultConstructor.class.getName()));
    }

    @Test
    public void testNoHasDefaultConstructor() {
        Object instance = InstanceFactory.getInstance(NoHasDefaultConstructor.class);
        assertThat(instance.getClass().getName(), Matchers.is(NoHasDefaultConstructor.class.getName()));
    }

    @Test
    public void testCheckAddGetterSetter() throws Exception {
        Object instance = InstanceFactory.getInstance(HasDefaultConstructor.class);
        instance.getClass().getMethod("setS", String.class);
        instance.getClass().getMethod("getS");
        assertTrue(true);
    }
}
