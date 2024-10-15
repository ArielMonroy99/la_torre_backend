package com.torre.backend.controllers;

import com.torre.backend.annotations.CasbinFilter;
import com.torre.backend.entities.CasbinRule;
import com.torre.backend.services.PolicyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/policy")
@Slf4j
@Tag(name = "Políticas")
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

   // @CasbinFilter
    @GetMapping()
    public ResponseEntity<List<CasbinRule>> getAllPolicies (){
        return ResponseEntity.ok(policyService.getPolicies());
    }

    @PostMapping()
    @Operation(description = "Endpoint para guardar una nueva política",
               summary = "Guardar política")
    @SecurityRequirement(name = "bearerToken")
    public ResponseEntity<CasbinRule> addPolicy (@RequestBody CasbinRule casbinRule){
        policyService.addPolicy(casbinRule);
        return ResponseEntity.ok(casbinRule);
    }
}
