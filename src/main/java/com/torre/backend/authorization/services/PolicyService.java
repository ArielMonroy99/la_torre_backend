package com.torre.backend.authorization.services;

import com.torre.backend.authorization.dto.PaginationDto;
import com.torre.backend.authorization.dto.PolicyDto;
import com.torre.backend.authorization.entities.CasbinRule;
import com.torre.backend.authorization.mappers.PolicyMapper;
import com.torre.backend.common.dtos.QueryParamsDto;
import com.torre.backend.common.exceptions.BaseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.casbin.jcasbin.main.Enforcer;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PolicyService {

  private final Enforcer casbinEnforcer;

  public PolicyService(Enforcer casbinEnforcer) {
    this.casbinEnforcer = casbinEnforcer;
  }

  public PaginationDto<?> getPolicies(QueryParamsDto queryParamsDto) {
    List<CasbinRule> casbinRules = casbinEnforcer.getPolicy().stream().map(m -> {
      CasbinRule casbinRule = new CasbinRule();
      casbinRule.setV0(m.get(0));
      casbinRule.setV1(m.get(1));
      casbinRule.setV2(m.get(2));
      return casbinRule;
    }).toList();
    List<PolicyDto> policies = casbinRules.stream().map(PolicyMapper::toDto).toList();

    policies = new ArrayList<>(policies.stream().filter(po -> {
      String filter = queryParamsDto.getFilter();
      return po.getAction().toLowerCase().contains(filter.toLowerCase()) || po.getSubject()
          .toLowerCase()
          .contains(filter.toLowerCase()) || po.getObject().toLowerCase()
          .contains(filter.toLowerCase());
    }).toList());

    if ("object".equals(queryParamsDto.getOrder())) {
      policies.sort(
          queryParamsDto.getSort().equals("ASC") ? Comparator.comparing(PolicyDto::getObject)
              : Comparator.comparing(PolicyDto::getObject).reversed());
    } else if ("subject".equals(queryParamsDto.getOrder())) {
      policies.sort(
          queryParamsDto.getSort().equals("ASC") ? Comparator.comparing(PolicyDto::getSubject)
              : Comparator.comparing(PolicyDto::getSubject).reversed());
    } else if ("action".equals(queryParamsDto.getOrder())) {
      policies.sort(
          queryParamsDto.getSort().equals("ASC") ? Comparator.comparing(PolicyDto::getAction)
              : Comparator.comparing(PolicyDto::getAction).reversed());
    }

    int start = (queryParamsDto.getPage() - 1) * queryParamsDto.getLimit();
    int end = Math.min(start + queryParamsDto.getLimit(), policies.size());
    log.info("Inicio {}, final {}", start, end);
    if (start > policies.size()) {
      return new PaginationDto<>(List.of(), 0, 0, 0);
    }
    return new PaginationDto<>(policies.subList(start, end), queryParamsDto.getPage(),
        queryParamsDto.getLimit(), policies.size());
  }

  public void addPolicy(PolicyDto policyDto) {
    log.info("adding new casbinRule: {} ", policyDto);

    casbinEnforcer.addPolicy(policyDto.getSubject(), policyDto.getObject(), policyDto.getAction());
  }

  public void deletePolicy(PolicyDto policyDto) {
    log.info("deleting old casbinRule: {} ", policyDto);
    casbinEnforcer.removePolicy(policyDto.getSubject(), policyDto.getObject(),
        policyDto.getAction());

  }

  public void updatePolicy(PolicyDto policyDto, PolicyDto oldPolicyDto) {
    log.info("updating old casbinRule: {} ", policyDto);
    if (casbinEnforcer.removePolicy(oldPolicyDto.getSubject(), oldPolicyDto.getObject(),
        oldPolicyDto.getAction())) {
      addPolicy(policyDto);
    } else {
      throw new BaseException(HttpStatus.NOT_FOUND, "Policy not found");
    }
  }
}
