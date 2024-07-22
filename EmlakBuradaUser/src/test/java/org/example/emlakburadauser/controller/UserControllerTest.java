package org.example.emlakburadauser.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.example.emlakburadauser.model.User;
import org.example.emlakburadauser.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    void register_successfully() throws Exception {
        // Given
        User user = new User();
        user.setId(1L);
        user.setEmail("test@example.com");
        user.setPassword("password");
        user.setPhoneNumber("1234567890");

        when(userService.registerUser(any(User.class))).thenReturn(user);

        String requestBody = objectMapper.writeValueAsString(user);

        // When
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/users")
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON));

        // Then
        resultActions.andExpect(status().isOk());
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        User responseUser = objectMapper.readValue(responseBody, User.class);

        assertEquals(user.getEmail(), responseUser.getEmail());
    }

    @Test
    void getAllUsers_successfully() throws Exception {
        // Given
        User user = new User();
        user.setId(1L);
        user.setEmail("test@example.com");

        when(userService.getAllUsers()).thenReturn(Collections.singletonList(user));

        // When
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON));

        // Then
        resultActions.andExpect(status().isOk());
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        List<User> responseUsers = objectMapper.readValue(responseBody, objectMapper.getTypeFactory().constructCollectionType(List.class, User.class));

        assertEquals(1, responseUsers.size());
        assertEquals(user.getEmail(), responseUsers.get(0).getEmail());
    }

    @Test
    void findByEmail_successfully() throws Exception {
        // Given
        String email = "test@example.com";
        User user = new User();
        user.setId(1L);
        user.setEmail(email);

        when(userService.findByEmail(anyString())).thenReturn(user);

        // When
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/users/email/{email}", email)
                .contentType(MediaType.APPLICATION_JSON));

        // Then
        resultActions.andExpect(status().isOk());
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        User responseUser = objectMapper.readValue(responseBody, User.class);

        assertEquals(email, responseUser.getEmail());
    }
}