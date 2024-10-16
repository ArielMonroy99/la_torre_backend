package com.torre.backend.authorization.controllers;

import com.torre.backend.authorization.annotations.CasbinFilter;
import com.torre.backend.authorization.entities.CasbinRule;
import com.torre.backend.authorization.services.PolicyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.tags.Param;

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

    @CasbinFilter
    @GetMapping()
    @Operation(description = "Endpoint para listar todas las politicas")
    public ResponseEntity<List<CasbinRule>> getAllPolicies (){
        return ResponseEntity.ok(policyService.getPolicies());
    }

    @CasbinFilter
    @PostMapping()
    @Operation(description = "Endpoint para guardar una nueva política",
               summary = "Guardar política")
    @SecurityRequirement(name = "bearerToken")
    public ResponseEntity<CasbinRule> addPolicy (@RequestBody CasbinRule casbinRule){
        policyService.addPolicy(casbinRule);
        return ResponseEntity.ok(casbinRule);
    }

    @CasbinFilter
    @PutMapping()
    @Operation(description = "Endpoint para actualizar una politica", summary = "Actualizar politica")
    @SecurityRequirement(name = "bearerToken")
    public ResponseEntity<CasbinRule> updatePolicy (@RequestBody CasbinRule casbinRule,
                                                    @RequestParam() String subject,
                                                    @RequestParam() String object,
                                                    @RequestParam() String action){
        CasbinRule oldRuleCasbin = new CasbinRule();
        oldRuleCasbin.setV0(subject);
        oldRuleCasbin.setV1(object);
        oldRuleCasbin.setV2(action);
        log.info("old rule {}", oldRuleCasbin);
        policyService.updatePolicy(casbinRule,oldRuleCasbin);
        return ResponseEntity.ok(casbinRule);
    }

    @CasbinFilter
    @DeleteMapping
    @Operation(description = "Endpoint para eliminar un política", summary = "Eliminar un política")
    @SecurityRequirement(name = "bearerToken")
    public ResponseEntity<CasbinRule> deletePolicy (@RequestBody CasbinRule casbinRule){
        policyService.deletePolicy(casbinRule);
        return ResponseEntity.ok(casbinRule);
    }
}
