package com.torre.backend.product.controllers;

import com.torre.backend.authorization.annotations.CasbinFilter;
import com.torre.backend.common.constants.Messages;
import com.torre.backend.common.dtos.QueryParamsDto;
import com.torre.backend.common.dtos.ResponseDto;
import com.torre.backend.product.dto.CreateDiscountDto;
import com.torre.backend.product.service.DiscountService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("discounts")
public class DiscountController {

  private final DiscountService discountService;

  public DiscountController(DiscountService discountService) {
    this.discountService = discountService;
  }

  @Operation(summary = "List all discounts paginated")
  @CasbinFilter
  @GetMapping()
  public ResponseEntity<?> getDiscounts(@ModelAttribute QueryParamsDto queryParamsDto) {
    return ResponseEntity.ok(
        new ResponseDto<>(true, Messages.SUCCESS, discountService.filterDiscounts(queryParamsDto)));
  }

  @Operation(summary = "Create discount")
  @CasbinFilter
  @PostMapping
  public ResponseEntity<?> createDiscount(@RequestBody CreateDiscountDto createDiscountDto,
      HttpServletRequest request) {
    String username = request.getAttribute("username").toString();
    discountService.createDiscount(createDiscountDto, username);
    return ResponseEntity.ok(new ResponseDto<>(true, Messages.SUCCESS, null));
  }

  @Operation(summary = "Updates a discount")
  @CasbinFilter
  @PutMapping("/{id}")
  public ResponseEntity<?> updateDiscount(@RequestBody CreateDiscountDto createDiscountDto,
      @PathVariable Long id, HttpServletRequest request) {
    String username = request.getAttribute("username").toString();
    discountService.updateDiscount(id, createDiscountDto, username);
    return ResponseEntity.ok(new ResponseDto<>(true, Messages.SUCCESS, null));
  }

  @Operation(summary = "Activas a discount")
  @CasbinFilter
  @PutMapping("/{id}/activate")
  public ResponseEntity<?> activateDiscount(@PathVariable Long id, HttpServletRequest request) {
    String username = request.getAttribute("username").toString();
    discountService.activateDiscount(id, username);
    return ResponseEntity.ok(new ResponseDto<>(true, Messages.SUCCESS, null));
  }

  @Operation(summary = "Activas a discount")
  @CasbinFilter
  @PutMapping("/{id}/inactivate")
  public ResponseEntity<?> inactivateDiscount(@PathVariable Long id, HttpServletRequest request) {
    String username = request.getAttribute("username").toString();
    discountService.inactivateDiscount(id, username);
    return ResponseEntity.ok(new ResponseDto<>(true, Messages.SUCCESS, null));
  }

  @Operation(summary = "Creates a new discount code for a discount")
  @CasbinFilter
  @PostMapping("/{id}/code")
  public ResponseEntity<?> cretePromotionCode(@PathVariable Long id, HttpServletRequest request) {
    String username = request.getAttribute("username").toString();
    return ResponseEntity.ok(
        new ResponseDto<>(true, Messages.SUCCESS, discountService.createCode(id, username)));
  }

  @Operation(summary = "Find a discount by its code")
  @CasbinFilter
  @GetMapping("/{code}")
  public ResponseEntity<?> getDiscountByCode(@PathVariable String code) {
    return ResponseEntity.ok(
        new ResponseDto<>(true, Messages.SUCCESS, discountService.findDiscountByCode(code)));
  }
}
