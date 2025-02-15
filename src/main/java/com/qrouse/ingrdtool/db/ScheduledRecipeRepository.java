package com.qrouse.ingrdtool.db;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.qrouse.ingrdtool.model.ScheduledRecipe;

public interface ScheduledRecipeRepository extends JpaRepository<ScheduledRecipe, Long> {
    ScheduledRecipe findByRecipeName(String recipeName);
    Optional<ScheduledRecipe> findByScheduledDate(LocalDate d);
}
