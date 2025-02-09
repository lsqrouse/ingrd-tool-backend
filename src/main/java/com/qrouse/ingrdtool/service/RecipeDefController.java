package com.qrouse.ingrdtool.service;

import com.qrouse.ingrdtool.model.RecipeDef;
import com.qrouse.ingrdtool.model.db.RecipeDefRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class RecipeDefController {
    private final Logger LOG = LoggerFactory.getLogger(RecipeDefController.class);
    private RecipeDefRepository recipeRepository;

    public RecipeDefController(RecipeDefRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @GetMapping("/recipeDef")
    Collection<RecipeDef> getAll() {
        return recipeRepository.findAll();
    }

    @GetMapping("/recipeDef/{id}")
    Optional<RecipeDef> getById(@PathVariable Long id) {
        return recipeRepository.findById(id);
    }

    @PostMapping("/recipeDef")
    ResponseEntity<RecipeDef> createRecipe(@RequestBody RecipeDef recipe) throws URISyntaxException {
        LOG.info("Request to create recipeDefinition: {}", recipe);
        RecipeDef result = recipeRepository.save(recipe);
        return ResponseEntity.created(new URI("/api/recipe/" + recipe.getId()))
                .body(result);
    }
}
