package com.niit.model;
import lombok.*;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Dish {
    private String dishId;
    private String dishType;
    private String dishName;
    private double dishPrice;
    private String restaurantName;
    //this qty is user's dish
    private int dishQuantity;
}