package com.niit.config;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class NotificationProducer {
    private RabbitTemplate rabbitTemplate;
    private DirectExchange exchange;
    @Autowired
    public NotificationProducer(RabbitTemplate rabbitTemplate, @Qualifier("notificationDirectExchange") DirectExchange exchange) {
        super();
        this.rabbitTemplate = rabbitTemplate;
        this.exchange = exchange;
    }

    public Object sendNotificationToRabbitMq(Object o)
    {
        Object obj= rabbitTemplate.convertSendAndReceive(exchange.getName(),"notification_routing_key",o);
        System.out.println("Object=-----"+obj);
        return obj;
    }
}
