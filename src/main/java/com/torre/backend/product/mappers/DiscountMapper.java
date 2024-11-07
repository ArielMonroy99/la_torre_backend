package com.torre.backend.product.mappers;

import com.torre.backend.product.dto.CreateDiscountDto;
import com.torre.backend.product.dto.DiscountDto;
import com.torre.backend.product.entities.Discount;

public class DiscountMapper {

  static ProductMapper productMapper = new ProductMapper();

  public static Discount createEntity(CreateDiscountDto createDiscountDto) {
    Discount discountEntity = new Discount();
    discountEntity.setType(createDiscountDto.getType());
    discountEntity.setNewPrice(createDiscountDto.getNewPrice());
    discountEntity.setPercentage(createDiscountDto.getPercentage());
    discountEntity.setStartDate(createDiscountDto.getStartDate());
    discountEntity.setEndDate(createDiscountDto.getEndDate());
    return discountEntity;
  }

  public static void updateEntity(Long id, CreateDiscountDto createDiscountDto,
      Discount discountEntity) {
    discountEntity.setId(id);
    discountEntity.setType(createDiscountDto.getType());
    discountEntity.setNewPrice(createDiscountDto.getNewPrice());
    discountEntity.setPercentage(createDiscountDto.getPercentage());
    discountEntity.setStartDate(createDiscountDto.getStartDate());
    discountEntity.setEndDate(createDiscountDto.getEndDate());
  }

  public static DiscountDto toDto(Discount discountEntity) {
    DiscountDto discountDto = new DiscountDto();
    discountDto.setId(discountEntity.getId());
    discountDto.setType(discountEntity.getType());
    discountDto.setNewPrice(discountEntity.getNewPrice());
    discountDto.setPercentage(discountEntity.getPercentage());
    discountDto.setStartDate(discountEntity.getStartDate());
    discountDto.setEndDate(discountEntity.getEndDate());
    discountDto.setStatus(discountEntity.getStatus());
    discountDto.setCode(discountEntity.getCode());
    discountDto.setProductDtoList(
        discountEntity.getProducts().stream().map(product -> productMapper.toDto(product))
            .toList());
    return discountDto;
  }
}
