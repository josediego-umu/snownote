package com.um.snownote.services.implementation;

import com.um.snownote.model.User;
import com.um.snownote.repository.interfaces.IUserRepository;
import com.um.snownote.services.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class UserService implements IUserService {
    @Autowired
    private IUserRepository userRepository;

    @Override
    public boolean login(String username, String password) {
        User user = userRepository.findUserByUsernameAndPassword(username, password);
        return user != null;
    }

    @Override
    public User register(String username, String password, String email, String name, String dateOfBirth) {
        User userRecover = userRepository.findUserByUsername(username);
        if (userRecover != null) {
            return null;
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        user.setName(name);
        user.setDateOfBirth(new Date(Long.parseLong(dateOfBirth)));
        return userRepository.save(user);
    }

    @Override
    public User register(User user) {
        User userRecover = userRepository.findUserByUsername(user.getUsername());

        if (userRecover != null) {
            return null;
        }

        return userRepository.save(user);
    }

    @Override
    public User getUser(String username) {
        return userRepository.findUserByUsername(username);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

}
