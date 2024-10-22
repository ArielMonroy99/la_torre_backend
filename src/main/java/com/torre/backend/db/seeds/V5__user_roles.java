package com.torre.backend.db.seeds;

import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;


public class V5__user_roles extends BaseJavaMigration {

    @Override
    public void migrate(Context context) {

        new JdbcTemplate(new SingleConnectionDataSource(context.getConnection(), true))
                .execute("insert into  user_roles (role_id, user_id) values (1,1),(2,1),(2,2);");

    }
}