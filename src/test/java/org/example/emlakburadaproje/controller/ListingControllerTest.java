package org.example.emlakburadaproje.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.example.emlakburadaproje.model.Listing;
import org.example.emlakburadaproje.model.ListingStatus;
import org.example.emlakburadaproje.service.ListingService;
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

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ListingController.class)
public class ListingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ListingService listingService;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    void getListings_successfully() throws Exception {
        // Given
        Listing listing = new Listing();
        listing.setId(1L);
        listing.setTitle("Sample Listing");
        listing.setCreatedAt(LocalDateTime.now());

        when(listingService.getListings()).thenReturn(Collections.singletonList(listing));

        // When
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/listings")
                .contentType(MediaType.APPLICATION_JSON));

        // Then
        resultActions.andExpect(status().isOk());
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        List<Listing> responseListings = objectMapper.readValue(responseBody, objectMapper.getTypeFactory().constructCollectionType(List.class, Listing.class));

        assertEquals(1, responseListings.size());
        assertEquals(listing.getId(), responseListings.get(0).getId());
    }

    @Test
    void createListing_successfully() throws Exception {
        // Given
        Listing listing = new Listing();
        listing.setTitle("New Listing");
        listing.setCreatedAt(LocalDateTime.now());

        when(listingService.createListing(any(Listing.class))).thenReturn(listing);

        String requestBody = objectMapper.writeValueAsString(listing);

        // When
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/listings")
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON));

        // Then
        resultActions.andExpect(status().isOk());
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        Listing responseListing = objectMapper.readValue(responseBody, Listing.class);

        assertEquals(listing.getTitle(), responseListing.getTitle());
    }

    @Test
    void updateListing_successfully() throws Exception {
        // Given
        Long id = 1L;
        Listing listing = new Listing();
        listing.setTitle("Updated Listing");
        listing.setCreatedAt(LocalDateTime.now());

        when(listingService.updateListing(anyLong(), any(Listing.class))).thenReturn(listing);

        String requestBody = objectMapper.writeValueAsString(listing);

        // When
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/listings/{id}/update", id)
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON));

        // Then
        resultActions.andExpect(status().isOk());
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        Listing responseListing = objectMapper.readValue(responseBody, Listing.class);

        assertEquals(listing.getTitle(), responseListing.getTitle());
    }

    @Test
    void deleteListing_successfully() throws Exception {
        // Given
        Long id = 1L;

        // When
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/listings/{id}/delete", id)
                .contentType(MediaType.APPLICATION_JSON));

        // Then
        resultActions.andExpect(status().isOk());
    }

    @Test
    void getListingById_successfully() throws Exception {
        // Given
        Long id = 1L;
        Listing listing = new Listing();
        listing.setId(id);
        listing.setTitle("Sample Listing");
        listing.setCreatedAt(LocalDateTime.now());

        when(listingService.getListingById(anyLong())).thenReturn(listing);

        // When
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/listings/{id}", id)
                .contentType(MediaType.APPLICATION_JSON));

        // Then
        resultActions.andExpect(status().isOk());
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        Listing responseListing = objectMapper.readValue(responseBody, Listing.class);

        assertEquals(listing.getId(), responseListing.getId());
    }

    @Test
    void getActiveListings_successfully() throws Exception {
        // Given
        Long userId = 1L;
        Listing listing = new Listing();
        listing.setId(1L);
        listing.setTitle("Active Listing");
        listing.setStatus(ListingStatus.ACTIVE);

        when(listingService.getListingsByStatus(anyLong(), any(ListingStatus.class)))
                .thenReturn(Collections.singletonList(listing));

        // When
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/listings/active")
                .param("userId", userId.toString())
                .contentType(MediaType.APPLICATION_JSON));

        // Then
        resultActions.andExpect(status().isOk());
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        List<Listing> responseListings = objectMapper.readValue(responseBody, objectMapper.getTypeFactory().constructCollectionType(List.class, Listing.class));

        assertEquals(1, responseListings.size());
        assertEquals(listing.getStatus(), responseListings.get(0).getStatus());
    }

    @Test
    void getPassiveListings_successfully() throws Exception {
        // Given
        Long userId = 1L;
        Listing listing = new Listing();
        listing.setId(1L);
        listing.setTitle("Passive Listing");
        listing.setStatus(ListingStatus.PASSIVE);

        when(listingService.getListingsByStatus(anyLong(), any(ListingStatus.class)))
                .thenReturn(Collections.singletonList(listing));

        // When
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/listings/passive")
                .param("userId", userId.toString())
                .contentType(MediaType.APPLICATION_JSON));

        // Then
        resultActions.andExpect(status().isOk());
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        List<Listing> responseListings = objectMapper.readValue(responseBody, objectMapper.getTypeFactory().constructCollectionType(List.class, Listing.class));

        assertEquals(1, responseListings.size());
        assertEquals(listing.getStatus(), responseListings.get(0).getStatus());
    }

    @Test
    void updateListingStatus_successfully() throws Exception {
        // Given
        Long id = 1L;
        ListingStatus status = ListingStatus.ACTIVE;
        Listing listing = new Listing();
        listing.setId(id);
        listing.setStatus(status);

        when(listingService.updateListingStatus(anyLong(), any(ListingStatus.class))).thenReturn(listing);

        // When
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/listings/{id}/status", id)
                .param("status", status.name())
                .contentType(MediaType.APPLICATION_JSON));

        // Then
        resultActions.andExpect(status().isOk());
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        Listing responseListing = objectMapper.readValue(responseBody, Listing.class);

        assertEquals(status, responseListing.getStatus());
    }
}