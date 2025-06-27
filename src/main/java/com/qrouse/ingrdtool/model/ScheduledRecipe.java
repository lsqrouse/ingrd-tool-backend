package com.qrouse.ingrdtool.model;

import lombok.*;

import java.time.Instant;

import org.hibernate.validator.constraints.UniqueElements;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.annotation.Nonnull;
import jakarta.persistence.*;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "scheduled_recipes_test")
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ScheduledRecipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    @NonNull
    public Long recipeDefId;
    @NonNull
    public Instant scheduledDate;
}
