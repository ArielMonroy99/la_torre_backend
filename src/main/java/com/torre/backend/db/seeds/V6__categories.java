package com.torre.backend.db.seeds;

import com.torre.backend.authorization.entities.Role;
import com.torre.backend.authorization.enums.RolEnum;
import com.torre.backend.inventory.dto.CategoryDto;
import com.torre.backend.inventory.entities.Category;
import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringJoiner;


public class V6__categories extends BaseJavaMigration {

    @Override
    public void migrate(Context context) {
        ArrayList<CategoryDto> categories = new ArrayList<>(Arrays.asList(
                new CategoryDto(1L, "Bebidas", "Bebidas"),
                new CategoryDto(2L, "Masa","Masa"),
                new CategoryDto(3L, "Limpieza", "Limpieza"),
                new CategoryDto(4L, "Cerveza", "Cerveza")
        ));

        StringJoiner valuesJoiner = new StringJoiner(",", "INSERT INTO category (id, name, description, created_at,created_by,status) VALUES ", ";");
        for (CategoryDto categoryDto : categories) {
            valuesJoiner.add(String.format("('%s',  '%s','%s', 'now()', 'migration', 'ACTIVE')", categoryDto.getId(),categoryDto.getName(),categoryDto.getDescription()));
        }
        String sql = valuesJoiner.toString();

        new JdbcTemplate(new SingleConnectionDataSource(context.getConnection(), true))
                .execute(sql);
    }
}