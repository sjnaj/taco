package com.tacos.repository.jdbc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tacos.data.Order;
import com.tacos.data.Taco;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

public class JdbcOrderRepository implements OrderRepository {
  private SimpleJdbcInsert ordreInserter;
  private SimpleJdbcInsert orderTacoInserter;
  private ObjectMapper objectMapper;

  public JdbcOrderRepository(JdbcTemplate jdbc) {
    ordreInserter =
      new SimpleJdbcInsert(jdbc)
        .withTableName("Taco_Order")
        .usingGeneratedKeyColumns("id");
    orderTacoInserter =
      new SimpleJdbcInsert(jdbc).withTableName("Taco_Order_Tacos");
    objectMapper = new ObjectMapper();
  }

  @Override
  public Order save(Order order) {
    order.setPlaceAt(new Date());
    long orderId = saveOrderDetails(order);
    order.setId(orderId);
    List<Taco> tacos = order.getTacos();
    for (Taco taco : tacos) {
      saveTacoToOrder(taco, orderId);
    }

    return order;
  }

  private long saveOrderDetails(Order order) {
    @SuppressWarnings("unchecked")
    Map<String, Object> values = objectMapper.convertValue(order, Map.class);//mapping order to pairs(valuename,value),can use other techniques
    values.put("placeAt", order.getPlaceAt());//should be replaced since above operation would convert date to long
    long orderId = ordreInserter.executeAndReturnKey(values).longValue();
    return orderId;
  }

  private void saveTacoToOrder(Taco taco, long orderId) {
    Map<String, Object> values = new HashMap<>();
    values.put("tacoOrder", orderId);
    values.put("taco", taco.getId());
    orderTacoInserter.execute(values);
  }
}
