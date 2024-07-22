package org.example.emlakburadauser.service;

import lombok.RequiredArgsConstructor;
import org.example.emlakburadauser.model.User;
import org.example.emlakburadauser.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional

public class UserService {


    private final UserRepository userRepository;


    private  final BCryptPasswordEncoder passwordEncoder;
    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = (BCryptPasswordEncoder) passwordEncoder;
    }

    public User registerUser(User user) {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        userRepository.save(user);
        return user;
    }
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }



    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}