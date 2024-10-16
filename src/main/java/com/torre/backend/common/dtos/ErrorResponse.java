package com.torre.backend.common.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {
    private Timestamp timestamp;
    private String message;
    private Integer status;
}
