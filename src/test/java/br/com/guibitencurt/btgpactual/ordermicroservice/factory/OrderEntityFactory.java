package br.com.guibitencurt.btgpactual.ordermicroservice.factory;

import br.com.guibitencurt.btgpactual.ordermicroservice.entity.OrderEntity;
import br.com.guibitencurt.btgpactual.ordermicroservice.entity.OrderItems;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import java.math.BigDecimal;
import java.util.List;

public class OrderEntityFactory {

    public static OrderEntity buildOrderEntity() {
        var items = new OrderItems("Mouse", 1, BigDecimal.valueOf(30.80));

        var entity = new OrderEntity();
        entity.setOrderId(1L);
        entity.setCustomerId(2L);
        entity.setTotalItems(BigDecimal.valueOf(30.80));
        entity.setListItems(List.of(items));

        return entity;

    }

    public static List<OrderEntity> buildInvalidOrderEntity() {

        OrderEntity order1 = new OrderEntity();
        order1.setOrderId(null);
        order1.setCustomerId(null);
        order1.setTotalItems(null);
        order1.setListItems(null);

        OrderEntity order2 = new OrderEntity();
        order2.setOrderId(0L);
        order2.setCustomerId(0L);
        order2.setTotalItems(null);
        order2.setListItems(null);

        OrderEntity order3 = new OrderEntity();
        order3.setOrderId(-1L);
        order3.setCustomerId(-1L);
        order3.setTotalItems(BigDecimal.valueOf(-1));
        order3.setListItems(null);

        return List.of(order1, order2, order3);

    }

    public static Page<OrderEntity> buildOrderEntityWithPage() {
        return new PageImpl<>(List.of(buildOrderEntity()));
    }

}