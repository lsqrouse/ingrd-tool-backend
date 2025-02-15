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
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
class Initializer implements CommandLineRunner {
        private final Logger LOG = LoggerFactory.getLogger(Initializer.class);


    private final RecipeDefRepository repository;
    private final RecipeIngredientRepository recipeIngredientRepository;
    private final IngredientDefRepository ingredientDefRepository;
    private final ScheduledRecipeRepository scheduledRecipeRepository;

    public Initializer(RecipeDefRepository repository, RecipeIngredientRepository recipeIngredientRepository,
    IngredientDefRepository ingredientDefRepository, ScheduledRecipeRepository scheduledRecipeRepository) {
        this.repository = repository;
        this.recipeIngredientRepository = recipeIngredientRepository;
        this.ingredientDefRepository = ingredientDefRepository;
        this.scheduledRecipeRepository = scheduledRecipeRepository;
    }

    @Override
    public void run(String... strings) {
        String[] types = {"dinner"};
        IngredientDef cheddarDef = new IngredientDef();
        cheddarDef.setIngredientName("Cheddar Cheese");
        cheddarDef.setTtl("1 week");
        cheddarDef = ingredientDefRepository.save(cheddarDef);

        IngredientDef butterDef = new IngredientDef();
        butterDef.setIngredientName("Butter");
        butterDef.setTtl("1 year");
        butterDef = ingredientDefRepository.save(butterDef);

        IngredientDef pastaDef = new IngredientDef();
        pastaDef.setIngredientName("Macaroni Pasta");
        pastaDef.setTtl("forever");
        pastaDef = ingredientDefRepository.save(pastaDef);




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

        ScheduledRecipe schedule = new ScheduledRecipe("test", macNCheese, LocalDate.now());
        LOG.info("added scheduled recipe with {}", LocalDate.now());
        scheduledRecipeRepository.save(schedule);
    }
}
