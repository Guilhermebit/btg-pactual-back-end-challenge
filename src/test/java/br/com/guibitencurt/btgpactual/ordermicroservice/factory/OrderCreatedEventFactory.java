package br.com.guibitencurt.btgpactual.ordermicroservice.factory;

import br.com.guibitencurt.btgpactual.ordermicroservice.listener.dto.OrderCreatedEvent;
import br.com.guibitencurt.btgpactual.ordermicroservice.listener.dto.OrderItemEvent;
import java.math.BigDecimal;
import java.util.List;

public class OrderCreatedEventFactory {

    public static OrderCreatedEvent buildEvent() {
        var items = new OrderItemEvent("Mouse", 1, BigDecimal.valueOf(30.80));
        var event = new OrderCreatedEvent(1L, 2L, List.of(items));
        return event;
    }

}