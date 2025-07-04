package com.qrouse.ingrdtool.service;

import com.qrouse.ingrdtool.db.IngredientDefRepository;
import com.qrouse.ingrdtool.db.StoredIngredientRepository;
import com.qrouse.ingrdtool.model.IngredientDef;
import com.qrouse.ingrdtool.model.StoredIngredient;
import com.qrouse.ingrdtool.model.requests.StoreIngredientRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.Duration;
import java.time.Instant;
import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class StoredIngredientController {
    private final Logger LOG = LoggerFactory.getLogger(StoredIngredientController.class);
    private IngredientDefRepository ingredientDefRepository;
    private StoredIngredientRepository storedIngredientRepository;

    public StoredIngredientController(IngredientDefRepository ingredientDefRepository, StoredIngredientRepository storedIngredientRepository) {
        this.ingredientDefRepository = ingredientDefRepository;
        this.storedIngredientRepository = storedIngredientRepository;
    }

    @GetMapping("/storedIngredients")
    Collection<StoredIngredient> getAllIngredientDefs() {
        LOG.info("Getting all stored ingredients");
        return storedIngredientRepository.findAll();
    }
    @GetMapping("/storedIngredients/{id}")
    Optional<StoredIngredient> getIngredientDef(@PathVariable Long id) {
        LOG.info("Getting ingredient {}...", id);
        return storedIngredientRepository.findById(id);
    }

    @PostMapping("/storeIngredient")
    ResponseEntity<?> storeIngredient(@RequestBody StoreIngredientRequest storeIngredientRequest) throws URISyntaxException {
        LOG.info("Request to store a new ingredient: {}", storeIngredientRequest);
        IngredientDef ingredientDef = ingredientDefRepository.findById(storeIngredientRequest.getIngredientDefId()).get();
        if (ingredientDef == null) {
            LOG.error("Invalid ingredient def id passed, cannnot store ingredient");
            return ResponseEntity.badRequest().build();
        }
        // when there is no ttl, that means the ingredient "functionally" never expires, so set our expiration date to the furthest date possible
        Instant expirationInstant = ingredientDef.ttl.equals(Duration.ZERO) ? Instant.MAX : Instant.now().plus(ingredientDef.ttl);
        
        StoredIngredient storedIngredient = new StoredIngredient(ingredientDef, expirationInstant);
        StoredIngredient result = storedIngredientRepository.save(storedIngredient);
        return ResponseEntity.created(new URI("/api/storedIngredients/" + result.getId()))
        .body(result);
    }
}
