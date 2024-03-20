package com.um.snownote.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.um.snownote.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Date;

@SpringBootTest
public class UserTest {

    @Autowired
    private ObjectMapper mapper;
    @Test
    public void registerUser() throws JsonProcessingException {

        User user = new User();
        user.setUsername("test");
        user.setPassword("test");
        user.setEmail("test@test.com");
        user.setName("test");
        user.setDateOfBirth(LocalDateTime.now());
        user.setCreateBy(null);
        user.setCreateDate(new Date());
        user.setUpdateBy(null);
        user.setUpdateDate(new Date());



        String json = mapper.writeValueAsString(user);

        System.out.println(json);
    }

}
