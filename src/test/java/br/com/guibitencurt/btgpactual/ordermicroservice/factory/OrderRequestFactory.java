package br.com.guibitencurt.btgpactual.ordermicroservice.factory;

import br.com.guibitencurt.btgpactual.ordermicroservice.controller.dto.OrderRequest;
import java.util.List;

public class OrderRequestFactory {

    public static OrderRequest buildRequest() {
        return new OrderRequest(1L, 0, 10);
    }

    public static List<OrderRequest> buildInvalidOrderRequests() {
        return List.of(new OrderRequest(null, null, null),
                       new OrderRequest(0L, null, null),
                       new OrderRequest(-1L, -1, -1));
    }

}