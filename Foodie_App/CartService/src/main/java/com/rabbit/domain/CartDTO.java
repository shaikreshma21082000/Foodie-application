package com.rabbit.domain;

import com.niit.model.Dish;
import com.niit.model.Restaurant;
import com.niit.model.User;
import lombok.*;

import java.util.List;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class CartDTO {

    private String cartId;
    private String emailId;
    private List<Dish> dishList;
    private double total;
    private User user;
    private Restaurant restaurant;

}
