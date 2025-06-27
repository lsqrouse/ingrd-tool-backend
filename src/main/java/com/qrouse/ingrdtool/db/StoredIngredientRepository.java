package com.qrouse.ingrdtool.db;


import java.time.Instant;

import org.springframework.data.jpa.repository.JpaRepository;

import com.qrouse.ingrdtool.model.StoredIngredient;

public interface StoredIngredientRepository extends JpaRepository<StoredIngredient, Long> {
    StoredIngredient findByExpirationDate(Instant date);
}
