package com.um.snownote.services.implementation;

import com.um.snownote.model.User;
import com.um.snownote.repository.interfaces.IUserRepository;
import com.um.snownote.services.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;

@Service
public class UserService implements IUserService {
    private final IUserRepository userRepository;

    @Autowired
    public UserService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User login(String username, String password) {
        return userRepository.findUserByUsernameAndPassword(username, password);
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
        ZonedDateTime dateOfBirthParse = ZonedDateTime.parse(dateOfBirth);
        user.setDateOfBirth(dateOfBirthParse.toLocalDateTime());
        return userRepository.save(user);
    }

    @Override
    public User register(User user) {
        User userRecover = userRepository.findUserByUsername(user.getUsername());

        if (userRecover != null) {
            throw new RuntimeException("User already exists");
        }

        return userRepository.save(user);
    }

    @Override
    public User getUserById(String id) {
        return userRepository.findById(id).orElse(null);
    }


    @Override
    public User getUser(String username) {
        return userRepository.findUserByUsername(username);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User updateUser(User user) {
        return userRepository.save(user);
    }

}
