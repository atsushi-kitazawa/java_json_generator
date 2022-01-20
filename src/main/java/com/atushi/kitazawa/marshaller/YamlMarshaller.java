package com.atushi.kitazawa.marshaller;

import java.io.StringWriter;

import org.yaml.snakeyaml.Yaml;

public class YamlMarshaller implements Marshaller {
    @Override
    public <T> String marshal(T instance) {
        Yaml yaml = new Yaml();
        StringWriter writer = new StringWriter();
        yaml.dump(instance, writer);
        return writer.toString();
    }
}
