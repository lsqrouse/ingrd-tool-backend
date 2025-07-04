package com.qrouse.ingrdtool.model;

import lombok.*;

import java.time.Duration;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.annotation.Nonnull;
import jakarta.persistence.*;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "ingredients_def_test")
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class IngredientDef {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    @Nonnull
    public String ingredientName;
    public Duration ttl;
}
