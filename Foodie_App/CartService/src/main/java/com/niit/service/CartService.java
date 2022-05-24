package com.niit.service;

import com.niit.model.Cart;
import com.niit.model.Dish;
import com.niit.model.Restaurant;
import com.niit.model.User;

public interface CartService {
    Cart addDish(Dish dish, Restaurant restaurant,String email);
    Cart emptyCart(String emailId);
    Cart deleteDish(String dishId,String emailId);
    Cart createCartObj(String emailId, User user);
    Cart getCartDetails(String emailId);
    Object placeOrder(String emailId);
}
