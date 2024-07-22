package org.example.emlakburadaproje.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.example.emlakburadaproje.model.Advert;
import org.example.emlakburadaproje.model.UserAdvert;
import org.example.emlakburadaproje.service.AdvertService;
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
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AdvertController.class)
public class AdvertControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AdvertService advertService;

    @Test
    void getPackages_successfully() throws Exception {
        // Given
        Advert advert = new Advert();
        advert.setId(1L);
        List<Advert> adverts = Collections.singletonList(advert);

        when(advertService.getAdverts()).thenReturn(adverts);

        // When
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/adverts")
                .contentType(MediaType.APPLICATION_JSON));

        // Then
        resultActions.andExpect(status().isOk());
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        List<Advert> responseAdverts = objectMapper.readValue(responseBody, objectMapper.getTypeFactory().constructCollectionType(List.class, Advert.class));

        assertEquals(adverts.size(), responseAdverts.size());
        assertEquals(adverts.get(0).getId(), responseAdverts.get(0).getId());
    }

    @Test
    void createAdvert_successfully() throws Exception {
        // Given
        Advert advert = new Advert();
        advert.setId(1L);

        when(advertService.createAdvert(any(Advert.class))).thenReturn(advert);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String body = objectMapper.writeValueAsString(advert);

        // When
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/adverts")
                .content(body)
                .contentType(MediaType.APPLICATION_JSON));

        // Then
        resultActions.andExpect(status().isOk());
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        Advert responseAdvert = objectMapper.readValue(responseBody, Advert.class);

        assertEquals(advert.getId(), responseAdvert.getId());
    }

    @Test
    void purchasePackage_successfully() throws Exception {
        // Given
        Long userId = 1L;
        Long packageId = 1L;
        UserAdvert userAdvert = new UserAdvert();
        userAdvert.setUserId(userId);
        userAdvert.setPackageId(packageId);

        when(advertService.purchaseAdvert(anyLong(), anyLong())).thenReturn(userAdvert);

        // When
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/adverts/purchase")
                .param("userId", userId.toString())
                .param("packageId", packageId.toString())
                .contentType(MediaType.APPLICATION_JSON));

        // Then
        resultActions.andExpect(status().isOk());
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        UserAdvert responseUserAdvert = objectMapper.readValue(responseBody, UserAdvert.class);

        assertEquals(userAdvert.getUserId(), responseUserAdvert.getUserId());
        assertEquals(userAdvert.getPackageId(), responseUserAdvert.getPackageId());
    }

    @Test
    void getUserPackages_successfully() throws Exception {
        // Given
        Long userId = 1L;
        UserAdvert userAdvert = new UserAdvert();
        userAdvert.setUserId(userId);
        List<UserAdvert> userAdverts = Collections.singletonList(userAdvert);

        when(advertService.getUserPackages(anyLong())).thenReturn(userAdverts);

        // When
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/adverts/user")
                .param("userId", userId.toString())
                .contentType(MediaType.APPLICATION_JSON));

        // Then
        resultActions.andExpect(status().isOk());
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        List<UserAdvert> responseUserAdverts = objectMapper.readValue(responseBody, objectMapper.getTypeFactory().constructCollectionType(List.class, UserAdvert.class));

        assertEquals(userAdverts.size(), responseUserAdverts.size());
        assertEquals(userAdverts.get(0).getUserId(), responseUserAdverts.get(0).getUserId());
    }
}