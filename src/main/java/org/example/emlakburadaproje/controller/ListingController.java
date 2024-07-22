package org.example.emlakburadaproje.controller;

import lombok.RequiredArgsConstructor;

import org.example.emlakburadaproje.model.Advert;
import org.example.emlakburadaproje.model.Listing;
import org.example.emlakburadaproje.model.ListingStatus;
import org.example.emlakburadaproje.service.ListingService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/listings")
@RequiredArgsConstructor
public class ListingController {


    private final ListingService listingService;

    @GetMapping
    public List<Listing> getListings() {
        return listingService.getListings();
    }


    @PostMapping
    public Listing createListing(@RequestBody Listing listing) {
        return listingService.createListing(listing);
    }

    @PutMapping("/{id}/update")
    public Listing updateListing(@PathVariable Long id, @RequestBody Listing listing) {
        return listingService.updateListing(id, listing);
    }

    @DeleteMapping("/{id}/delete")
    public void deleteListing(@PathVariable Long id) {
        listingService.deleteListing(id);
    }

    @GetMapping("/{id}")
    public Listing getListingById(@PathVariable Long id) {
        return listingService.getListingById(id);
    }

    @GetMapping("/active")
    public List<Listing> getActiveListings(@RequestParam Long userId) {
        return listingService.getListingsByStatus(userId, ListingStatus.ACTIVE);
    }

    @GetMapping("/passive")
    public List<Listing> getPassiveListings(@RequestParam Long userId) {
        return listingService.getListingsByStatus(userId, ListingStatus.PASSIVE);
    }

    @PutMapping("/{id}/status")
    public Listing updateListingStatus(@PathVariable Long id, @RequestParam ListingStatus status) {
        return listingService.updateListingStatus(id, status);
    }
}