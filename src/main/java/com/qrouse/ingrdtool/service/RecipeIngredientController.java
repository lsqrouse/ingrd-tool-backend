package com.qrouse.ingrdtool.service;

import com.qrouse.ingrdtool.db.IngredientDefRepository;
import com.qrouse.ingrdtool.db.RecipeIngredientRepository;
import com.qrouse.ingrdtool.model.IngredientDef;
import com.qrouse.ingrdtool.model.RecipeIngredient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class RecipeIngredientController {
    private final Logger LOG = LoggerFactory.getLogger(RecipeIngredientController.class);
    private RecipeIngredientRepository repository;
    private IngredientDefRepository ingredientDefRepository;

    public RecipeIngredientController(RecipeIngredientRepository repository, IngredientDefRepository ingredientDefRepository) {
        this.repository = repository;
        this.ingredientDefRepository = ingredientDefRepository;
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
    ResponseEntity<?> createRecipe(@RequestBody RecipeIngredient ingredient) throws URISyntaxException {
        LOG.info("Request to create RecipeIngredient: {}", ingredient);

        // Make sure all ingredientDefs are stored in DB
        try {
            IngredientDef existingIngredientDef = ingredientDefRepository.findByIngredientName(ingredient.getIngredientDef().getIngredientName()).get();
            if (!existingIngredientDef.getTtl().equals(ingredient.getIngredientDef().getTtl())) {
                LOG.info("Ingredient name and ttl is {} {}, existing def is {} {}", ingredient.getIngredientDef().getIngredientName(), ingredient.getIngredientDef().getTtl(), existingIngredientDef.getIngredientName(), existingIngredientDef.getTtl());
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Ingredient with same name of " + existingIngredientDef.getIngredientName() + ", but different TTL already exists. TTLs must match");
            }
            ingredient.setIngredientDef(existingIngredientDef);
        } catch (NoSuchElementException e) {
            IngredientDef savedIngredientDef = ingredientDefRepository.save(ingredient.getIngredientDef());
            ingredient.setIngredientDef(savedIngredientDef);
        }



        RecipeIngredient result = repository.save(ingredient);
        return ResponseEntity.created(new URI("/api/recipeIngredient/" + ingredient.getId()))
                .body(result);

        
    }
}
