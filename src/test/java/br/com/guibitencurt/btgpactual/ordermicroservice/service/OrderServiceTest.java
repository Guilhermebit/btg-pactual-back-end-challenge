package br.com.guibitencurt.btgpactual.ordermicroservice.service;

import br.com.guibitencurt.btgpactual.ordermicroservice.controller.dto.OrderRequest;
import br.com.guibitencurt.btgpactual.ordermicroservice.controller.dto.OrderResponse;
import br.com.guibitencurt.btgpactual.ordermicroservice.entity.OrderEntity;
import br.com.guibitencurt.btgpactual.ordermicroservice.factory.OrderCreatedEventFactory;
import br.com.guibitencurt.btgpactual.ordermicroservice.factory.OrderEntityFactory;
import br.com.guibitencurt.btgpactual.ordermicroservice.factory.OrderRequestFactory;
import br.com.guibitencurt.btgpactual.ordermicroservice.repository.OrderRepository;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    OrderRepository orderRepository;

    @InjectMocks
    OrderService orderService;

    @Captor
    ArgumentCaptor<OrderEntity> orderEntityCaptor;

    @Nested
    class FindAllByCustomerId {

        @Test
        void shouldCallRepository() {
            // ARRANGE
            OrderRequest order = OrderRequestFactory.buildRequest();
            var pageRequest = PageRequest.of(order.page(), order.pageSize());

            doReturn(OrderEntityFactory.buildOrderEntityWithPage())
                    .when(orderRepository).findAllByCustomerId(eq(order.customerId()), eq(pageRequest));

            // ACT
            orderService.findAllByCustomerId(order);

            // ASSERT
            verify(orderRepository, times(1)).findAllByCustomerId(eq(order.customerId()), eq(pageRequest));

        }

        @Test
        void shouldMapResponse() {
            // ARRANGE
            OrderRequest order = OrderRequestFactory.buildRequest();
            var page = OrderEntityFactory.buildOrderEntityWithPage();

            doReturn(page).when(orderRepository).findAllByCustomerId(anyLong(), any());

            // ACT
            var response = orderService.findAllByCustomerId(order);

            // ASSERT

            OrderEntity fisrtPageOrder = page.getContent().get(0);
            OrderResponse firstResponseOrder = response.getContent().get(0);

            // order data
            assertEquals(fisrtPageOrder.getOrderId(), firstResponseOrder.orderId());
            assertEquals(fisrtPageOrder.getCustomerId(), firstResponseOrder.customerId());
            assertEquals(fisrtPageOrder.getTotalItems(), firstResponseOrder.total());

            // pages
            assertEquals(page.getNumber(), response.getNumber());
            assertEquals(page.getSize(), response.getSize());
            assertEquals(page.getTotalElements(), response.getTotalElements());
            assertEquals(page.getTotalPages(), response.getTotalPages());

        }
    }

    @Nested
    class Save {

        @Test
        void shouldCallRepositorySave() {
            // ARRANGE
            var orderCreated = OrderCreatedEventFactory.buildEvent();

            // ACT
            orderService.save(orderCreated);

            // ASSERT
            verify(orderRepository, times(1)).save(any());

        }

        @Test
        void shouldMapEventToEntityCorrectly() {
            // ARRANGE
            var orderCreated = OrderCreatedEventFactory.buildEvent();

            // ACT
            orderService.save(orderCreated);

            // ASSERT
            verify(orderRepository, times(1)).save(orderEntityCaptor.capture());

            var entity = orderEntityCaptor.getValue();

            assertEquals(orderCreated.codigoPedido(), entity.getOrderId());
            assertEquals(orderCreated.codigoCliente(), entity.getCustomerId());
            assertNotNull(entity.getTotalItems());
            assertEquals(orderCreated.itens().getFirst().produto(), entity.getListItems().getFirst().getProduct());
            assertEquals(orderCreated.itens().getFirst().quantidade(), entity.getListItems().getFirst().getQuantity());
            assertEquals(orderCreated.itens().getFirst().preco(), entity.getListItems().getFirst().getPrice());

        }
    }
}