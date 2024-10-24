package com.torre.backend.inventory.controllers;

import com.torre.backend.authorization.annotations.CasbinFilter;
import com.torre.backend.authorization.enums.StatusEnum;
import com.torre.backend.common.dtos.QueryParamsDto;
import com.torre.backend.common.dtos.ResponseDto;
import com.torre.backend.inventory.dto.CategoryDto;
import com.torre.backend.inventory.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/category")
@Tag(name = "Categories")
@SecurityScheme(
        name = "bearerToken",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT"
)
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    @Operation(
            summary = "Endpoint para listar y filtrar todas las categorias "
    )
    @SecurityRequirement(name = "bearerToken")
    @CasbinFilter
    public ResponseEntity<?> getAllCategories(@ModelAttribute QueryParamsDto query) {
        Page<CategoryDto> categories = this.categoryService.filterCategories(query);
        return ResponseEntity.ok(new ResponseDto<>(true,"Operation successful",categories));
    }

    @PostMapping
    @Operation(summary = "Endpoint para crear una nueva categoria")
    @SecurityRequirement(name = "bearerToken")
    @CasbinFilter
    public ResponseEntity<?> createCategory(@RequestBody CategoryDto categoryDto, HttpServletRequest request) {
        String username = request.getAttribute("username").toString();
        this.categoryService.createCategory(categoryDto, username);
        return ResponseEntity.ok(new ResponseDto<>(true,"Operation successful",null));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Endpoint para actualizar una categoria")
    @SecurityRequirement(name = "bearerToken")
    @CasbinFilter
    public ResponseEntity<?> updateCategory(@PathVariable Long id, @RequestBody CategoryDto categoryDto, HttpServletRequest request) {
        String username = request.getAttribute("username").toString();
        this.categoryService.updateCategory(id, categoryDto, username);
        return ResponseEntity.ok(new ResponseDto<>(true,"Operation successful",null));
    }

    @PutMapping("/{id}/activate")
    @Operation(summary = "Endpoint para activar una categoria")
    @SecurityRequirement(name = "bearerToken")
    @CasbinFilter
    public ResponseEntity<?> activateCategory(@PathVariable Long id, HttpServletRequest request) {
        String username = request.getAttribute("username").toString();
        this.categoryService.updateCategoryStatus(id, StatusEnum.ACTIVE,username);
        return ResponseEntity.ok(new ResponseDto<>(true,"Operation successful",null));
    }

    @PutMapping("/{id}/inactivate")
    @Operation(summary = "Endpoint para inactivar una categoria")
    @SecurityRequirement(name = "bearerToken")
    @CasbinFilter
    public ResponseEntity<?> inactivateCategory(@PathVariable Long id, HttpServletRequest request) {
        String username = request.getAttribute("username").toString();
        this.categoryService.updateCategoryStatus(id, StatusEnum.INACTIVE,username);
        return ResponseEntity.ok(new ResponseDto<>(true,"Operation successful",null));
    }


}
