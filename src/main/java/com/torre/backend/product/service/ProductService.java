package com.torre.backend.product.service;

import static com.torre.backend.common.utils.PageableMapper.buildPage;

import com.torre.backend.authorization.enums.StatusEnum;
import com.torre.backend.common.exceptions.BaseException;
import com.torre.backend.inventory.entities.Category;
import com.torre.backend.inventory.repository.CategoryRepository;
import com.torre.backend.product.dto.CreateProductDto;
import com.torre.backend.product.dto.ProductDto;
import com.torre.backend.product.dto.ProductQueryParamsDto;
import com.torre.backend.product.entities.Product;
import com.torre.backend.product.mappers.ProductMapper;
import com.torre.backend.product.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ProductService {

  private final ProductRepository productRepository;
  private final CategoryRepository categoryRepository;
  private final ProductMapper productMapper;

  public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository,
      ProductMapper productMapper) {
    this.productRepository = productRepository;
    this.categoryRepository = categoryRepository;
    this.productMapper = productMapper;
  }

  public void createProduct(CreateProductDto productDto, String username) {
      if (productDto == null) {
          throw new BaseException(HttpStatus.BAD_REQUEST, "Errores de validación");
      }
    Category category = categoryRepository.findById(productDto.getCategoryId())
        .orElseThrow(() -> new BaseException(HttpStatus.NOT_FOUND, "Categoria no encontrada"));
    Product product = productMapper.createEntity(productDto, category);
    product.setCreatedBy(username);
    productRepository.save(product);
    log.info("Product created successfully: {}", product);
  }

  public Page<ProductDto> getActivatedProducts(ProductQueryParamsDto queryParamsDto) {
    String filter = "%" + queryParamsDto.getFilter() + "%";
    String productName = "%" + queryParamsDto.getProductName() + "%";
    Page<Product> productPage = productRepository.filterActivatedProducts(filter, productName,
        buildPage(queryParamsDto));
    log.info("page {}", productPage.getContent());
    return productPage.map(productMapper::toDto);
  }

  public Page<ProductDto> getProducts(ProductQueryParamsDto queryParamsDto) {
    String filter = "%" + queryParamsDto.getFilter() + "%";
    String productName = "%" + queryParamsDto.getProductName() + "%";
    Page<Product> productPage = productRepository.filterProducts(filter, productName,
        buildPage(queryParamsDto));
    log.info("page {}", productPage.getContent());
    return productPage.map(productMapper::toDto);
  }

  public void updateProduct(Long id, CreateProductDto productDto, String username) {
      if (productDto == null) {
          throw new BaseException(HttpStatus.BAD_REQUEST, "Errores de validación");
      }
    Category category = categoryRepository.findById(productDto.getCategoryId())
        .orElseThrow(() -> new BaseException(HttpStatus.NOT_FOUND, "Categoría no encontrada"));
    Product product = productRepository.findById(id)
        .orElseThrow(() -> new BaseException(HttpStatus.NOT_FOUND, "Producto no encontrado"));
    productMapper.updateEntity(product, productDto, category);
    product.setUpdatedBy(username);
    productRepository.save(product);
  }

  public void updateStatus(Long id, String username, StatusEnum statusEnum) {
    Product product = productRepository.findById(id).orElse(null);
      if (product == null) {
          throw new BaseException(HttpStatus.NOT_FOUND, "Category not found");
      }
    product.setStatus(statusEnum);
    product.setUpdatedBy(username);
    productRepository.save(product);
  }

  public Product getProductById(Long id) {
    return productRepository.findById(id)
        .orElseThrow(() -> new BaseException(HttpStatus.NOT_FOUND, "Product not found"));
  }

}
