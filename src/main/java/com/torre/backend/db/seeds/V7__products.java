package com.torre.backend.db.seeds;

import com.torre.backend.product.dto.CreateProductDto;
import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringJoiner;

public class V7__products extends BaseJavaMigration {
    @Override
    public void migrate(Context context) {
        ArrayList<CreateProductDto> products = new ArrayList<>(Arrays.asList(
                new CreateProductDto("Pizza Gorlami",new BigDecimal("50.0"),1L),
                new CreateProductDto("pizza queso",new BigDecimal("50.0"),2L),
                new CreateProductDto("Pizza Napolitana",new BigDecimal("50.0"),3L)
        ));

        String sql = buildInsertQuery(products);
        new JdbcTemplate(new SingleConnectionDataSource(context.getConnection(), true))
                .execute(sql);
    }

    private static String buildInsertQuery(ArrayList<CreateProductDto> products) {
        StringJoiner valuesJoiner = new StringJoiner(",", "INSERT INTO product (name, price, category_id, created_at, created_by, status) VALUES ", ";");

        for (CreateProductDto product : products) {
            valuesJoiner.add(String.format("('%s', %s, %d, now(), 'migration', 'ACTIVE')",
                    product.getName(),
                    product.getPrice().toPlainString(),
                    product.getCategoryId()
            ));
        }

        return valuesJoiner.toString();
    }

}