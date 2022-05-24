package com.niit.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.niit.exception.DishAlreadyExistsException;
import com.niit.exception.DishNotFoundException;
import com.niit.exception.RestaurantNotFoundException;
import com.niit.exception.UserNotFoundException;
import com.niit.model.Dish;
import com.niit.model.DishImage;
import com.niit.model.Restaurant;
import com.niit.model.UserRequest;
import com.niit.service.DishImageService;
import com.niit.service.RestaurantService;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/v")
@CrossOrigin(origins = "*")
public class RestaurantController {

//    TokenGeneration tokenGeneration;
    RestaurantService restaurantService;
    DishImageService dishImageService;
    private ResponseEntity responseEntity;

    @Autowired
    public RestaurantController(RestaurantService restaurantService,DishImageService dishImageService)
    {
        this.restaurantService=restaurantService;
//        this.tokenGeneration=tokenGeneration;
        this.dishImageService=dishImageService;
    }

    //    @HystrixCommand(fallbackMethod = "fallbackLogin")
//    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000")
    @PostMapping("/login")
    public ResponseEntity verifyRestaurant(@RequestBody UserRequest userRequest) throws RestaurantNotFoundException, UserNotFoundException {
        return new ResponseEntity<>(restaurantService.checkIfAuthenticated(userRequest), HttpStatus.OK);
    }

