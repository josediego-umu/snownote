package com.um.snownote.controller;



import com.um.snownote.model.User;
import com.um.snownote.services.interfaces.IUserService;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private IUserService userService;

    public UserController() {
    }
    @PostMapping("/login")
    public boolean login(@RequestParam String username, @RequestParam String password) {
        return userService.login(username, password);
    }

    @PostMapping("/register")
    public boolean register(@RequestParam String username, @RequestParam String password, @RequestParam String email, @RequestParam String name, @RequestParam String dateOfBirth) {
        return userService.register(username, password, email, name, dateOfBirth.trim()) != null;
    }
    @GetMapping(value = "/username/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    public User getUser(@PathVariable String username) {
        return userService.getUser(username);
    }
    @GetMapping
    public List<User> getUsers() {
        return userService.getAllUsers();
    }

}
