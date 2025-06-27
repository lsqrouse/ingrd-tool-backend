package com.qrouse.ingrdtool.model;

import lombok.*;

import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "stored_ingredients_test")
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class StoredIngredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ingredient_def_id")
    public IngredientDef ingredientDef ;
    @NonNull
    public Instant expirationDate;
    public float quantity;
}
