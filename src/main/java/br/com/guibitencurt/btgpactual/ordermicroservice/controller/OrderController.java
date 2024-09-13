package br.com.guibitencurt.btgpactual.ordermicroservice.controller;

import br.com.guibitencurt.btgpactual.ordermicroservice.controller.dto.ApiResponse;
import br.com.guibitencurt.btgpactual.ordermicroservice.controller.dto.OrderRequest;
import br.com.guibitencurt.btgpactual.ordermicroservice.controller.dto.OrderResponse;
import br.com.guibitencurt.btgpactual.ordermicroservice.controller.dto.PaginationResponse;
import br.com.guibitencurt.btgpactual.ordermicroservice.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/customers/{customerId}/orders")
    public ResponseEntity<ApiResponse<OrderResponse>> listOrders(@PathVariable("customerId") Long customerId,
                                                                 @RequestParam(name = "page", defaultValue = "0") Integer page,
                                                                 @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {

        OrderRequest orderRequest = new OrderRequest(customerId, page, pageSize);

        var pageResponse = orderService.findAllByCustomerId(orderRequest);
        if(pageResponse == null || pageResponse.getContent().isEmpty())
            return ResponseEntity.noContent().build();


        return ResponseEntity.ok(new ApiResponse<>(
                pageResponse.getContent(),
                PaginationResponse.fromPage(pageResponse)));
    }

}
