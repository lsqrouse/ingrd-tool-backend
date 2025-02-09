package com.qrouse.ingrdtool.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.*;




@Data
@NoArgsConstructor
@Entity
@Table(name = "recipe_ingredients_test")
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class RecipeIngredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    public float quantity;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ingredient_def_id", referencedColumnName = "id")
    private IngredientDef ingredientDef;
}
