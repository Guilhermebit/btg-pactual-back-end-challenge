package br.com.guibitencurt.btgpactual.ordermicroservice.listener;

import br.com.guibitencurt.btgpactual.ordermicroservice.config.RabbitMQConfig;
import br.com.guibitencurt.btgpactual.ordermicroservice.listener.dto.OrderCreatedEvent;
import br.com.guibitencurt.btgpactual.ordermicroservice.service.OrderService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class RabbitMQConsumer {

    private final Logger logger = LoggerFactory.getLogger(RabbitMQConsumer.class);

    private final OrderService orderService;

    public RabbitMQConsumer(OrderService orderService) {this.orderService = orderService;}

    @RabbitListener(queues = RabbitMQConfig.ORDER_QUEUE_NAME)
    public void receiveMesssage(Message<OrderCreatedEvent> message) {
        logger.info("Message consumed: {}" + message);
        orderService.save(message.getPayload());
    }
}