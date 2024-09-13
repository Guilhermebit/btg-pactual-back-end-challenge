package br.com.guibitencurt.btgpactual.ordermicroservice.controller.dto;

import jakarta.validation.constraints.NotNull;

public record OrderRequest(
        @NotNull(message = "Customer Id should not be null.") Long customerId,
        @NotNull(message = "Page should not be null.") Integer page,
        @NotNull(message = "PageSize should not be null.") Integer pageSize) {

    public OrderRequest {
        if(page == null)
            page = 0;
        if(pageSize == null)
            pageSize = 10;
    }
}
