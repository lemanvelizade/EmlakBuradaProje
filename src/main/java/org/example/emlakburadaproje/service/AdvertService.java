package org.example.emlakburadaproje.service;

import lombok.RequiredArgsConstructor;

import org.example.emlakburadaproje.model.Advert;
import org.example.emlakburadaproje.model.UserAdvert;
import org.example.emlakburadaproje.repository.AdvertRepository;
import org.example.emlakburadaproje.repository.UserAdvertRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional

public class AdvertService {


    private final AdvertRepository advertRepository;


    private final UserAdvertRepository userAdvertRepository;

    public List<Advert> getAdverts() {
        return advertRepository.findAll();
    }

    public UserAdvert purchaseAdvert(Long userId, Long advertId) {
        Advert advert = advertRepository.findById(advertId).orElseThrow(() -> new RuntimeException("Package not found"));

        UserAdvert userAdvert = new UserAdvert();
        userAdvert.setUserId(userId);
        userAdvert.setPackageId(advertId);
        userAdvert.setRemainingListings(advert.getListingCount());
        userAdvert.setExpiryDate(LocalDateTime.now().plusDays(advert.getValidity()));

        return userAdvertRepository.save(userAdvert);
    }

    public List<UserAdvert> getUserPackages(Long userId) {
        return userAdvertRepository.findByUserId(userId);
    }

    public Advert createAdvert(Advert advert) {
        return advertRepository.save(advert);
    }
}
