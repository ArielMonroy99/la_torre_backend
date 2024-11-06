package com.torre.backend.product.dto;

import com.torre.backend.common.dtos.QueryParamsDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductQueryParamsDto extends QueryParamsDto {
    private String productName = "";
    private String status = null;
}
