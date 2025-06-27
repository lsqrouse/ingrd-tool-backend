package com.qrouse.ingrdtool.model.requests;


import java.time.Instant;

import lombok.Data;

@Data
public class ScheduleRecipeRequest {
    private Long recipeDefId;
    private Instant scheduledDate;
}
