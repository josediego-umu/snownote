package com.um.snownote.services.interfaces;

import com.um.snownote.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IUserService {

    boolean login(String username, String password);

    User register(String username, String password, String email, String name, String dateOfBirth);

    User register(User user);

    User getUser(String username);

    List<User> getAllUsers();
}
