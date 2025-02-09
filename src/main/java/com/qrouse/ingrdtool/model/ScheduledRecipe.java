package com.qrouse.ingrdtool.model;

import lombok.*;

import java.sql.Date;

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
    public String recipeName;  
    @Nonnull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "recipe_def_id")
    public RecipeDef recipeDef;
    public Date scheduledDate;
}
