package com.torre.backend.authorization.mappers;

import com.torre.backend.authorization.dto.PolicyDto;
import com.torre.backend.authorization.entities.CasbinRule;

public class PolicyMapper {
    public static PolicyDto toDto(CasbinRule casbinRule) {
        PolicyDto policyDto = new PolicyDto();
        policyDto.setSubject(casbinRule.getV0());
        policyDto.setAction(casbinRule.getV2());
        policyDto.setObject(casbinRule.getV1());
        return policyDto;
    }

    public static CasbinRule toRule(PolicyDto policy) {
        CasbinRule casbinRule = new CasbinRule();
        casbinRule.setV0(policy.getSubject());
        casbinRule.setV2(policy.getAction());
        casbinRule.setV1(policy.getObject());
        casbinRule.setPtype("p");
        return casbinRule;
    }
}
