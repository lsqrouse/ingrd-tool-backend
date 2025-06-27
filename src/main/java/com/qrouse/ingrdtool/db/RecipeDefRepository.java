package com.qrouse.ingrdtool.db;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.qrouse.ingrdtool.model.RecipeDef;


public interface RecipeDefRepository extends JpaRepository<RecipeDef, Long> {
    RecipeDef findByRecipeName(String recipeName);
    Optional findById(Long id);
}
