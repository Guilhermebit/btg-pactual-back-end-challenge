package br.com.guibitencurt.btgpactual.ordermicroservice.controller;

import br.com.guibitencurt.btgpactual.ordermicroservice.controller.dto.OrderRequest;
import br.com.guibitencurt.btgpactual.ordermicroservice.controller.dto.PaginationResponse;
import br.com.guibitencurt.btgpactual.ordermicroservice.factory.OrderRequestFactory;
import br.com.guibitencurt.btgpactual.ordermicroservice.factory.OrderResponseFactory;
import br.com.guibitencurt.btgpactual.ordermicroservice.service.OrderService;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import java.util.List;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class OrderControllerTest {

    @Mock
    OrderService orderService;

    @InjectMocks
    OrderController orderController;

    @Captor
    ArgumentCaptor<OrderRequest> orderRequestCaptor;

    @Nested
    class ListOrders {

        @Test
        void shouldReturnHttpOk() {

            OrderRequest order = OrderRequestFactory.buildRequest();

            // ARRANGE
            doReturn(OrderResponseFactory.buildOneItem()).when(orderService).findAllByCustomerId(order);

            // ACT
            var response = orderController.listOrders(order.customerId(), order.page(), order.pageSize());

            // ASSERT
            assertEquals(HttpStatus.OK, response.getStatusCode());

        }

        @Test
        void shouldReturnHttpNoContent() {
            // ARRANGE
            List<OrderRequest> invalidRequests = OrderRequestFactory.buildInvalidOrderRequests();
            for(OrderRequest invalidOrder : invalidRequests) {

                doReturn(null).when(orderService).findAllByCustomerId(invalidOrder);

                // ACT
                var response = orderController.listOrders(invalidOrder.customerId(), invalidOrder.page(), invalidOrder.pageSize());

                // ASSERT
                assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());

            }
        }


        @Test
        void shouldReturnNoContentForInvalidRequestData() {

            // ARRANGE
            List<OrderRequest> invalidRequests = OrderRequestFactory.buildInvalidOrderRequests();
            for(OrderRequest invalidOrder : invalidRequests) {

                // ACT
                var response = orderController.listOrders(invalidOrder.customerId(), invalidOrder.page(), invalidOrder.pageSize());

                // ASSERT
                assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());

            }

        }

        @Test
        void shouldApplyDefaultValuesForPageAndPageSize() {

            // ARRANGE
            var order = new OrderRequest(1L, null, null);
            doReturn(OrderResponseFactory.buildOneItem()).when(orderService).findAllByCustomerId(any());

            // ACT
            var response = orderController.listOrders(order.customerId(), order.page(), order.pageSize());

            // ASSERT
            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals(0, order.page());
            assertEquals(10, order.pageSize());

        }

        @Test
        void shouldPassCorrectParamsToService() {

            // ARRANGE
            OrderRequest order = OrderRequestFactory.buildRequest();
            doReturn(OrderResponseFactory.buildOneItem()).when(orderService).findAllByCustomerId(orderRequestCaptor.capture());

            // ACT
            orderController.listOrders(order.customerId(), order.page(), order.pageSize());

            // ASSERT
            ArgumentCaptor<OrderRequest> captor = ArgumentCaptor.forClass(OrderRequest.class);
            verify(orderService).findAllByCustomerId(captor.capture());

            assertEquals(1, orderRequestCaptor.getAllValues().size());

            OrderRequest captureOrderRequest = orderRequestCaptor.getValue();

            assertEquals(order.customerId(), captureOrderRequest.customerId());
            assertEquals(order.page(), captureOrderRequest.page());
            assertEquals(order.pageSize(), captureOrderRequest.pageSize());

        }

        @Test
        void shouldReturnResponseBodyCorrectly() {

            // ARRANGE
            OrderRequest order = OrderRequestFactory.buildRequest();
            var pagination = OrderResponseFactory.buildOneItem();

            doReturn(pagination).when(orderService).findAllByCustomerId(any());

            // ACT
            var response = orderController.listOrders(order.customerId(), order.page(), order.pageSize());

            // ASSERT
            assertNotNull(response);
            assertNotNull(response.getBody());
            assertNotNull(response.getBody().data());
            assertNotNull(response.getBody().pagination());

            // data
            assertEquals(pagination.getContent(), response.getBody().data());

            PaginationResponse paginationResponse = response.getBody().pagination();

            // page
            assertEquals(pagination.getNumber(), paginationResponse.page());
            assertEquals(pagination.getSize(), paginationResponse.pageSize());
            assertEquals(pagination.getTotalPages(), paginationResponse.totalPages());
            assertEquals(pagination.getTotalElements(), paginationResponse.totalElements());

        }
    }
}