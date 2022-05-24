package com.niit.config;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class DeleteOrderProducer {
    private RabbitTemplate rabbitTemplate;
    private DirectExchange exchange;

    @Autowired
    public DeleteOrderProducer(RabbitTemplate rabbitTemplate,@Qualifier("deleteOrderDirectExchange") DirectExchange exchange) {
        super();
        this.rabbitTemplate = rabbitTemplate;
        this.exchange = exchange;
    }

    public Object sendMessageToOrderService(String orderId)
    {
        return  rabbitTemplate.convertSendAndReceive(exchange.getName(),"del_order_routing_key",orderId);
    }
}
