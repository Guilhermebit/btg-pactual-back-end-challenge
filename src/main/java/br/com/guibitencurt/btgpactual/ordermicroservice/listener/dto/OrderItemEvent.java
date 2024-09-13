package br.com.guibitencurt.btgpactual.ordermicroservice.listener.dto;

import java.math.BigDecimal;

public record OrderItemEvent(String produto,
                             Integer quantidade,
                             BigDecimal preco) {
}
