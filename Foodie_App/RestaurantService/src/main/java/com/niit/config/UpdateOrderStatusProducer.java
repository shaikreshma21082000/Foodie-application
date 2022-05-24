package com.niit.config;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class UpdateOrderStatusProducer {
    private RabbitTemplate rabbitTemplate;
    private DirectExchange exchange;

    @Autowired
    public UpdateOrderStatusProducer(RabbitTemplate rabbitTemplate,@Qualifier("updateOrderStatusDirectExchange") DirectExchange exchange) {
        super();
        this.rabbitTemplate = rabbitTemplate;
        this.exchange = exchange;
    }

//    Queue registerQueue, @Qualifier("directExchange") DirectExchange exchange @Qualifier("registerQueue")

    public Object sendMessageToOrderThroughRabbitMq(String orderId)
    {
        return  rabbitTemplate.convertSendAndReceive(exchange.getName(),"update_order_status_routing_key",orderId);
    }

}
