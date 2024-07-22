package org.example.emlakburadaproje.repository;


import org.example.emlakburadaproje.model.UserAdvert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserAdvertRepository extends JpaRepository<UserAdvert, Long> {
    List<UserAdvert> findByUserId(Long userId);
}