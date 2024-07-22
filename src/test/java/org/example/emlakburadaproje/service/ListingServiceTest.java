package org.example.emlakburadaproje.service;


import org.example.emlakburadaproje.model.Listing;
import org.example.emlakburadaproje.model.ListingStatus;
import org.example.emlakburadaproje.repository.ListingRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ListingServiceTest {

    @Mock
    private ListingRepository listingRepository;

    @InjectMocks
    private ListingService listingService;

    @Test
    public void createListing_successfully() {
        // Given
        Listing listing = new Listing();
        listing.setTitle("Test Listing");
        listing.setDescription("Description");
        Listing savedListing = new Listing();
        savedListing.setId(1L);
        savedListing.setTitle("Test Listing");
        savedListing.setDescription("Description");
        savedListing.setStatus(ListingStatus.IN_REVIEW);
        savedListing.setCreatedAt(LocalDateTime.now());
        when(listingRepository.save(any(Listing.class))).thenReturn(savedListing);

        // When
        Listing result = listingService.createListing(listing);

        // Then
        assertEquals(savedListing.getId(), result.getId());
        assertEquals(savedListing.getTitle(), result.getTitle());
        assertEquals(savedListing.getDescription(), result.getDescription());
        verify(listingRepository, times(1)).save(any(Listing.class));
    }

    @Test
    public void updateListing_successfully() {
        // Given
        Listing existingListing = new Listing();
        existingListing.setId(1L);
        existingListing.setTitle("Old Title");
        existingListing.setDescription("Old Description");
        existingListing.setStatus(ListingStatus.IN_REVIEW);
        existingListing.setCreatedAt(LocalDateTime.now());

        Listing updatedListing = new Listing();
        updatedListing.setTitle("Updated Title");
        updatedListing.setDescription("Updated Description");

        Listing savedListing = new Listing();
        savedListing.setId(1L);
        savedListing.setTitle("Updated Title");
        savedListing.setDescription("Updated Description");
        savedListing.setStatus(ListingStatus.IN_REVIEW);
        savedListing.setCreatedAt(existingListing.getCreatedAt());
        savedListing.setUpdatedAt(LocalDateTime.now());

        when(listingRepository.findById(anyLong())).thenReturn(Optional.of(existingListing));
        when(listingRepository.save(any(Listing.class))).thenReturn(savedListing);

        // When
        Listing result = listingService.updateListing(1L, updatedListing);

        // Then
        assertEquals(savedListing.getTitle(), result.getTitle());
        assertEquals(savedListing.getDescription(), result.getDescription());
        verify(listingRepository, times(1)).findById(anyLong());
        verify(listingRepository, times(1)).save(any(Listing.class));
    }

    @Test
    public void deleteListing_successfully() {
        // When
        listingService.deleteListing(1L);

        // Then
        verify(listingRepository, times(1)).deleteById(anyLong());
    }

    @Test
    public void getListingById_successfully() {
        // Given
        Listing listing = new Listing();
        listing.setId(1L);
        listing.setTitle("Test Listing");
        when(listingRepository.findById(anyLong())).thenReturn(Optional.of(listing));

        // When
        Listing result = listingService.getListingById(1L);

        // Then
        assertEquals(listing.getId(), result.getId());
        assertEquals(listing.getTitle(), result.getTitle());
        verify(listingRepository, times(1)).findById(anyLong());
    }

    @Test
    public void getListingById_notFound() {
        // When
        when(listingRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Then
        assertThrows(RuntimeException.class, () -> listingService.getListingById(1L));
    }

    @Test
    public void getListingsByStatus_successfully() {
        // Given
        Listing listing1 = new Listing();
        listing1.setId(1L);
        listing1.setStatus(ListingStatus.ACTIVE);
        Listing listing2 = new Listing();
        listing2.setId(2L);
        listing2.setStatus(ListingStatus.ACTIVE);
        when(listingRepository.findByUserIdAndStatus(anyLong(), any(ListingStatus.class)))
                .thenReturn(Arrays.asList(listing1, listing2));

        // When
        List<Listing> results = listingService.getListingsByStatus(1L, ListingStatus.ACTIVE);

        // Then
        assertEquals(2, results.size());
        assertEquals(ListingStatus.ACTIVE, results.get(0).getStatus());
        verify(listingRepository, times(1)).findByUserIdAndStatus(anyLong(), any(ListingStatus.class));
    }

    @Test
    public void updateListingStatus_successfully() {
        // Given
        Listing listing = new Listing();
        listing.setId(1L);
        listing.setStatus(ListingStatus.IN_REVIEW);
        Listing updatedListing = new Listing();
        updatedListing.setId(1L);
        updatedListing.setStatus(ListingStatus.ACTIVE);
        updatedListing.setUpdatedAt(LocalDateTime.now());

        when(listingRepository.findById(anyLong())).thenReturn(Optional.of(listing));
        when(listingRepository.save(any(Listing.class))).thenReturn(updatedListing);

        // When
        Listing result = listingService.updateListingStatus(1L, ListingStatus.ACTIVE);

        // Then
        assertEquals(ListingStatus.ACTIVE, result.getStatus());
        verify(listingRepository, times(1)).findById(anyLong());
        verify(listingRepository, times(1)).save(any(Listing.class));
    }

    @Test
    public void getListings_successfully() {
        // Given
        Listing listing1 = new Listing();
        listing1.setId(1L);
        Listing listing2 = new Listing();
        listing2.setId(2L);
        when(listingRepository.findAll()).thenReturn(Arrays.asList(listing1, listing2));

        // When
        List<Listing> results = listingService.getListings();

        // Then
        assertEquals(2, results.size());
        verify(listingRepository, times(1)).findAll();
    }
}