package br.com.guibitencurt.btgpactual.ordermicroservice.factory;

import br.com.guibitencurt.btgpactual.ordermicroservice.controller.dto.OrderResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import java.math.BigDecimal;
import java.util.List;

public class OrderResponseFactory {

    public static Page<OrderResponse> buildOneItem() {
        var orderRsponse = new OrderResponse(1L, 2L, BigDecimal.valueOf(30.80));
        return new PageImpl<>(List.of(orderRsponse));
    }

}