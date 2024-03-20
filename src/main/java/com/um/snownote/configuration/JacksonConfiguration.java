package com.um.snownote.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JacksonConfiguration {
    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.findAndRegisterModules();
        return mapper;
    }

    @Bean
    public ZonedDateTimeWriteConverter zonedDateTimeWriteConverter() {
        return new ZonedDateTimeWriteConverter();
    }

    @Bean ZonedDateTimeReadConverter zonedDateTimeReadConverter() {
        return new ZonedDateTimeReadConverter();
    }

}
