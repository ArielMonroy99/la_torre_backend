package com.torre.backend.authorization.configurations;

import java.util.List;

public class Constants {
    public static final List<String> PROTECTED_ROUTES = List.of(
            "/api/v1/policy",
            "/api/v1/items" ,
            "/api/v1/items/**",
            "/api/v1/category",
            "/api/v1/category/**",
            "/api/v1/user/**",
            "/api/v1/products/**"
    );
}
