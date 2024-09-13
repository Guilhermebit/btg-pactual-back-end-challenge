package br.com.guibitencurt.btgpactual.ordermicroservice.controller.dto;

import java.util.List;

public record ApiResponse<T>(List<T> data, PaginationResponse pagination) {
}
