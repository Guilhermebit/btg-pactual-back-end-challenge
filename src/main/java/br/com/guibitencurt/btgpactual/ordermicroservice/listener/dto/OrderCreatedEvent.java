package br.com.guibitencurt.btgpactual.ordermicroservice.listener.dto;


import java.util.List;

public record OrderCreatedEvent(Long codigoPedido,
                                Long codigoCliente,
                                List<OrderItemEvent> itens) {
}
