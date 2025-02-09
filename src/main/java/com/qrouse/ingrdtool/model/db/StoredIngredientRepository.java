package com.qrouse.ingrdtool.model.db;

import java.sql.Date;

import org.springframework.data.jpa.repository.JpaRepository;

import com.qrouse.ingrdtool.model.StoredIngredient;

public interface StoredIngredientRepository extends JpaRepository<StoredIngredient, Long> {
    StoredIngredient findByExpirationDate(Date date);
}
