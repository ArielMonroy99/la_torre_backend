package com.torre.backend.db.seeds;

import com.torre.backend.authorization.entities.User;
import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;


public class V3__users extends BaseJavaMigration {

    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @Override
    public void migrate(Context context) {
        List<User> users = new ArrayList<>(Arrays.asList(
                new User("admin", "password", "ADMIN"),
                new User("user", "password", "USER")
        ));
        StringJoiner valuesJoiner = new StringJoiner(",", "INSERT INTO user_torre (username, password, name, created_at, created_by, status) VALUES ", ";");
        for (User user : users) {
            valuesJoiner.add(String.format("('%s', '%s', '%s', 'now()', 'migration', '%s')",
                    user.getUsername(),bCryptPasswordEncoder.encode(user.getPassword()),user.getName(),user.getStatus()));
        }
        String sql = valuesJoiner.toString();

        new JdbcTemplate(new SingleConnectionDataSource(context.getConnection(), true))
                .execute(sql);

    }
}