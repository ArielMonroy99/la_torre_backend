package com.torre.backend.authorization.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaginationDto<T> {
    private T content;
    private int page;
    private int size;
    private int totalElements;
}
