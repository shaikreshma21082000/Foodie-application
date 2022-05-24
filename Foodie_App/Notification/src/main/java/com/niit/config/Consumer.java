package com.niit.config;


import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class Consumer {
    @RabbitListener(queues="notification_queue")
    public Object getNotificationFromFromRabbitMq(Object o)
    {
        System.out.println("data-->"+o);
        return o;
    }
}
