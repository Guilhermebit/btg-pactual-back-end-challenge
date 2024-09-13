package br.com.guibitencurt.btgpactual.ordermicroservice.service;

import br.com.guibitencurt.btgpactual.ordermicroservice.controller.dto.OrderRequest;
import br.com.guibitencurt.btgpactual.ordermicroservice.controller.dto.OrderResponse;
import br.com.guibitencurt.btgpactual.ordermicroservice.entity.OrderEntity;
import br.com.guibitencurt.btgpactual.ordermicroservice.entity.OrderItems;
import br.com.guibitencurt.btgpactual.ordermicroservice.listener.dto.OrderCreatedEvent;
import br.com.guibitencurt.btgpactual.ordermicroservice.repository.OrderRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }


    public Page<OrderResponse> findAllByCustomerId(OrderRequest orderRequest) {
        var orders = orderRepository.findAllByCustomerId(orderRequest.customerId(), PageRequest.of(orderRequest.page(), orderRequest.pageSize()));
        return orders.map(OrderResponse::fromEntity);
    }

    public void save(OrderCreatedEvent event) {

        var entity = new OrderEntity();
        entity.setOrderId(event.codigoPedido());
        entity.setCustomerId((event.codigoCliente()));
        entity.setListItems(getOrderItems(event));
        entity.setTotalItems(getTotalItems(event));

        orderRepository.save(entity);

    }

    private BigDecimal getTotalItems(OrderCreatedEvent event) {

        return event.itens().stream()
                .map(i -> i.preco().multiply(BigDecimal.valueOf(i.quantidade())))
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);

    }

    private List<OrderItems> getOrderItems(OrderCreatedEvent event) {

        return event.itens().stream()
                .map(i -> new OrderItems(i.produto(), i.quantidade(), i.preco())).toList();
    }

}