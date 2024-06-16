package com.um.snownote.mappers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

public class MapSetSerializer extends JsonSerializer<Map<String, Set<String>>> {

    @Override
    public void serialize(Map<String, Set<String>> value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
        for (Map.Entry<String, Set<String>> entry : value.entrySet()) {
            gen.writeFieldName(entry.getKey());
            gen.writeStartArray();
            for (String item : entry.getValue()) {
                gen.writeString(item);
            }
            gen.writeEndArray();
        }
        gen.writeEndObject();
    }


}
