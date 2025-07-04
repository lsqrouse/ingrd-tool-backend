package com.qrouse.ingrdtool.service;

import com.qrouse.ingrdtool.db.IngredientDefRepository;
import com.qrouse.ingrdtool.db.RecipeDefRepository;
import com.qrouse.ingrdtool.db.RecipeIngredientRepository;
import com.qrouse.ingrdtool.db.ScheduledRecipeRepository;
import com.qrouse.ingrdtool.db.StoredIngredientRepository;
import com.qrouse.ingrdtool.model.IngredientDef;
import com.qrouse.ingrdtool.model.RecipeDef;
import com.qrouse.ingrdtool.model.RecipeIngredient;
import com.qrouse.ingrdtool.model.ScheduledRecipe;
import com.qrouse.ingrdtool.model.StoredIngredient;

import jakarta.persistence.criteria.CriteriaBuilder.In;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Component
class Initializer implements CommandLineRunner {
        private final Logger LOG = LoggerFactory.getLogger(Initializer.class);


    private final RecipeDefRepository repository;
    private final RecipeIngredientRepository recipeIngredientRepository;
    private final IngredientDefRepository ingredientDefRepository;
    private final ScheduledRecipeRepository scheduledRecipeRepository;
    StoredIngredientRepository storedIngredientRepository;

    public Initializer(RecipeDefRepository repository, RecipeIngredientRepository recipeIngredientRepository,
    IngredientDefRepository ingredientDefRepository, ScheduledRecipeRepository scheduledRecipeRepository, StoredIngredientRepository storedIngredientRepository) {
        this.repository = repository;
        this.recipeIngredientRepository = recipeIngredientRepository;
        this.ingredientDefRepository = ingredientDefRepository;
        this.scheduledRecipeRepository = scheduledRecipeRepository;
        this.storedIngredientRepository = storedIngredientRepository;
    }

    @Override
    public void run(String... strings) {
        String[] types = {"dinner"};
        IngredientDef cheddarDef = new IngredientDef();
        cheddarDef.setIngredientName("Cheddar Cheese");
        cheddarDef.setTtl(Duration.ofDays(7));
        cheddarDef = ingredientDefRepository.save(cheddarDef);

        IngredientDef butterDef = new IngredientDef();
        butterDef.setIngredientName("Butter");
        butterDef.setTtl(Duration.ofDays(365));
        butterDef = ingredientDefRepository.save(butterDef);

        IngredientDef pastaDef = new IngredientDef();
        pastaDef.setIngredientName("Macaroni Pasta");
        pastaDef.setTtl(Duration.ZERO);
        pastaDef = ingredientDefRepository.save(pastaDef);

        StoredIngredient pantryCheddar = new StoredIngredient(cheddarDef, Instant.now().plus(cheddarDef.ttl));
        pantryCheddar = storedIngredientRepository.save(pantryCheddar);
        StoredIngredient pantryButter = new StoredIngredient(butterDef, Instant.now().plus(butterDef.ttl));
        pantryButter = storedIngredientRepository.save(pantryButter);
        StoredIngredient pantryPasta = new StoredIngredient(pastaDef, Instant.MAX);
        pantryPasta = storedIngredientRepository.save(pantryPasta);



        RecipeIngredient cheddar = new RecipeIngredient();
        cheddar.setIngredientDef(cheddarDef);
        cheddar.setQuantity(1.0f);
        cheddar = recipeIngredientRepository.save(cheddar);

        RecipeIngredient butter = new RecipeIngredient();
        butter.setIngredientDef(butterDef);
        butter.setQuantity(2.0f);
        butter = recipeIngredientRepository.save(butter);

        RecipeIngredient pasta = new RecipeIngredient();
        pasta.setIngredientDef(pastaDef);
        pasta.setQuantity(10.0f);
        pasta = recipeIngredientRepository.save(pasta);

        List<RecipeIngredient> ingredientList = new ArrayList<>();
        ingredientList.add(cheddar);
        ingredientList.add(butter);
        ingredientList.add(pasta);


        RecipeDef macNCheese = new RecipeDef("Mac N CHeese", types, ingredientList);
        repository.save(macNCheese);

        ScheduledRecipe schedule = new ScheduledRecipe(macNCheese.getId(), Instant.now().truncatedTo(ChronoUnit.DAYS));
        LOG.info("added scheduled recipe with {}", Instant.now().truncatedTo(ChronoUnit.DAYS));
        scheduledRecipeRepository.save(schedule);
    }
}
