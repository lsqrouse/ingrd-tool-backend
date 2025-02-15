package com.qrouse.ingrdtool.service;

import com.qrouse.ingrdtool.db.IngredientDefRepository;
import com.qrouse.ingrdtool.db.RecipeDefRepository;
import com.qrouse.ingrdtool.db.RecipeIngredientRepository;
import com.qrouse.ingrdtool.db.ScheduledRecipeRepository;
import com.qrouse.ingrdtool.model.IngredientDef;
import com.qrouse.ingrdtool.model.RecipeDef;
import com.qrouse.ingrdtool.model.RecipeIngredient;
import com.qrouse.ingrdtool.model.ScheduledRecipe;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Collection;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class ScheduledRecipeController {
    private final Logger LOG = LoggerFactory.getLogger(ScheduledRecipeController.class);
    private ScheduledRecipeRepository scheduledRecipeRepository;

    public ScheduledRecipeController(ScheduledRecipeRepository scheduledRecipeRepository) {
        this.scheduledRecipeRepository = scheduledRecipeRepository;
    }

    @GetMapping("/scheduledRecipes")
    Collection<ScheduledRecipe> getAll() {
        LOG.info("Getting all recipeDefs...");
        return scheduledRecipeRepository.findAll();
    }

    @GetMapping("/scheduledRecipe")
    Optional<ScheduledRecipe> getByDate(@RequestParam("date") String dateString) {
        DateTimeFormatter formatter = new DateTimeFormatterBuilder()
            .appendOptional(DateTimeFormatter.ofPattern("M/dd/yyyy"))
            .appendOptional(DateTimeFormatter.ofPattern("MM/dd/yyyy"))
            .toFormatter();

        formatter = formatter.withLocale(Locale.US);
        LocalDate d = LocalDate.parse(dateString, formatter);
        LOG.info("Getting scheduledRecipe {}", d);
        return scheduledRecipeRepository.findByScheduledDate(d);
    }

    @PostMapping("/scheduledRecipes")
    ResponseEntity<?> createRecipe(@RequestBody ScheduledRecipe recipe) {
        LOG.info("Request to create recipeDefinition: {}", recipe);
        
        return ResponseEntity.status(HttpStatus.CREATED)
            .body("yay");
    }
}
