package com.torre.backend.product.mappers;

import com.torre.backend.inventory.entities.Category;
import com.torre.backend.inventory.mappers.CategoryMapper;
import com.torre.backend.product.dto.CreateProductDto;
import com.torre.backend.product.dto.ProductDto;
import com.torre.backend.product.entities.Product;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {
    public Product createEntity(@NotNull CreateProductDto createProductDto, Category category) {
        Product product = new Product();
        product.setName(createProductDto.getName());
        product.setPrice(createProductDto.getPrice());
        product.setCategory(category);
        return product;
    }

    public ProductDto toDto(@NotNull Product product) {
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setName(product.getName());
        productDto.setPrice(product.getPrice());
        productDto.setCategory(CategoryMapper.toDto(product.getCategory()));
        return productDto;
    }

    public void updateEntity(@NotNull Product product, CreateProductDto createProductDto, Category category) {
        product.setName(createProductDto.getName());
        product.setPrice(createProductDto.getPrice());
        product.setCategory(category);
    }
}
