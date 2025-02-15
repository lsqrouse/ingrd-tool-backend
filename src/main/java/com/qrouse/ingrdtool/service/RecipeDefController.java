package com.qrouse.ingrdtool.service;

import com.qrouse.ingrdtool.model.IngredientDef;
import com.qrouse.ingrdtool.model.RecipeDef;
import com.qrouse.ingrdtool.model.RecipeIngredient;
import com.qrouse.ingrdtool.model.db.IngredientDefRepository;
import com.qrouse.ingrdtool.model.db.RecipeDefRepository;
import com.qrouse.ingrdtool.model.db.RecipeIngredientRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class RecipeDefController {
    private final Logger LOG = LoggerFactory.getLogger(RecipeDefController.class);
    private RecipeDefRepository recipeRepository;
    private RecipeIngredientRepository recipeIngredientRepository;
    private IngredientDefRepository ingredientDefRepository;

    public RecipeDefController(RecipeDefRepository recipeRepository, RecipeIngredientRepository recipeIngredientRepository,
    IngredientDefRepository ingredientDefRepository) {
        this.recipeRepository = recipeRepository;
        this.recipeIngredientRepository = recipeIngredientRepository;
        this.ingredientDefRepository = ingredientDefRepository;
    }

    @GetMapping("/recipeDef")
    Collection<RecipeDef> getAll() {
        LOG.info("Getting all recipeDefs...");
        return recipeRepository.findAll();
    }

    @GetMapping("/recipeDef/{id}")
    Optional<RecipeDef> getById(@PathVariable Long id) {
        LOG.info("Getting recipedef {}", id);
        return recipeRepository.findById(id);
    }

    @PostMapping("/recipeDef")
    ResponseEntity<?> createRecipe(@RequestBody RecipeDef recipe) {
        LOG.info("Request to create recipeDefinition: {}", recipe);
        // ArrayList<RecipeIngredient> newIngredientList = new ArrayList<>();
        for (RecipeIngredient ingredient : recipe.getIngredientList()) {
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


            // If no ID was provied, make sure this recipeIngredient has been saved, and populate the ID field
            if (ingredient.getId() == null) {
                RecipeIngredient savedRecipeIngredient = recipeIngredientRepository.save(ingredient);
                ingredient.setId(savedRecipeIngredient.getId());
            }
            

        }
        RecipeDef result = recipeRepository.save(recipe);

        return ResponseEntity.status(HttpStatus.CREATED)
            .body(result);
    }
}
