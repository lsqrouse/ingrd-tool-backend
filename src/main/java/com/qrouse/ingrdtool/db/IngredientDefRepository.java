package com.qrouse.ingrdtool.db;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.qrouse.ingrdtool.model.IngredientDef;

public interface IngredientDefRepository extends JpaRepository<IngredientDef, Long> {
    Optional<IngredientDef> findByIngredientName(String ingredientName);
}
