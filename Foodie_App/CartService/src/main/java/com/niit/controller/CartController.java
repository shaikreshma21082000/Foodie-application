package com.niit.controller;


import com.niit.model.*;
import com.niit.service.CartService;
import com.rabbitmq.domain.AddDishToCartDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CartController {
    CartService cartService;
    ResponseEntity responseEntity;
    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/create-cart-obj/{emailId}")
    public ResponseEntity createObjectOfCart(@PathVariable String emailId,@RequestBody User user) {
        responseEntity = new ResponseEntity(cartService.createCartObj(emailId,user), HttpStatus.OK);
        return responseEntity;
    }

    @PostMapping("/add-dish")
    public ResponseEntity addDishToCart(@RequestBody AddDishToCartDTO addDishToCartDTO)
    {
        Dish dish=new Dish(addDishToCartDTO.getDishId(),addDishToCartDTO.getDishType(),addDishToCartDTO.getDishName(),addDishToCartDTO.getDishPrice(),addDishToCartDTO.getRestaurantName(),addDishToCartDTO.getDishQuantity());
        Location location=new Location(addDishToCartDTO.getArea(),addDishToCartDTO.getCity(),addDishToCartDTO.getState(),addDishToCartDTO.getPincode());
        Restaurant restaurant=new Restaurant(addDishToCartDTO.getRestaurantId(),addDishToCartDTO.getRestaurantName(),location,addDishToCartDTO.getRestaurantRatings(),addDishToCartDTO.getRestaurantCuisineType());
        responseEntity = new ResponseEntity(cartService.addDish(dish,restaurant,addDishToCartDTO.getEmail()), HttpStatus.OK);
        return responseEntity;
    }

    @DeleteMapping("/remove-dish/{dishId}/{emailId}")
    public ResponseEntity deleteDishFromCart(@PathVariable String dishId,@PathVariable String emailId)
    {
        responseEntity = new ResponseEntity(cartService.deleteDish(dishId,emailId), HttpStatus.OK);
        return responseEntity;
    }

    @GetMapping("/cart-details/{emailId}")
    public ResponseEntity getDetailsOfCartForAUser(@PathVariable String emailId)
    {
        responseEntity = new ResponseEntity(cartService.getCartDetails(emailId), HttpStatus.OK);
        return responseEntity;
    }

    @PutMapping("/empty-cart/{emailId}")
    public ResponseEntity emptyUserCart(@PathVariable String emailId)
    {
        responseEntity = new ResponseEntity(cartService.emptyCart(emailId), HttpStatus.OK);
        return responseEntity;
    }

    @PostMapping("/place-order-through-cart/{emailId}")
    public ResponseEntity placeAnOrder(@PathVariable String emailId)
    {
        responseEntity = new ResponseEntity(cartService.placeOrder(emailId), HttpStatus.OK);
        return responseEntity;
    }
}









