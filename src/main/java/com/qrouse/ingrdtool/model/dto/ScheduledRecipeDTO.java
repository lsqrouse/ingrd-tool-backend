package com.qrouse.ingrdtool.model.dto;

import java.time.Instant;

import com.qrouse.ingrdtool.model.RecipeDef;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ScheduledRecipeDTO {
    private Instant scheduledDate;
    private RecipeDef recipeDef;
    
}
