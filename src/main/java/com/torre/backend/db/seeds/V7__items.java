package com.torre.backend.db.seeds;

import com.torre.backend.inventory.dto.CategoryDto;
import com.torre.backend.inventory.dto.CreateItemDto;
import com.torre.backend.inventory.entities.Item;
import com.torre.backend.inventory.enums.ItemType;
import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringJoiner;


public class V7__items extends BaseJavaMigration {

    @Override
    public void migrate(Context context) {
        ArrayList<CreateItemDto> items = new ArrayList<>(Arrays.asList(
                new CreateItemDto("Queso",10,5,"kg",ItemType.KITCHEN,4L),
                new CreateItemDto("Coca-cola 500ml",10,5,"botella",ItemType.BEVERAGE,1L),
                new CreateItemDto("Limpia pisos",10,5,"botella",ItemType.CLEANING,3L)
        ));
        String sql = getString(items);
        new JdbcTemplate(new SingleConnectionDataSource(context.getConnection(), true))
                .execute(sql);

    }

    private static String getString(ArrayList<CreateItemDto> items) {
        StringJoiner valuesJoiner = new StringJoiner(",", "INSERT INTO item ( name, stock,minimum_stock, unit, type, category_id, created_at,created_by,status) VALUES ", ";");
        for (CreateItemDto createItemDto : items) {
            valuesJoiner.add(String.format("('%s','%s','%s', '%s','%s','%s',now(), 'migration', 'ACTIVE')", createItemDto.getName(),createItemDto.getStock(),createItemDto.getMinimumStock(),createItemDto.getUnit(),createItemDto.getType(),createItemDto.getCategoryId()));
        }
        String sql = valuesJoiner.toString();
        return sql;
    }
}