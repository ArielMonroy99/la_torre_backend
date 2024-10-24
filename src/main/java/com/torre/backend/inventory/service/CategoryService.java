package com.torre.backend.inventory.service;

import com.torre.backend.authorization.enums.StatusEnum;
import com.torre.backend.common.dtos.QueryParamsDto;
import com.torre.backend.common.exceptions.BaseException;
import com.torre.backend.common.utils.PageableMapper;
import com.torre.backend.inventory.dto.CategoryDto;
import com.torre.backend.inventory.entities.Category;
import com.torre.backend.inventory.mappers.CategoryMapper;
import com.torre.backend.inventory.repository.CategoryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Page<CategoryDto> filterCategories(QueryParamsDto queryParamsDto) {
        String filter = queryParamsDto.getFilter();
        Pageable page = PageableMapper.buildPage(queryParamsDto);
        Page<Category> categoryPage = categoryRepository.filterCategory(filter, page);
        return categoryPage.map(CategoryMapper::toDto);
    }

    public void createCategory(CategoryDto categoryDto, String username) {
        Category category = CategoryMapper.toEntity(categoryDto);
        category.setCreatedBy(username);
        categoryRepository.save(category);
    }

    public void updateCategory(Long id, CategoryDto categoryDto, String username) {
        Category category = categoryRepository.findById(id).orElse(null);
        if (category == null) throw new BaseException(HttpStatus.NOT_FOUND,"Category not found");
        Category updatedCategory = CategoryMapper.toEntity(categoryDto);
        updatedCategory.setId(id);
        updatedCategory.setUpdatedBy(username);
        categoryRepository.save(updatedCategory);
    }

    public void updateCategoryStatus(Long id, StatusEnum statusEnum, String username) {
        Category category = categoryRepository.findById(id).orElse(null);
        if (category == null) throw new BaseException(HttpStatus.NOT_FOUND,"Category not found");
        category.setStatus(statusEnum);
        category.setUpdatedBy(username);
        categoryRepository.save(category);
    }
}
