package com.qrouse.ingrdtool.model;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "recipes_defs_test")
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class RecipeDef {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    @NonNull
    public String recipeName;
    @NonNull
    private String[] types;
    @NonNull
    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "recipe_id", referencedColumnName = "id")
    private List<RecipeIngredient> ingredientList;
    private int servings;
}
