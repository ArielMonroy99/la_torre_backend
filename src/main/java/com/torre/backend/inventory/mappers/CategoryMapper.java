package com.torre.backend.inventory.mappers;

import com.torre.backend.inventory.dto.CategoryDto;
import com.torre.backend.inventory.entities.Category;

public class CategoryMapper {
    public static CategoryDto toDto(Category category) {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(category.getId());
        categoryDto.setName(category.getName());
        return categoryDto;
    }
}
