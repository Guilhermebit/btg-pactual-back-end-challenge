package br.com.guibitencurt.btgpactual.ordermicroservice.controller.dto;

import org.springframework.data.domain.Page;

public record PaginationResponse(Integer page,
                                 Integer pageSize,
                                 Long totalElements,
                                 Integer totalPages) {

    public static PaginationResponse fromPage(Page<?> page) {
            if(page == null || page.isEmpty())
                return null;

            return new PaginationResponse(page.getNumber(),
                                          page.getSize(),
                                          page.getTotalElements(),
                                          page.getTotalPages());
    }
}
