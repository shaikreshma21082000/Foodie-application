package com.rabbitmq.domain;

import com.niit.model.Dish;
import com.niit.model.Restaurant;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.atomic.AtomicReference;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderDTO {
    private String orderId;
    private UserDTO user;
    private Restaurant restaurant;
    private List<Dish> dishList;
    private String orderDate;
    private double orderTotal;


}
