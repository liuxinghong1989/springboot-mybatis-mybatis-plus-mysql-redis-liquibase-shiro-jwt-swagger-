package com.example.demo.security.exception;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;

/**
 * @author zhuyanwei
 */
public class CustomOauthExceptionSerializer extends StdSerializer<CustomOauthException> {

    private static final Logger LOG = LoggerFactory.getLogger(CustomOauthExceptionSerializer.class);

    public CustomOauthExceptionSerializer() {
        super(CustomOauthException.class);
    }

    @Override
    public void serialize(CustomOauthException value, JsonGenerator generator, SerializerProvider serializerProvider) throws IOException {
        LOG.warn("——————————————————————————————————————————————————————————————————");
        generator.writeStartObject();
        generator.writeStringField("code", value.getCode().toString());
        generator.writeStringField("message", value.getMessage());
        if (value.getAdditionalInformation()!=null) {
            for (Map.Entry<String, String> entry : value.getAdditionalInformation().entrySet()) {
                String key = entry.getKey();
                String add = entry.getValue();
                generator.writeStringField(key, add);
            }
        }
        generator.writeEndObject();
    }
}
