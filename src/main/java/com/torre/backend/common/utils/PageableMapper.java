package com.torre.backend.common.utils;

import com.torre.backend.common.dtos.QueryParamsDto;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class PageableMapper {

  public static Pageable buildPage(QueryParamsDto queryParamsDto) {
    Integer page = queryParamsDto.getPage();
    Integer limit = queryParamsDto.getLimit();
    String order = queryParamsDto.getOrder();
    String sort = queryParamsDto.getSort();
    return PageRequest.of(page - 1, limit, Sort.Direction.valueOf(sort), order);
  }
}
