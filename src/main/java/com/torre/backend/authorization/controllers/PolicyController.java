package com.torre.backend.authorization.controllers;

import com.torre.backend.authorization.annotations.CasbinFilter;
import com.torre.backend.authorization.dto.PolicyDto;
import com.torre.backend.authorization.services.PolicyService;
import com.torre.backend.common.dtos.QueryParamsDto;
import com.torre.backend.common.dtos.ResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/policy")
@Tag(name = "Polícies")
@SecurityScheme(
    name = "bearerToken",
    type = SecuritySchemeType.HTTP,
    scheme = "bearer",
    bearerFormat = "JWT"
)
public class PolicyController {

  private final PolicyService policyService;

  public PolicyController(PolicyService policyService) {
    this.policyService = policyService;
  }

  @CasbinFilter
  @GetMapping()
  @Operation(description = "Endpoint for get paginated list of policies", summary = "List policies")
  @SecurityRequirement(name = "bearerToken")
  public ResponseEntity<?> getAllPolicies(@Valid @ModelAttribute QueryParamsDto queryParams) {
    return ResponseEntity.ok(new ResponseDto<>(true, "Operation finished successfully",
        this.policyService.getPolicies(queryParams)));
  }

  @CasbinFilter
  @PostMapping()
  @Operation(description = "Endpoint create a new policy",
      summary = "Save policy")
  @SecurityRequirement(name = "bearerToken")
  public ResponseEntity<ResponseDto<?>> addPolicy(@RequestBody PolicyDto policyDto) {
    policyService.addPolicy(policyDto);
    return ResponseEntity.ok(new ResponseDto<>(true, "Operation finished successfully", policyDto));
  }

  @CasbinFilter
  @PutMapping()
  @Operation(description = "Endpoint for update policy", summary = "Update policy")
  @SecurityRequirement(name = "bearerToken")
  public ResponseEntity<ResponseDto<?>> updatePolicy(@RequestBody PolicyDto policyDto,
      @ModelAttribute PolicyDto oldPolicyDto) {
    log.info("old rule {}", policyDto);
    policyService.updatePolicy(policyDto, oldPolicyDto);
    return ResponseEntity.ok(new ResponseDto<>(true, "Operation finished successfully", policyDto));
  }

  @CasbinFilter
  @DeleteMapping
  @Operation(description = "Endpoint for deleting policy", summary = "Delete policy")
  @SecurityRequirement(name = "bearerToken")
  public ResponseEntity<?> deletePolicy(@RequestBody PolicyDto policyDto) {
    policyService.deletePolicy(policyDto);
    return ResponseEntity.ok(new ResponseDto<>(true, "Operation finished successfully", policyDto));
  }
}
