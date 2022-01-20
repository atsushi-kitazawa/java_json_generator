package com.atushi.kitazawa;

public class MarshallerFactory {
    public static Marshaller getMarshaller(String format) {
        switch (format) {
            case "JSON":
                return new JsonMarshaller();
            case "YAML":
                return new YamlMarshaller();
            default:
                throw new UnsupportedOperationException(String.format("%s format don't support.", format));
        }
    }
}
