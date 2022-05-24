package com.rabbitmq.domain;

import com.niit.model.Location;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class AddDishToCartDTO {
    private String dishId;
    private String dishType;
    private String dishName;
    private double dishPrice;
    private int dishQuantity;

    private String restaurantId;
    private String restaurantName;
    private String restaurantRatings;
    private String restaurantCuisineType;

    private String area;
    private String city;
    private String state;
    private String pincode;

    private String email;
}
