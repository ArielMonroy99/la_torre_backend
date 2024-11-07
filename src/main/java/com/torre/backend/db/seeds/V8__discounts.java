package com.torre.backend.db.seeds;

import com.torre.backend.product.dto.CreateDiscountDto;
import com.torre.backend.product.enums.DiscountTypeEnum;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;


public class V8__discounts extends BaseJavaMigration {

  @Override
  public void migrate(Context context) throws SQLException {
    LocalDateTime now = LocalDateTime.now();
    ArrayList<CreateDiscountDto> items = new ArrayList<>(Arrays.asList(
        new CreateDiscountDto(DiscountTypeEnum.NEW_PRICE, null, new BigDecimal("15.50"), now,
            now.plusDays(3), List.of(1L)),
        new CreateDiscountDto(DiscountTypeEnum.PERCENTAGE, 10, null, now,
            now.plusDays(3), List.of(1L))
    ));

    String sql =
        "INSERT INTO discount (type, percentage, new_price, start_date, end_date, created_at, created_by, status) "
            +
            "VALUES (?, ?, ?, ?, ?, now(), 'migration', 'ACTIVE')";
    Connection connection = context.getConnection();
    PreparedStatement statement = connection.prepareStatement(sql);
    for (CreateDiscountDto item : items) {
      statement.setString(1, item.getType().name());
      if (item.getPercentage() != null) {
        statement.setInt(2, item.getPercentage());
      } else {
        statement.setNull(2, java.sql.Types.DECIMAL);
      }
      if (item.getNewPrice() != null) {
        statement.setBigDecimal(3, item.getNewPrice());
      } else {
        statement.setNull(3, java.sql.Types.DECIMAL);
      }
      statement.setObject(4, item.getStartDate());
      statement.setObject(5, item.getEndDate());
      statement.execute();
    }
    statement.close();
    statement = connection.prepareStatement("Insert into product_discount values (? , ?)");
    int index = 1;
    for (CreateDiscountDto item : items) {
      for (Long productId : item.getProductIdList()) {
        statement.setInt(1, index);
        statement.setLong(2, productId);
      }
      statement.execute();
      index++;
    }
    statement.close();
  }
}
