package com.torre.backend.inventory.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateStockRequestDto {
    private Integer stock;
}
