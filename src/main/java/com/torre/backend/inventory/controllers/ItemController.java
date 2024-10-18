package com.torre.backend.inventory.controllers;

import com.torre.backend.common.dtos.QueryParamsDto;
import com.torre.backend.inventory.service.ItemService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/items")
@Tag(name = "Items")
@Slf4j
public class ItemController {
    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping()
    public ResponseEntity<?> getItems(@ModelAttribute QueryParamsDto queryParams) {
        return ResponseEntity.ok(itemService.getItems(queryParams));
    }
}
