package com.niit.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@ToString

public class Dish {
    private String dishId;
    private String dishType;
    private String dishName;
    private double dishPrice;
    private String restaurantName;
    private DishImage dishImage;
    private int totalDishQuantity;
    private int dishQuantity;

    public void setDishImage(DishImage dishImage) {
        this.dishImage = dishImage;
    }
}
