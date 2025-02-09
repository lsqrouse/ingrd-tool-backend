package com.qrouse.ingrdtool.service;

import com.qrouse.ingrdtool.model.RecipeDef;
import com.qrouse.ingrdtool.model.RecipeIngredient;
import com.qrouse.ingrdtool.model.db.RecipeDefRepository;
import com.qrouse.ingrdtool.model.db.RecipeIngredientRepository;

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
public class RecipeIngredientController {
    private final Logger LOG = LoggerFactory.getLogger(RecipeIngredientController.class);
    private RecipeIngredientRepository repository;

    public RecipeIngredientController(RecipeIngredientRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/recipeIngredient")
    Collection<RecipeIngredient> groups() {
        return repository.findAll();
    }

        @GetMapping("/recipeIngredient/{id}")
    Optional<RecipeIngredient> getById(@PathVariable Long id) {
        return repository.findById(id);
    }

    @PostMapping("/recipeIngredient")
    ResponseEntity<RecipeIngredient> createRecipe(@RequestBody RecipeIngredient ingredient) throws URISyntaxException {
        LOG.info("Request to create RecipeIngredient: {}", ingredient);
        RecipeIngredient result = repository.save(ingredient);
        return ResponseEntity.created(new URI("/api/recipeIngredient/" + ingredient.getId()))
                .body(result);
    }
}
