package com.atushi.kitazawa;

public interface Marshaller {
    <T> String marshal(T instance);
}
