package com.torre.backend.inventory.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateInventoryLogDto {
    @NotNull
    @Min(2)
    @Max(1000)
    private String comment;
    @NotNull
    private Long ItemId;
    @NotNull
    private Long userId;
}
