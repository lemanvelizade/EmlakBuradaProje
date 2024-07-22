package org.example.emlakburadauser.controller;

import lombok.RequiredArgsConstructor;
import org.example.emlakburadauser.model.User;
import org.example.emlakburadauser.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor

public class UserController {


    private final UserService userService;

    @PostMapping
    public User register(@RequestBody User user) {
        return userService.registerUser(user);
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/email/{email}")
    public User findByEmail(@PathVariable String email) {
        return userService.findByEmail(email);
    }
}