package org.example.emlakburadaproje.service;

import lombok.RequiredArgsConstructor;

import org.example.emlakburadaproje.model.Advert;
import org.example.emlakburadaproje.model.Listing;
import org.example.emlakburadaproje.model.ListingStatus;
import org.example.emlakburadaproje.repository.ListingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional

public class ListingService {


    private final ListingRepository listingRepository;

    public Listing createListing(Listing listing) {
        listing.setStatus(ListingStatus.IN_REVIEW);
        listing.setCreatedAt(LocalDateTime.now());
        return listingRepository.save(listing);
    }

    public Listing updateListing(Long id, Listing updatedListing) {
        Listing listing = listingRepository.findById(id).orElseThrow(() -> new RuntimeException("Listing not found"));
        listing.setTitle(updatedListing.getTitle());
        listing.setDescription(updatedListing.getDescription());
        listing.setUpdatedAt(LocalDateTime.now());
        return listingRepository.save(listing);
    }

    public void deleteListing(Long id) {
        listingRepository.deleteById(id);
    }

    public Listing getListingById(Long id) {
        return listingRepository.findById(id).orElseThrow(() -> new RuntimeException("Listing not found"));
    }

    public List<Listing> getListingsByStatus(Long userId, ListingStatus status) {
        return listingRepository.findByUserIdAndStatus(userId, status);
    }

    public Listing updateListingStatus(Long id, ListingStatus status) {
        Listing listing = listingRepository.findById(id).orElseThrow(() -> new RuntimeException("Listing not found"));
        listing.setStatus(status);
        listing.setUpdatedAt(LocalDateTime.now());
        return listingRepository.save(listing);
    }

    public List<Listing> getListings() {

            return listingRepository.findAll();

    }
}