package com.torre.backend.inventory.dto;

import com.torre.backend.authorization.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InventoryLogsDto {
    private Long id;
    private String comment;
    private LocalDateTime date;
    private UserDto user;
    private ItemDto item;
}