    @PostMapping("/apis/new-dish")
    @HystrixCommand(fallbackMethod = "fallbackAddDish")
    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1000")
    public ResponseEntity addNewDish(@RequestBody Dish dish) throws DishAlreadyExistsException {
        try {

            responseEntity=new ResponseEntity(restaurantService.addDishToMenu(dish),HttpStatus.OK);
        }
        catch (DishAlreadyExistsException e)
        {
            throw new DishAlreadyExistsException();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return responseEntity;
    }

    @HystrixCommand(fallbackMethod = "fallbackDeleteDish")
    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1000")
    @DeleteMapping("/apis/remove-dish/{dishId}")
    public ResponseEntity removeDish(@PathVariable String dishId) throws DishNotFoundException {
        try {
            responseEntity=new ResponseEntity(restaurantService.deleteADishFromMenu(dishId),HttpStatus.OK);
        }
        catch (DishNotFoundException e)
        {
            throw new DishNotFoundException();
        }
        return responseEntity;
    }

    @HystrixCommand(fallbackMethod = "fallbackUpdateDish")
    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1000")
    @PutMapping("/apis/dish/{dishId}")
    public ResponseEntity updateDish(@PathVariable String dishId,@RequestBody Dish dish) throws DishNotFoundException {
        try {
            System.out.println("entered into controller");
            responseEntity=new ResponseEntity(restaurantService.updateDishInMenu(dishId,dish),HttpStatus.OK);
        }
        catch (DishNotFoundException e)
        {
            throw new DishNotFoundException();
        }
        return responseEntity;
    }

    @HystrixCommand(fallbackMethod = "fallbackRetrieveAllDishes")
    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1000")
    @GetMapping("/dishes")
    public ResponseEntity retrieveAllDishes() throws Exception {
        try {
            responseEntity=new ResponseEntity(restaurantService.getAllDishes(),HttpStatus.OK);
        }
        catch (Exception e)
        {
            throw new Exception();
        }
        return responseEntity;
    }

    @HystrixCommand(fallbackMethod = "fallbackGetDishOfParticularDishId")
    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1000")
    @GetMapping("/apis/dish/{dishId}")
    public ResponseEntity getDishOfParticularDishId(@PathVariable String dishId) throws DishNotFoundException {
        try {
            responseEntity=new ResponseEntity(restaurantService.getADishOfParticularId(dishId),HttpStatus.OK);
        }
        catch (DishNotFoundException e)
        {
            throw new DishNotFoundException();
        }
        return responseEntity;
    }

    //================================================
    //images method
    @HystrixCommand(fallbackMethod = "fallbackAddImage")
    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1000")
    @PostMapping("/add-image")
    public ResponseEntity addDishImage(@RequestPart("image") MultipartFile image) throws IOException {

        System.out.println("content type"+   image.getContentType());
        DishImage dishImage=new DishImage(image.getOriginalFilename(), image.getContentType(),new Binary(image.getBytes()));
        responseEntity=new ResponseEntity(dishImageService.addImageOfDish(dishImage),HttpStatus.OK);
        return responseEntity;
    }

    @HystrixCommand(fallbackMethod = "fallbackGetImage")
    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1000")
    @GetMapping("/get-image/{imageId}")
    public ResponseEntity getImageOfDish(@PathVariable String imageId)
    {
        responseEntity=new ResponseEntity(dishImageService.getImageOfDish(imageId),HttpStatus.OK);
        return responseEntity;
    }

    @HystrixCommand(fallbackMethod = "fallbackUpdateImage")
    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1000")
    @PutMapping("/new-image/{imageId}")
    public ResponseEntity updateImage(@PathVariable String imageId,@RequestParam("image") MultipartFile image) throws IOException {
        responseEntity=new ResponseEntity(dishImageService.updateImageOfDish(imageId,image),HttpStatus.OK);
        return responseEntity;
    }

    @GetMapping("/orders/{restaurantId}")
    public ResponseEntity getOrdersOfParticularRestaurant(@PathVariable String restaurantId)
    {
        Object obj=restaurantService.getAllOrdersOfRestaurant(restaurantId);
        responseEntity=new ResponseEntity(obj,HttpStatus.OK);
        System.out.println(obj.toString());
        return responseEntity;
    }


    @DeleteMapping("/remove-order/{orderId}")
    public ResponseEntity rejectOrderRequest(@PathVariable String orderId)
    {
        return responseEntity=new ResponseEntity(restaurantService.deleteOrder(orderId),HttpStatus.OK);
    }

    @PutMapping("/update-order/{orderId}")
    public ResponseEntity updateOrderStatus(@PathVariable String orderId)
    {
        return responseEntity=new ResponseEntity(restaurantService.updateOrderStatus(orderId),HttpStatus.OK);
    }



    //    //---------
//    //fallback methods
    public ResponseEntity fallbackLogin(@RequestBody Restaurant restaurant)
            throws Exception{
        String message="Sorry!!!!Service you are requesting is currently down";
        return new ResponseEntity(message,HttpStatus.BAD_REQUEST);
    }
    public ResponseEntity fallbackAddDish(@RequestBody Dish dish)
            throws Exception{
        String message="Sorry!!!!Service you are requesting is currently down";
        return new ResponseEntity(message,HttpStatus.BAD_REQUEST);
    }
    public ResponseEntity fallbackDeleteDish(@PathVariable String dishId)
            throws Exception{
        String message="Sorry!!!!Service you are requesting is currently down";
        return new ResponseEntity(message,HttpStatus.BAD_REQUEST);
    }
    public ResponseEntity fallbackUpdateDish(@PathVariable String dishId)
            throws Exception{
        String message="Sorry!!!!Service you are requesting is currently down";
        return new ResponseEntity(message,HttpStatus.BAD_REQUEST);
    }
    public ResponseEntity fallbackRetrieveAllDishes()
            throws Exception{
        String message="Sorry!!!!Service you are requesting is currently down";
        return new ResponseEntity(message,HttpStatus.BAD_REQUEST);
    }
    public ResponseEntity fallbackGetDishOfParticularDishId(@PathVariable String dishId)
            throws Exception{
        String message="Sorry!!!!Service you are requesting is currently down";
        return new ResponseEntity(message,HttpStatus.BAD_REQUEST);
    }
    public ResponseEntity fallbackAddImage(@RequestParam("image") MultipartFile image)
            throws Exception{
        String message="Sorry!!!!Service you are requesting is currently down";
        return new ResponseEntity(message,HttpStatus.BAD_REQUEST);
    }
    public ResponseEntity fallbackGetImage(@PathVariable String imageId)
            throws Exception{
        String message="Sorry!!!!Service you are requesting is currently down";
        return new ResponseEntity(message,HttpStatus.BAD_REQUEST);
    }
    public ResponseEntity fallbackUpdateImage(@PathVariable String imageId,@RequestParam("image") MultipartFile image)
            throws Exception{
        String message="Sorry!!!!Service you are requesting is currently down";
        return new ResponseEntity(message,HttpStatus.BAD_REQUEST);
    }
}