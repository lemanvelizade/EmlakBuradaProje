package org.example.emlakburadaproje.service;

import org.example.emlakburadaproje.model.Advert;
import org.example.emlakburadaproje.model.UserAdvert;
import org.example.emlakburadaproje.repository.AdvertRepository;
import org.example.emlakburadaproje.repository.UserAdvertRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
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
public class AdvertServiceTest {

    @Mock
    private AdvertRepository advertRepository;

    @Mock
    private UserAdvertRepository userAdvertRepository;

    @InjectMocks
    private AdvertService advertService;

    @Test
    public void getAdverts_successfully() {
        // Given
        Advert advert1 = new Advert();
        advert1.setId(1L);
        Advert advert2 = new Advert();
        advert2.setId(2L);
        when(advertRepository.findAll()).thenReturn(Arrays.asList(advert1, advert2));

        // When
        List<Advert> results = advertService.getAdverts();

        // Then
        assertEquals(2, results.size());
        verify(advertRepository, times(1)).findAll();
    }

    @Test
    public void purchaseAdvert_successfully() {
        // Given
        Long userId = 1L;
        Long advertId = 2L;

        Advert advert = new Advert();
        advert.setId(advertId);
        advert.setListingCount(10);
        advert.setValidity(30);

        UserAdvert userAdvert = new UserAdvert();
        userAdvert.setUserId(userId);
        userAdvert.setPackageId(advertId);
        userAdvert.setRemainingListings(10);
        userAdvert.setExpiryDate(LocalDateTime.now().plusDays(30));

        when(advertRepository.findById(anyLong())).thenReturn(Optional.of(advert));
        when(userAdvertRepository.save(any(UserAdvert.class))).thenReturn(userAdvert);

        // When
        UserAdvert result = advertService.purchaseAdvert(userId, advertId);

        // Then
        assertEquals(userId, result.getUserId());
        assertEquals(advertId, result.getPackageId());
        assertEquals(10, result.getRemainingListings());
        verify(advertRepository, times(1)).findById(anyLong());
        verify(userAdvertRepository, times(1)).save(any(UserAdvert.class));
    }

    @Test
    public void purchaseAdvert_notFound() {
        // Given
        Long userId = 1L;
        Long advertId = 2L;

        when(advertRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Then
        assertThrows(RuntimeException.class, () -> advertService.purchaseAdvert(userId, advertId));
    }

    @Test
    public void getUserPackages_successfully() {
        // Given
        UserAdvert userAdvert1 = new UserAdvert();
        userAdvert1.setUserId(1L);
        UserAdvert userAdvert2 = new UserAdvert();
        userAdvert2.setUserId(1L);
        when(userAdvertRepository.findByUserId(anyLong())).thenReturn(Arrays.asList(userAdvert1, userAdvert2));

        // When
        List<UserAdvert> results = advertService.getUserPackages(1L);

        // Then
        assertEquals(2, results.size());
        assertEquals(1L, results.get(0).getUserId());
        verify(userAdvertRepository, times(1)).findByUserId(anyLong());
    }

    @Test
    public void createAdvert_successfully() {
        // Given
        Advert advert = new Advert();

        Advert savedAdvert = new Advert();
        savedAdvert.setId(1L);

        when(advertRepository.save(any(Advert.class))).thenReturn(savedAdvert);

        // When
        Advert result = advertService.createAdvert(advert);

        // Then
        assertEquals(savedAdvert.getId(), result.getId());
        verify(advertRepository, times(1)).save(any(Advert.class));
    }
}