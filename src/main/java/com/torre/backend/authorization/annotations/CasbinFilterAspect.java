package com.torre.backend.authorization.annotations;

import com.torre.backend.authorization.entities.Role;
import com.torre.backend.common.exceptions.BaseException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.casbin.jcasbin.main.Enforcer;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.List;


@Slf4j
@Aspect
@Component
public class CasbinFilterAspect {

    private final HttpServletRequest request;
    private final Enforcer enforcer;

    public CasbinFilterAspect(HttpServletRequest request, Enforcer enforcer) {
        this.request = request;
        this.enforcer = enforcer;
    }

    @Before("@annotation(com.torre.backend.authorization.annotations.CasbinFilter)")
    public void logAfter(JoinPoint joinPoint) {
        String uri = request.getRequestURI();
        String method = request.getMethod();
        //TODO: create common messages class
        if (request.getAttribute("roles") == null) throw new BaseException(HttpStatus.UNAUTHORIZED, "Unauthorized");
        List<Role> roles = (List<Role>) request.getAttribute("roles");
        log.info("method: {}, uri: {}, username: {}", method, uri, roles);
        boolean hasPermission = roles.stream().anyMatch(role ->
                enforcer.enforce(role.getRole().toString(), uri, method)
        );
        if (!hasPermission) {
            throw new BaseException(HttpStatus.FORBIDDEN, "Forbidden");
        }
    }
}
