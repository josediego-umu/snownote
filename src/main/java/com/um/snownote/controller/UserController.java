package com.um.snownote.controller;


import com.um.snownote.dto.UserDTO;
import com.um.snownote.mappers.MapperDTO;
import com.um.snownote.model.User;
import com.um.snownote.services.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    private final IUserService userService;
    private final MapperDTO mapper = MapperDTO.INSTANCE;

    @Autowired
    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public UserDTO login(@RequestBody User user) {
        return mapper.userToUserDTO(userService.login(user.getUsername(), user.getPassword()));
    }

    /*TODO
    Cambiar el metodo de registro para que reciba un objeto UserDTO y no los parametros por separado, cambiar el formado de fechas a ISO 8601
     */
    @PostMapping("/register")
    public User register(@RequestBody User user) {
        return userService.register(user);
    }

    @GetMapping(value = "/username/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    public User getUser(@PathVariable String username) {
        return userService.getUser(username);
    }

    @GetMapping
    public List<UserDTO> getUsers() {
        List<User> users = userService.getAllUsers();
        List<UserDTO> userDTOS = new ArrayList<>();

        for (User user : users) {
            userDTOS.add(mapper.userToUserDTO(user));
        }

        return userDTOS;
    }

    @PutMapping
    public UserDTO updateUser(@RequestBody User user) {

        return mapper.userToUserDTO(userService.updateUser(user));
    }

}
