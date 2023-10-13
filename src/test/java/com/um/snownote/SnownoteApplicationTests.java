package com.um.snownote;

import com.um.snownote.model.User;
import com.um.snownote.repository.interfaces.IUserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

@SpringBootTest
class SnownoteApplicationTests {
    @Autowired
    private IUserRepository IUserRepository;

    @Test
    public void insertUser()  {
        User user = new User();
        user.setName("test");
        user.setUsername("test");
        user.setPassword("test");
        user.setEmail("test");
        user.setPhone("test");

        IUserRepository.save(user);

        Optional<User> userRecover = IUserRepository.findById(user.getId());
        Assertions.assertTrue(userRecover.isPresent());
        Assertions.assertNotNull(userRecover.get());
    }
    @Test
    public void findByName(){

        List<User> users = IUserRepository.findUserByName("test");

        Assertions.assertNotNull(users);
        Assertions.assertFalse(users.isEmpty());
    }



}
