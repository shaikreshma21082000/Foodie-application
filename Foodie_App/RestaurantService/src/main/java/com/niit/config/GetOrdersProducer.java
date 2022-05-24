package com.niit.config;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class GetOrdersProducer {
    private RabbitTemplate rabbitTemplate;
    private DirectExchange exchange;
    @Autowired
    public GetOrdersProducer(RabbitTemplate rabbitTemplate, @Qualifier("getOrdersDirectExchange") DirectExchange exchange) {
        super();
        this.rabbitTemplate = rabbitTemplate;
        this.exchange = exchange;
    }

    public Object sendMessageToRabbitMq(String restaurantId)
    {
        Object obj= rabbitTemplate.convertSendAndReceive(exchange.getName(),"get-orders-key",restaurantId);
        System.out.println("Object=-----"+obj);
        return obj;
    }
}