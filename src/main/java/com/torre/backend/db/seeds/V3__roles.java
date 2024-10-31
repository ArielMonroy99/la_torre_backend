package com.torre.backend.db.seeds;

import com.torre.backend.authorization.entities.Role;
import com.torre.backend.authorization.enums.RolEnum;
import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringJoiner;


public class V3__roles extends BaseJavaMigration {

    @Override
    public void migrate(Context context) {
        ArrayList<Role> roles = new ArrayList<>(Arrays.asList(
                new Role(1L, RolEnum.ADMINISTRATOR),
                new Role(2L, RolEnum.USER),
                new Role(3L, RolEnum.CASHIER),
                new Role(4L, RolEnum.KITCHEN)
        ));

        StringJoiner valuesJoiner = new StringJoiner(",", "INSERT INTO role (id, role, created_at,created_by,status) VALUES ", ";");
        for (Role role : roles) {
            valuesJoiner.add(String.format("('%s', '%s', 'now()', 'migration', '%s')", role.getId(),role.getRole(),role.getStatus()));
        }
        String sql = valuesJoiner.toString();

        new JdbcTemplate(new SingleConnectionDataSource(context.getConnection(), true))
                .execute(sql);
    }
}