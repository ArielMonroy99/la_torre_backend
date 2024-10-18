package com.torre.backend.common.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QueryParamsDto {
    private String filter;
    private String sort;
    private String order;
    private Integer page;
    private Integer limit;
}
