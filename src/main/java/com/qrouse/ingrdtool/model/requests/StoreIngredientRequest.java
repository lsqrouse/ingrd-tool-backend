package com.qrouse.ingrdtool.model.requests;

import lombok.Data;

@Data
public class StoreIngredientRequest {
    private Long ingredientDefId;
    private Long quantity;
}
