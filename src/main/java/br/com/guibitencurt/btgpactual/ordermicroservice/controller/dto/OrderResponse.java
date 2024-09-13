package br.com.guibitencurt.btgpactual.ordermicroservice.controller.dto;

import br.com.guibitencurt.btgpactual.ordermicroservice.entity.OrderEntity;
import java.math.BigDecimal;

public record OrderResponse(Long orderId,
                            Long customerId,
                            BigDecimal total) {

    public static OrderResponse fromEntity(OrderEntity entity) {
        if(entity == null)
            return null;

        return new OrderResponse(entity.getOrderId(), entity.getCustomerId(), entity.getTotalItems());
    }

}
