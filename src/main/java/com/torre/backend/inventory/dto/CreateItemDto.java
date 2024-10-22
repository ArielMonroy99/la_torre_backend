package com.torre.backend.inventory.dto;

import com.torre.backend.inventory.enums.ItemType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateItemDto {
    @NotNull
    @NotEmpty
    private String name;
    @NotNull
    @Min(0)
    private Integer minimumStock;
    @NotNull
    @Min(0)
    private Integer stock;
    @NotNull
    @NotEmpty
    private String unit;
    @NotNull
    @NotEmpty
    private ItemType type;
    @NotNull
    @NotEmpty
    private Long categoryId;
}
