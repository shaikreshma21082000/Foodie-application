package com.niit.config;

import com.niit.model.*;
import com.niit.service.CartServiceImpl;
import com.rabbit.domain.CartDTO;
import com.rabbit.domain.UserRequestDTO;
import com.rabbitmq.domain.AddDishToCartDTO;
import com.rabbitmq.domain.DelDishDTO;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Locale;

@Component
public class Consumer {
    @Autowired
    CartServiceImpl cartService;

    @RabbitListener(queues="create_cart_queue")
    public Object receiveMessage(UserRequestDTO user)
    {
        System.out.println("consumed request"+user);
        Address a=new Address(user.getHouseNo(),user.getStreet(),user.getCity(),user.getState(),user.getPincode());
        User u=new User(user.getEmail(), user.getFirstName(), user.getLastName(), user.getPassword(), user.getDateOfBirth(),user.getGender(),a);
        System.out.println("user details to create c aca rt object is "+u);
        System.out.println("email id: "+user.getEmail());
        return cartService.createCartObj(user.getEmail(),u);
    }

    @RabbitListener(queues="addDishToCartQueue")
    public Object receiveDishToAddToCart(AddDishToCartDTO addDishToCartDTO)
    {
        System.out.println("consumed cart object**********"+addDishToCartDTO);
        Dish dish=new Dish(addDishToCartDTO.getDishId(),addDishToCartDTO.getDishType(),addDishToCartDTO.getDishName(),addDishToCartDTO.getDishPrice(),addDishToCartDTO.getRestaurantName(),addDishToCartDTO.getDishQuantity());
        System.out.println("dish to be added to cart :"+dish);
        Location location=new Location(addDishToCartDTO.getArea(),addDishToCartDTO.getCity(),addDishToCartDTO.getState(),addDishToCartDTO.getPincode());
        Restaurant restaurant=new Restaurant(addDishToCartDTO.getRestaurantId(),addDishToCartDTO.getRestaurantName(),location,addDishToCartDTO.getRestaurantRatings(),addDishToCartDTO.getRestaurantCuisineType());
        System.out.println("restaurant object in add dish to cart method"+restaurant);
        System.out.println("email of user ----"+addDishToCartDTO.getEmail());
        return cartService.addDish(dish,restaurant,addDishToCartDTO.getEmail());
    }

    @RabbitListener(queues="deleteDishToCartQueue")
    public Object receiveDishToDeleteFromCart(DelDishDTO delDishDTO)
    {
        System.out.println("consumed cart object"+delDishDTO);
        return cartService.deleteDish(delDishDTO.getDishId(),delDishDTO.getEmail());
    }

    @RabbitListener(queues="getDishToCartQueue")
    public Object giveCartDetails(String emailId)
    {
        System.out.println("consumed email id"+emailId);
        return cartService.getCartDetails(emailId);
    }

    @RabbitListener(queues="emptyToCartQueue")
    public Object emptyCartDetails(String emailId)
    {
        System.out.println("consumed email id"+emailId);
        return cartService.emptyCart(emailId);
    }

    @RabbitListener(queues="placeOrderQueue")
    public Object placeItemsInCart(String emailId)
    {
        System.out.println("consumed order from search--------"+emailId);
        return cartService.placeOrder(emailId);
    }

}
