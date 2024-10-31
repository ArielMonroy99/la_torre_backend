package com.torre.backend.db.seeds;

import com.torre.backend.authorization.dto.CreateUserDto;
import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;


public class V4__users extends BaseJavaMigration {

    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @Override
    public void migrate(Context context) {
        List<CreateUserDto> users = new ArrayList<>(Arrays.asList(
                new CreateUserDto("admin", "admin", 1233452,"admin@admin.com","admin","password",1L),
                new CreateUserDto("user", "user", 12312312,"user@user.com","user","password",2L)
        ));
        StringJoiner valuesJoiner = new StringJoiner(",", "INSERT INTO user_torre (name, lastname, cellphone, email, username, password, role_id, created_at, created_by, status) VALUES ", ";");
        for (CreateUserDto user : users) {
            valuesJoiner.add(String.format("('%s','%s','%s','%s','%s', '%s', '%s', 'now()', 'migration', '%s')",
                    user.getName(), user.getLastname(), user.getCellphone(), user.getEmail(), user.getUsername(),bCryptPasswordEncoder.encode(user.getPassword()),user.getRoleId(),user.getStatus()));
        }
        String sql = valuesJoiner.toString();

        new JdbcTemplate(new SingleConnectionDataSource(context.getConnection(), true))
                .execute(sql);

    }
}