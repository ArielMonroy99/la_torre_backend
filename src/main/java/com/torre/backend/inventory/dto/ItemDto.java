package com.torre.backend.inventory.dto;

import com.torre.backend.inventory.enums.ItemType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemDto {
    private Long id;
    private String name;
    private Integer minimumStock;
    private Integer stock;
    private String unit;
    private ItemType type;
    private CategoryDto category;

}
