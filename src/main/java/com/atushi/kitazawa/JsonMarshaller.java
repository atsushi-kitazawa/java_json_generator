package com.atushi.kitazawa;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonMarshaller implements Marshaller {
    @Override
    public <T> String marshal(T instance) {
        ObjectMapper mapper = new ObjectMapper();
        String json = null;
        try {
            json = mapper.writeValueAsString(instance);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return json;
    }
}
