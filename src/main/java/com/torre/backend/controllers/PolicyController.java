package com.torre.backend.controllers;

import com.torre.backend.annotations.CasbinFilter;
import com.torre.backend.entities.CasbinRule;
import com.torre.backend.services.PolicyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/policy")
@Slf4j
public class PolicyController {
    private final PolicyService policyService;

    public PolicyController(PolicyService policyService) {
        this.policyService = policyService;
    }

    @CasbinFilter
    @GetMapping()
    public ResponseEntity<List<CasbinRule>> getAllPolicies (){
        return ResponseEntity.ok(policyService.getPolicies());
    }

    @PostMapping()
    public ResponseEntity<CasbinRule> addPolicy (@RequestBody CasbinRule casbinRule){
        policyService.addPolicy(casbinRule);
        return ResponseEntity.ok(casbinRule);
    }
}
