package com.qrouse.ingrdtool.model.db;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.qrouse.ingrdtool.model.IngredientDef;
import com.qrouse.ingrdtool.model.RecipeIngredient;

public interface RecipeIngredientRepository extends JpaRepository<RecipeIngredient, Long> {
    Optional<RecipeIngredient> findById(Long id);
    Optional<RecipeIngredient> findByIngredientDef(IngredientDef ingredientDef);
}
