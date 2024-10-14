package com.torre.backend.services;

import com.torre.backend.entities.CasbinRule;
import lombok.extern.slf4j.Slf4j;
import org.casbin.jcasbin.main.Enforcer;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class PolicyService {
    private final Enforcer casbinEnforcer;
    public PolicyService(Enforcer casbinEnforcer){
        this.casbinEnforcer = casbinEnforcer;
    }

    public List<CasbinRule> getPolicies() {
        List<List<String>> rules = casbinEnforcer.getPolicy();
        log.info("rules {}: " ,rules);
        return casbinEnforcer.getPolicy().stream().map(m -> {
            CasbinRule casbinRule = new CasbinRule();
            casbinRule.setV0(m.getFirst());
            casbinRule.setV1(m.get(1));
            casbinRule.setV2(m.get(2));
            return casbinRule;
        }).toList();
    }
    public void addPolicy(CasbinRule casbinRule) {
        casbinEnforcer.addPolicy(casbinRule.getV0(),casbinRule.getV1(),casbinRule.getV2());
    }
}
