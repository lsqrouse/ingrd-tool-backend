package com.qrouse.ingrdtool.service;

import com.qrouse.ingrdtool.model.IngredientDef;
import com.qrouse.ingrdtool.model.RecipeDef;
import com.qrouse.ingrdtool.model.RecipeIngredient;
import com.qrouse.ingrdtool.model.db.IngredientDefRepository;
import com.qrouse.ingrdtool.model.db.RecipeDefRepository;
import com.qrouse.ingrdtool.model.db.RecipeIngredientRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.lang.reflect.Array;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

@Component
class Initializer implements CommandLineRunner {

    private final RecipeDefRepository repository;
    private final RecipeIngredientRepository recipeIngredientRepository;
    private final IngredientDefRepository ingredientDefRepository;

    public Initializer(RecipeDefRepository repository, RecipeIngredientRepository recipeIngredientRepository,
    IngredientDefRepository ingredientDefRepository) {
        this.repository = repository;
        this.recipeIngredientRepository = recipeIngredientRepository;
        this.ingredientDefRepository = ingredientDefRepository;
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
    }
}
