package com.niit.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MessageConfiguration {
    //private Jackson2JsonMessageConverter producerJackson2MessageConverter = new Jackson2JsonMessageConverter();
    private String exchangeName = "get-orders-exchange";
    private String registerQueue = "get-orders-queue";

    @Bean
    public DirectExchange getOrdersDirectExchange() {
        return new DirectExchange(exchangeName);
    }

    @Bean
    public Queue getOrdersRegisterQueue() {
        return new Queue(registerQueue, true);
    }

    //For Conversion for message
    @Bean
    public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    //it is responsible for establishing communication and pass the data
    @Bean
    public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemp = new RabbitTemplate(connectionFactory);
        rabbitTemp.setMessageConverter(producerJackson2MessageConverter());
        return rabbitTemp;
    }

    @Bean
    Binding binding(@Qualifier("getOrdersRegisterQueue") Queue registerQueue, @Qualifier("getOrdersDirectExchange") DirectExchange exchange) {
        return BindingBuilder.bind(getOrdersRegisterQueue()).to(exchange).with("get-orders-key");
    }

    //delete order
    private String deleteOrderExchangeName ="del_order_exchange";
    private String deleteOrderRegisterQueue ="del_order_queue";

    @Bean
    public DirectExchange deleteOrderDirectExchange()
    {
        return new DirectExchange(deleteOrderExchangeName);
    }

    @Bean
    public Queue deleteOrderRegisterQueue()
    {
        return new Queue(deleteOrderRegisterQueue,false);
    }

    @Bean
    Binding deleteOrderBinding(@Qualifier("deleteOrderRegisterQueue")Queue registerQueue,@Qualifier("deleteOrderDirectExchange") DirectExchange exchange)
    {
        return BindingBuilder.bind(deleteOrderRegisterQueue()).to(exchange).with("del_order_routing_key");
    }

    //update order status
    private String updateOrderStatusExchangeName ="update_order_status__exchange";
    private String updateOrderStatusRegisterQueue ="update_order_status_queue";

    @Bean
    public DirectExchange updateOrderStatusDirectExchange()
    {
        return new DirectExchange(updateOrderStatusExchangeName);
    }

    @Bean
    public Queue updateOrderStatusRegisterQueue()
    {
        return new Queue(updateOrderStatusRegisterQueue,false);
    }

    @Bean
    Binding OrderStatusUpdateBinding(@Qualifier("updateOrderStatusRegisterQueue")Queue registerQueue,@Qualifier("updateOrderStatusDirectExchange") DirectExchange exchange)
    {
        return BindingBuilder.bind(updateOrderStatusRegisterQueue()).to(exchange).with("update_order_status_routing_key");
    }

    //notify user configuration
    private String notificationExchangeName ="notification_exchange";
    private String notificationRegisterQueue ="notification_queue";

    @Bean
    public DirectExchange notificationDirectExchange()
    {
        return new DirectExchange(notificationExchangeName);
    }

    @Bean
    public Queue notificationRegisterQueue()
    {
        return new Queue(notificationRegisterQueue,false);
    }

    @Bean
    Binding notificationBinding(@Qualifier("notificationRegisterQueue")Queue registerQueue,@Qualifier("notificationDirectExchange") DirectExchange exchange)
    {
        return BindingBuilder.bind(notificationRegisterQueue()).to(exchange).with("notification_routing_key");
    }


}