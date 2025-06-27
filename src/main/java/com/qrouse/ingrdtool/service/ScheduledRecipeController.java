package com.qrouse.ingrdtool.service;

import com.qrouse.ingrdtool.db.IngredientDefRepository;
import com.qrouse.ingrdtool.db.RecipeDefRepository;
import com.qrouse.ingrdtool.db.RecipeIngredientRepository;
import com.qrouse.ingrdtool.db.ScheduledRecipeRepository;
import com.qrouse.ingrdtool.model.IngredientDef;
import com.qrouse.ingrdtool.model.RecipeDef;
import com.qrouse.ingrdtool.model.RecipeIngredient;
import com.qrouse.ingrdtool.model.ScheduledRecipe;
import com.qrouse.ingrdtool.model.dto.ScheduledRecipeDTO;
import com.qrouse.ingrdtool.model.requests.ScheduleRecipeRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class ScheduledRecipeController {
    private final Logger LOG = LoggerFactory.getLogger(ScheduledRecipeController.class);
    private ScheduledRecipeRepository scheduledRecipeRepository;
    private RecipeDefRepository recipeDefRepository;


    public ScheduledRecipeController(ScheduledRecipeRepository scheduledRecipeRepository, RecipeDefRepository recipeDefRepository) {
        this.scheduledRecipeRepository = scheduledRecipeRepository;
        this.recipeDefRepository = recipeDefRepository;
    }

    @GetMapping("/scheduledRecipes")
    Collection<ScheduledRecipe> getAll() {
        LOG.info("Getting all scheduled recipes...");
        return scheduledRecipeRepository.findAll();
    }

    @GetMapping("/scheduledRecipe")
    ResponseEntity<?> getByDate(@RequestParam("date") Instant date) {
        Instant truncatedDate = date.truncatedTo(ChronoUnit.DAYS);

        LOG.info("Getting scheduledRecipe {}", truncatedDate);
        ScheduledRecipe schedule = scheduledRecipeRepository.findByScheduledDate(truncatedDate);
        if (schedule != null) {
            Optional<RecipeDef> recipeDef = recipeDefRepository.findById(schedule.getRecipeDefId());
            if (!recipeDef.isPresent()) {
                return ResponseEntity.badRequest().body("RecipeDef in the scheduledRecipe was not found!");
            }
            ScheduledRecipeDTO response = new ScheduledRecipeDTO(truncatedDate, recipeDef.get());
            return ResponseEntity.ok().body(response);
        } else {
            return ResponseEntity.notFound().build();
        }
        
    }

    @GetMapping("/weekRecipes")
    ResponseEntity<?> getweekRecipes(@RequestParam("startDate") Instant startDate) {
        Instant truncatedDate = startDate.truncatedTo(ChronoUnit.DAYS);

        LOG.info("Getting scheduledRecipe {}", truncatedDate);
        // Build an array of the scheduled recipes for this week. If a recipe isn't found, just return an empty ScheduledRecipe
        ScheduledRecipeDTO[] scheduledRecipes = new ScheduledRecipeDTO[7];
        for (int i = 0; i < 7; i++) {
            LOG.info("Searching for recipes scheduled on {}...", truncatedDate);
            ScheduledRecipe schedule = scheduledRecipeRepository.findByScheduledDate(truncatedDate);
            if (schedule == null) {
                scheduledRecipes[i] = new ScheduledRecipeDTO(truncatedDate,  null);
            } else {
                Optional<RecipeDef> recipeDef = recipeDefRepository.findById(schedule.getRecipeDefId());
                if (!recipeDef.isPresent()) {
                    return ResponseEntity.badRequest().body("RecipeDef in the scheduledRecipe was not found!");
                }
                scheduledRecipes[i] = new ScheduledRecipeDTO(truncatedDate, recipeDef.get());
            }
            
            truncatedDate = truncatedDate.plus(1, ChronoUnit.DAYS);
        }
        return ResponseEntity.ok().body(scheduledRecipes);       
    }

    @PostMapping("/scheduledRecipes")
    ResponseEntity<?> createRecipe(@RequestBody ScheduledRecipe recipeRequest) throws URISyntaxException{
        LOG.info("Request to create scheduledRecipe: {}", recipeRequest);
        recipeRequest.setScheduledDate(recipeRequest.getScheduledDate().truncatedTo(ChronoUnit.DAYS)); // always have to truncate to days on the instants in case the frontend has added hours to it somehow
        ScheduledRecipe duplicate = scheduledRecipeRepository.findByScheduledDate(recipeRequest.getScheduledDate());
        if (duplicate != null) {
            LOG.info("Recipe with scheduled date already exists, deleting old schedule");
            scheduledRecipeRepository.delete(duplicate);
        }

        ScheduledRecipe result = scheduledRecipeRepository.save(recipeRequest);
        return ResponseEntity.created(new URI("/api/scheduledRecipes/" + result.getId()))
                .body(result);
    }
}
