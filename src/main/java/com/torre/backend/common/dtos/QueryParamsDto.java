package com.torre.backend.common.dtos;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QueryParamsDto {

  private String filter = "";
  private String sort = "ASC";
  private String order = "id";
  @Min(value = 1, message = "Page should be greater than 0")
  private Integer page = 1;
  private Integer limit = 10;
}
