package org.example.emlakburadauser.service;


import org.example.emlakburadauser.model.User;
import org.example.emlakburadauser.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        // Inject mock into UserService
        userService = new UserService(userRepository, passwordEncoder);
    }

    @Test
    void registerUser_successfully() {
        // Given
        User user = new User();
        user.setId(1L);
        user.setEmail("test@example.com");
        user.setPassword("password");
        String encodedPassword = "encodedPassword";

        when(passwordEncoder.encode(any(String.class))).thenReturn(encodedPassword);
        when(userRepository.save(any(User.class))).thenReturn(user);

        // When
        User result = userService.registerUser(user);

        // Then
        assertEquals(encodedPassword, result.getPassword());
        assertEquals(user.getEmail(), result.getEmail());
        verify(passwordEncoder).encode(user.getPassword());
        verify(userRepository).save(user);
    }

    @Test
    void getAllUsers_successfully() {
        // Given
        User user = new User();
        user.setId(1L);
        user.setEmail("test@example.com");

        when(userRepository.findAll()).thenReturn(Collections.singletonList(user));

        // When
        List<User> result = userService.getAllUsers();

        // Then
        assertEquals(1, result.size());
        assertEquals(user.getEmail(), result.get(0).getEmail());
        verify(userRepository).findAll();
    }

    @Test
    void findByEmail_successfully() {
        // Given
        String email = "test@example.com";
        User user = new User();
        user.setId(1L);
        user.setEmail(email);

        when(userRepository.findByEmail(anyString())).thenReturn(user);

        // When
        User result = userService.findByEmail(email);

        // Then
        assertEquals(email, result.getEmail());
        verify(userRepository).findByEmail(email);
    }
}