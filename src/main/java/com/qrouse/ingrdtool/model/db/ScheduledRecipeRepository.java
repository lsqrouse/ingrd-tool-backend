package com.qrouse.ingrdtool.model.db;

import org.springframework.data.jpa.repository.JpaRepository;

import com.qrouse.ingrdtool.model.ScheduledRecipe;

public interface ScheduledRecipeRepository extends JpaRepository<ScheduledRecipe, Long> {
    ScheduledRecipe findByRecipeName(String recipeName);
}
