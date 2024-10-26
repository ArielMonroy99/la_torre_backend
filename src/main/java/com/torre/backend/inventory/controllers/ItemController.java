package com.torre.backend.inventory.controllers;

import com.torre.backend.authorization.annotations.CasbinFilter;
import com.torre.backend.common.dtos.QueryParamsDto;
import com.torre.backend.common.dtos.ResponseDto;
import com.torre.backend.common.exceptions.BaseException;
import com.torre.backend.inventory.dto.CreateItemDto;
import com.torre.backend.inventory.dto.UpdateStockRequestDto;
import com.torre.backend.inventory.service.ItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    @Operation(summary = "List all items paginated ")
    @CasbinFilter()
    public ResponseEntity<?> getItems(@ModelAttribute QueryParamsDto queryParams) {
        return ResponseEntity.ok(new ResponseDto<>(true,"Operation successful",itemService.getItems(queryParams)));
    }

    @PostMapping()
    @Operation(summary = "Creates a new Item with data in request body")
    @CasbinFilter()
    public ResponseEntity<?> createItem(@RequestBody CreateItemDto createItemDto, HttpServletRequest request) {
        String username = (String) request.getAttribute("username");
        if (username == null) throw new BaseException(HttpStatus.UNAUTHORIZED,"Not logged in");
        itemService.createItem(createItemDto, username);
        return ResponseEntity.ok(new ResponseDto<>(true,"Operation successfull", null));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Updates an existing item with data in request body")
    @CasbinFilter()
    public  ResponseEntity<?> updateItem(@PathVariable("id") Long id, @RequestBody() CreateItemDto createItemDto, HttpServletRequest request) {
        String username = (String) request.getAttribute("username");
        if (username == null) throw new BaseException(HttpStatus.UNAUTHORIZED,"Not logged in");
        itemService.updateItem(id, createItemDto, username);
        return ResponseEntity.ok(new ResponseDto<>(true,"Operation successfull", null));
    }

    @PutMapping("/{id}/activate")
    @Operation(summary = "Activates a existing item")
    @CasbinFilter()
    public ResponseEntity<?> activateItem(@PathVariable("id") Long id, HttpServletRequest request) {
        String username = (String) request.getAttribute("username");
        if (username == null) throw new BaseException(HttpStatus.UNAUTHORIZED,"Not logged in");
        itemService.updateStatus(id, username, "ACTIVE");
        return ResponseEntity.ok(new ResponseDto<>(true,"Operation successful", null));
    }

    @PutMapping("/{id}/inactivate")
    @Operation(summary = "Inactivates a existing item")
    @CasbinFilter()
    public ResponseEntity<?> inactivateItem(@PathVariable("id") Long id, HttpServletRequest request) {
        String username = (String) request.getAttribute("username");
        if (username == null) throw new BaseException(HttpStatus.UNAUTHORIZED,"Not logged in");
        itemService.updateStatus(id, username, "INACTIVE");
        return ResponseEntity.ok(new ResponseDto<>(true,"Operation successful", null));
    }

    @PutMapping("/{id}/add-stock")
    @Operation(summary = "Adds stock to an existing Item")
    @CasbinFilter
    public  ResponseEntity<?> addStock(@PathVariable("id") Long id, @RequestBody UpdateStockRequestDto updateStock, HttpServletRequest request) {
        String username = (String) request.getAttribute("username");
        if (username == null) throw new BaseException(HttpStatus.UNAUTHORIZED,"Not logged in");
        itemService.addStock(id,updateStock,username);
        return ResponseEntity.ok(new ResponseDto<>(true,"Operation successful", null));
    }

    @PutMapping("/{id}/remove-stock")
    @Operation(summary = "Adds stock to an existing Item")
    @CasbinFilter
    public  ResponseEntity<?> removeStock(@PathVariable("id") Long id, @RequestBody UpdateStockRequestDto updateStock, HttpServletRequest request) {
        String username = (String) request.getAttribute("username");
        if (username == null) throw new BaseException(HttpStatus.UNAUTHORIZED,"Not logged in");
        itemService.removeStock(id,updateStock,username);
        return ResponseEntity.ok(new ResponseDto<>(true,"Operation successful", null));
    }
}
