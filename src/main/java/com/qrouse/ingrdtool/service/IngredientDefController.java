package com.qrouse.ingrdtool.service;

import com.qrouse.ingrdtool.model.IngredientDef;
import com.qrouse.ingrdtool.model.db.IngredientDefRepository;
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
public class IngredientDefController {
    private final Logger LOG = LoggerFactory.getLogger(IngredientDefController.class);
    private IngredientDefRepository ingredientDefRepository;

    public IngredientDefController(IngredientDefRepository ingredientDefRepository) {
        this.ingredientDefRepository = ingredientDefRepository;
    }

    @GetMapping("/ingredientDef")
    Collection<IngredientDef> getAllIngredientDefs() {
        return ingredientDefRepository.findAll();
    }
    @GetMapping("/ingredientDef/{id}")
    Optional<IngredientDef> getIngredientDef(@PathVariable Long id) {
        return ingredientDefRepository.findById(id);
    }

    @PostMapping("/ingredientDef")
    ResponseEntity<IngredientDef> createIngredientDef(@RequestBody IngredientDef ingredientDef) throws URISyntaxException {
        LOG.info("Request to create ingredient def: {}", ingredientDef);
        IngredientDef result = ingredientDefRepository.save(ingredientDef);
        return ResponseEntity.created(new URI("/api/ingredientDef/" + ingredientDef.getId()))
                .body(result);
    }
}
