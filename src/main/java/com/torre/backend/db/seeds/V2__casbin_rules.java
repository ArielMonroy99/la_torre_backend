package com.torre.backend.db.seeds;

import com.torre.backend.authorization.entities.CasbinRule;
import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;

public class V2__casbin_rules extends BaseJavaMigration {

    public List<CasbinRule> casbinRules;
    @Override
    public void migrate(Context context) {
        casbinRules = new ArrayList<>();
        casbinRules.addAll(Arrays.asList(
                new CasbinRule("p", "ADMINISTRATOR", "/api/v1/policy", "GET|POST|PUT"),
                new CasbinRule("p", "ADMINISTRATOR", "/api/v1/user", "GET|POST|PUT"),
                new CasbinRule("p", "ADMINISTRATOR", "/api/v1/user/profile", "GET"),
                new CasbinRule("p", "ADMINISTRATOR", "/api/v1/user/:id", "PUT"),
                new CasbinRule("p", "ADMINISTRATOR", "/api/v1/user/:id/password", "PUT"),
                new CasbinRule("p", "ADMINISTRATOR", "/api/v1/user/:id/activate", "PUT"),
                new CasbinRule("p", "ADMINISTRATOR", "/api/v1/user/:id/inactivate", "PUT"),
                new CasbinRule("p", "ADMINISTRATOR", "/api/v1/items", "GET|POST"),
                new CasbinRule("p", "ADMINISTRATOR", "/api/v1/items/:id", "PUT"),
                new CasbinRule("p", "ADMINISTRATOR", "/api/v1/items/:id/activate", "PUT"),
                new CasbinRule("p", "ADMINISTRATOR", "/api/v1/items/:id/inactivate", "PUT"),
                new CasbinRule("p", "ADMINISTRATOR", "/api/v1/category", "GET|POST"),
                new CasbinRule("p", "ADMINISTRATOR", "/api/v1/category/:id", "PUT"),
                new CasbinRule("p", "ADMINISTRATOR", "/api/v1/category/:id/inactivate", "PUT"),
                new CasbinRule("p", "ADMINISTRATOR", "/api/v1/category/:id/inactivate", "PUT")

        ));
        StringJoiner valuesJoiner = new StringJoiner(",", "INSERT INTO casbin_rule (ptype, v0, v1, v2) VALUES ", ";");

        for (CasbinRule rule : casbinRules) {
            valuesJoiner.add(String.format("('%s', '%s', '%s', '%s')",
                    rule.getPtype(), rule.getV0(), rule.getV1(), rule.getV2()));
        }

        String sql = valuesJoiner.toString();
        System.out.println(sql);
        new JdbcTemplate(new SingleConnectionDataSource(context.getConnection(), true))
                .execute(sql);
    }
}