package com.niit.config;

import com.niit.model.Cart;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class Producer {
    private RabbitTemplate rabbitTemplate;
    private DirectExchange exchange;

    @Autowired
    public Producer(RabbitTemplate rabbitTemplate, @Qualifier("directExchange") DirectExchange exchange) {
        super();
        this.rabbitTemplate = rabbitTemplate;
        this.exchange = exchange;
    }
    public Object sendMessageToRabbitMq(Cart cart)
    {
        System.out.println("cart producer------"+cart);
        return rabbitTemplate.convertSendAndReceive(exchange.getName(),"order",cart);
    }
}
