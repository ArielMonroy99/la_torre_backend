package com.torre.backend.authorization.services;

import com.torre.backend.authorization.entities.CasbinRule;
import com.torre.backend.common.exceptions.BaseException;
import lombok.extern.slf4j.Slf4j;
import org.casbin.jcasbin.main.Enforcer;
import org.springframework.http.HttpStatus;
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
        log.info("adding new casbinRule: {} ", casbinRule);
        casbinEnforcer.addPolicy(casbinRule.getV0(),casbinRule.getV1(),casbinRule.getV2());
    }
    public void deletePolicy(CasbinRule casbinRule) {
        log.info("deleting old casbinRule: {} ", casbinRule);
        casbinEnforcer.removePolicy(casbinRule.getV0(),casbinRule.getV1(),casbinRule.getV2());
    }
    public void updatePolicy(CasbinRule casbinRule, CasbinRule oldRule) {
        log.info("updating old casbinRule: {} ", casbinRule);
        if(casbinEnforcer.removePolicy(oldRule.getV0(),oldRule.getV1(),oldRule.getV2()))
            addPolicy(casbinRule);
        else
            throw new BaseException(HttpStatus.NOT_FOUND,"Policy not found");
    }
}
