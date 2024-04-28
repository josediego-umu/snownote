package com.um.snownote.controller;


import com.um.snownote.dto.UserDTO;
import com.um.snownote.exceptions.LoginException;
import com.um.snownote.jwtUtils.JwtTokenRequired;
import com.um.snownote.jwtUtils.JwtUtil;
import com.um.snownote.mappers.MapperDTO;
import com.um.snownote.model.User;
import com.um.snownote.services.interfaces.IUserService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(UserController.class);
    private final long EXPIRATION_TIME = 1000 * 60 * 60 * 24;
    private final IUserService userService;
    private final MapperDTO mapper = MapperDTO.INSTANCE;

    @Autowired
    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User userCredentials) {

        User user = userService.login(userCredentials.getUsername(), userCredentials.getPassword());
        UserDTO userDTO = mapper.userToUserDTO(user);

        if (userDTO == null) {

            throw new LoginException("Invalid username or password");
        }

        try {

            String token = JwtUtil.generateToken(EXPIRATION_TIME, userDTO);

            return ResponseEntity.ok().header("Authorization", token).body("{ \"token\": \"" + token + "\" }");

        } catch (Exception e) {

            logger.error("Error in login", e);
        }


        return null;
    }

    @PostMapping("/register")
    public User register(@RequestBody User user) {

        if (user.getUsername() == null || user.getUsername().isEmpty())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username cannot be null or empty");
        if (user.getPassword() == null || user.getPassword().isEmpty())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password cannot be null or empty");
        if (user.getEmail() == null || user.getEmail().isEmpty())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email cannot be null or empty");
        if (user.getName() == null || user.getName().isEmpty())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Name cannot be null or empty");
        if (user.getDateOfBirth() == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Date of birth cannot be null");

        try {
            return userService.register(user);
        } catch (Exception e) {
            logger.error("Error in register", e);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }

    }

    @JwtTokenRequired
    @GetMapping(value = "/username/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    public UserDTO getUser(@RequestHeader("Authorization") String token, @PathVariable String username) {
        return mapper.userToUserDTO(userService.getUser(username));
    }

    @GetMapping
    @JwtTokenRequired
    public List<UserDTO> getUsers(@RequestHeader("Authorization") String token) {

        List<User> users = userService.getAllUsers();
        List<UserDTO> userDTOS = new ArrayList<>();

        for (User user : users) {
            userDTOS.add(mapper.userToUserDTO(user));
        }

        return userDTOS;
    }

    @PutMapping
    @JwtTokenRequired
    public UserDTO updateUser(@RequestHeader("Authorization") String token, @RequestBody User user) {

        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }

        User userToUpdate = userService.getUser(user.getUsername());

        if (userToUpdate == null) {
            throw new IllegalArgumentException("User does not exist");
        }

        userToUpdate.setName(user.getName());
        userToUpdate.setUsername(user.getUsername());
        userToUpdate.setEmail(user.getEmail());
        userToUpdate.setDateOfBirth(user.getDateOfBirth());

        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            userToUpdate.setPassword(user.getPassword());
        }

        return mapper.userToUserDTO(userService.updateUser(userToUpdate));

    }

}
