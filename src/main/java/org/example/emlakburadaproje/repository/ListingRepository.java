package org.example.emlakburadaproje.repository;


import org.example.emlakburadaproje.model.Listing;
import org.example.emlakburadaproje.model.ListingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ListingRepository extends JpaRepository<Listing, Long> {
    List<Listing> findByUserIdAndStatus(Long userId, ListingStatus status);
}