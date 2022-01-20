package com.atushi.kitazawa.marshaller;

public interface Marshaller {
    <T> String marshal(T instance);
}
