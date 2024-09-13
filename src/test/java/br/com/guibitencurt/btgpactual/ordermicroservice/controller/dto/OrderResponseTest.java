package br.com.guibitencurt.btgpactual.ordermicroservice.controller.dto;

import br.com.guibitencurt.btgpactual.ordermicroservice.entity.OrderEntity;
import br.com.guibitencurt.btgpactual.ordermicroservice.factory.OrderEntityFactory;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class OrderResponseTest {

    @Nested
    class FromEntity {

        @Test
        void shouldMapFromEntityCorrectly() {
            // ARRANGE
            var inputEntity = OrderEntityFactory.buildOrderEntity();

            // ACT
            var outputEntity = OrderResponse.fromEntity(inputEntity);

            // ASSERT
            assertEquals(inputEntity.getOrderId(), outputEntity.orderId());
            assertEquals(inputEntity.getCustomerId(), outputEntity.customerId());
            assertEquals(inputEntity.getTotalItems(), outputEntity.total());

        }

        @Test
        void shouldHandleInvalidData() {

            // ARRANGE
            List<OrderEntity> invalidInputEntity = OrderEntityFactory.buildInvalidOrderEntity();
            for(OrderEntity invalidList : invalidInputEntity) {

                // ACT
                var outputEntity = OrderResponse.fromEntity(invalidList);

                // ASSERT
                assertEquals(invalidList.getOrderId(), outputEntity.orderId());
                assertEquals(invalidList.getCustomerId(), outputEntity.customerId());
                assertEquals(invalidList.getTotalItems(), outputEntity.total());

            }
        }
    }
}