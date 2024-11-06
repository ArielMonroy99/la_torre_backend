package com.torre.backend.product.controllers;

import com.torre.backend.authorization.annotations.CasbinFilter;
import com.torre.backend.authorization.enums.StatusEnum;
import com.torre.backend.common.dtos.ResponseDto;
import com.torre.backend.common.exceptions.BaseException;
import com.torre.backend.product.dto.CreateProductDto;
import com.torre.backend.product.dto.ProductQueryParamsDto;
import com.torre.backend.product.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
@Tag(name = "Products")
@SecurityScheme(
        name = "bearerToken",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT"
)
@Slf4j
public class ProductController {
    public final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }


    @PostMapping()
    @Operation(summary = "Creates a new Product")
    @CasbinFilter()
    public ResponseEntity<?> createProduct(@Valid @RequestBody CreateProductDto createProductDto, HttpServletRequest request) {
        String username = (String) request.getAttribute("username");
        if (username == null) throw new BaseException(HttpStatus.UNAUTHORIZED,"Not logged in");
        productService.createProduct(createProductDto, username);
        return ResponseEntity.ok(new ResponseDto<>(true,"Operation successfully", null));
    }

    @GetMapping("activate")
    @Operation(summary = "List all products paginated with status activate")
    @CasbinFilter()
    public ResponseEntity<?> getActivatedProducts(@ModelAttribute ProductQueryParamsDto queryParams) {
        return ResponseEntity.ok(new ResponseDto<>(true,"Operation successful",productService.getActivatedProducts(queryParams)));
    }

    @GetMapping()
    @Operation(summary = "List all products paginated with any status")
    @CasbinFilter()
    public ResponseEntity<?> getAllProducts(@ModelAttribute ProductQueryParamsDto queryParams) {
        return ResponseEntity.ok(new ResponseDto<>(true,"Operation successful",productService.getProducts(queryParams)));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Updates an existing product with data in request body")
    @CasbinFilter()
    public  ResponseEntity<?> updateProduct(@PathVariable("id") Long id, @Valid @RequestBody() CreateProductDto createProductDto, HttpServletRequest request) {
        String username = (String) request.getAttribute("username");
        if (username == null) throw new BaseException(HttpStatus.UNAUTHORIZED,"Not logged in");
        productService.updateProduct(id, createProductDto, username);
        return ResponseEntity.ok(new ResponseDto<>(true,"Operation successfull", null));
    }

    @PutMapping("/{id}/activate")
    @Operation(summary = "Activates a existing product")
    @CasbinFilter()
    public ResponseEntity<?> activateProduct(@PathVariable("id") Long id, HttpServletRequest request) {
        String username = (String) request.getAttribute("username");
        if (username == null) throw new BaseException(HttpStatus.UNAUTHORIZED,"Not logged in");
        productService.updateStatus(id, username, StatusEnum.valueOf("ACTIVE"));
        return ResponseEntity.ok(new ResponseDto<>(true,"Operation successful", null));
    }

    @PutMapping("/{id}/inactivate")
    @Operation(summary = "Inactivates a existing product")
    @CasbinFilter()
    public ResponseEntity<?> inactivateProduct(@PathVariable("id") Long id, HttpServletRequest request) {
        String username = (String) request.getAttribute("username");
        if (username == null) throw new BaseException(HttpStatus.UNAUTHORIZED,"Not logged in");
        productService.updateStatus(id, username, StatusEnum.valueOf("INACTIVE"));
        return ResponseEntity.ok(new ResponseDto<>(true,"Operation successful", null));
    }

}
