package org.example.emlakburadaproje.controller;

import lombok.RequiredArgsConstructor;

import org.example.emlakburadaproje.model.Advert;
import org.example.emlakburadaproje.model.Listing;
import org.example.emlakburadaproje.model.UserAdvert;
import org.example.emlakburadaproje.service.AdvertService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/adverts")
@RequiredArgsConstructor
public class AdvertController {


    private final AdvertService advertService;

    @GetMapping
    public List<Advert> getPackages() {
        return advertService.getAdverts();
    }

    @PostMapping
    public ResponseEntity<Advert> createAdvert(@RequestBody Advert advert) {
        Advert newAdvert = advertService.createAdvert(advert);
        return ResponseEntity.ok(newAdvert);
    }

    @PostMapping("/purchase")
    public UserAdvert purchasePackage(@RequestParam Long userId, @RequestParam Long packageId) {
        return advertService.purchaseAdvert(userId, packageId);
    }

    @GetMapping("/user")
    public List<UserAdvert> getUserPackages(@RequestParam Long userId) {
        return advertService.getUserPackages(userId);
    }
}