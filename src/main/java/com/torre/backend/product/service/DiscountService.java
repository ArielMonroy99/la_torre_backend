package com.torre.backend.product.service;

import com.torre.backend.authorization.enums.StatusEnum;
import com.torre.backend.common.dtos.QueryParamsDto;
import com.torre.backend.common.exceptions.BaseException;
import com.torre.backend.common.utils.PageableMapper;
import com.torre.backend.product.dto.CreateDiscountDto;
import com.torre.backend.product.dto.DiscountDto;
import com.torre.backend.product.entities.Discount;
import com.torre.backend.product.entities.Product;
import com.torre.backend.product.mappers.DiscountMapper;
import com.torre.backend.product.repository.DiscountRepository;
import com.torre.backend.product.utils.CodeGenerator;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class DiscountService {

  private final DiscountRepository discountRepository;
  private final ProductService productService;
  private final CodeGenerator codeGenerator;

  public DiscountService(DiscountRepository discountRepository, ProductService productService,
      CodeGenerator codeGenerator) {
    this.discountRepository = discountRepository;
    this.productService = productService;
    this.codeGenerator = codeGenerator;
  }

  public void createDiscount(CreateDiscountDto createDiscountDto, String username) {
    List<Product> products = new ArrayList<>();
    for (Long productId : createDiscountDto.getProductIdList()) {
      products.add(productService.getProductById(productId));
    }
    Discount discount = DiscountMapper.createEntity(createDiscountDto);
    discount.setProducts(products);
    discount.setCreatedBy(username);
    discountRepository.save(discount);
  }

  public void updateDiscount(Long id, CreateDiscountDto createDiscountDto, String username) {
    Discount discount = discountRepository.findById(id).orElse(null);
    if (discount == null) {
      throw new BaseException(HttpStatus.NOT_FOUND, "Discount not found");
    }
    DiscountMapper.updateEntity(id, createDiscountDto, discount);
    List<Product> products = new ArrayList<>();
    for (Long productId : createDiscountDto.getProductIdList()) {
      products.add(productService.getProductById(productId));
    }
    discount.setUpdatedBy(username);
    discount.setProducts(products);
    discountRepository.save(discount);
  }

  public Page<?> filterDiscounts(QueryParamsDto queryParamsDto) {
    String filter = queryParamsDto.getFilter();
    Pageable page = PageableMapper.buildPage(queryParamsDto);
    Page<Discount> discountPage = discountRepository.filterDiscounts(page, filter);
    return discountPage.map(DiscountMapper::toDto);
  }

  public void activateDiscount(Long id, String username) {
    Discount discount = discountRepository.findById(id).orElse(null);
    if (discount == null) {
      throw new BaseException(HttpStatus.NOT_FOUND, "Discount not found");
    }
    discount.setUpdatedBy(username);
    discount.setStatus(StatusEnum.ACTIVE);
    discountRepository.save(discount);
  }

  public void inactivateDiscount(Long id, String username) {
    Discount discount = discountRepository.findById(id).orElse(null);
    if (discount == null) {
      throw new BaseException(HttpStatus.NOT_FOUND, "Discount not found");
    }
    discount.setUpdatedBy(username);
    discount.setStatus(StatusEnum.INACTIVE);
    discountRepository.save(discount);
  }

  public Discount findDiscountByProductId(Long productId) {
    return discountRepository.findDiscountByProductId(productId);
  }

  public String createCode(Long discountId, String username) {
    Discount discount = discountRepository.findById(discountId).orElse(null);
    if (discount == null) {
      throw new BaseException(HttpStatus.NOT_FOUND, "Discount not found");
    }
    String code = codeGenerator.generateCode();
    discount.setCode(code);
    discount.setUpdatedBy(username);
    discountRepository.save(discount);
    return code;
  }

  public DiscountDto findDiscountByCode(String code) {
    Discount discount = discountRepository.findDiscountByCode(code);
    if (discount == null) {
      throw new BaseException(HttpStatus.NOT_FOUND, "Discount not found");
    }
    return DiscountMapper.toDto(discount);
  }
}

