package br.com.guibitencurt.btgpactual.ordermicroservice.listener;

import br.com.guibitencurt.btgpactual.ordermicroservice.factory.OrderCreatedEventFactory;
import br.com.guibitencurt.btgpactual.ordermicroservice.service.OrderService;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.messaging.support.MessageBuilder;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RabbitMQConsumerTest {

    @Mock
    OrderService orderService;

    @InjectMocks
    RabbitMQConsumer rabbitMQConsumer;

    @Nested
    class ReceiveMessage {

        @Test
        void shouldPassCorrectParametersToService() {

            // ARRANGE
            var event = OrderCreatedEventFactory.buildEvent();
            var message = MessageBuilder.withPayload(event).build();

            // ACT
            rabbitMQConsumer.receiveMesssage(message);

            // ASSERT
            verify(orderService, times(1)).save(eq(message.getPayload()));

        }
    }
}