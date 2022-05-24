package com.niit.service;

import com.niit.exception.DishAlreadyExistsException;
import com.niit.exception.DishNotFoundException;
import com.niit.exception.RestaurantNotFoundException;
import com.niit.exception.UserNotFoundException;
import com.niit.model.Dish;
import com.niit.model.Restaurant;
import com.niit.model.UserRequest;

import java.io.IOException;
import java.util.List;

public interface RestaurantService {
    Restaurant checkIfAuthenticated(UserRequest userRequest) throws RestaurantNotFoundException, UserNotFoundException;

    Dish addDishToMenu(Dish dish) throws DishAlreadyExistsException, IOException;
    Boolean deleteADishFromMenu(String dishId)throws DishNotFoundException;
    Dish updateDishInMenu(String dishId,Dish dish)throws DishNotFoundException;
    List<Dish> getAllDishes();
    Dish getADishOfParticularId(String dishId)throws DishNotFoundException;

    Object getAllOrdersOfRestaurant(String restaurantId);
    Object deleteOrder(String orderId);
    Object updateOrderStatus(String OrderId);
}
