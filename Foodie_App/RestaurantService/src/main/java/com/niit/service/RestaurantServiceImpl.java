package com.niit.service;

import com.niit.config.DeleteOrderProducer;
import com.niit.config.GetOrdersProducer;
import com.niit.config.NotificationProducer;
import com.niit.config.UpdateOrderStatusProducer;
import com.niit.exception.DishAlreadyExistsException;
import com.niit.exception.DishNotFoundException;
import com.niit.exception.RestaurantNotFoundException;
import com.niit.exception.UserNotFoundException;
import com.niit.model.Dish;
import com.niit.model.DishImage;
import com.niit.model.Restaurant;
import com.niit.model.UserRequest;
import com.niit.repository.DishImageRepository;
import com.niit.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class RestaurantServiceImpl implements RestaurantService{
    RestaurantRepository restaurantRepository;
    public Restaurant restObject;
    static String ImageId;
    DishImageRepository dishImageRepository;
    GetOrdersProducer getOrdersProducer;
    DeleteOrderProducer deleteOrderProducer;
    UpdateOrderStatusProducer updateOrderStatusProducer;
    NotificationProducer notificationProducer;

    public RestaurantServiceImpl() {
    }

    @Autowired
    public RestaurantServiceImpl(RestaurantRepository restaurantRepository,DishImageRepository dishImageRepository,GetOrdersProducer getOrdersProducer,DeleteOrderProducer deleteOrderProducer,UpdateOrderStatusProducer updateOrderStatusProducer,NotificationProducer notificationProducer)
    {
        this.restaurantRepository=restaurantRepository;
        this.dishImageRepository=dishImageRepository;
        this.getOrdersProducer=getOrdersProducer;
        this.deleteOrderProducer=deleteOrderProducer;
        this.updateOrderStatusProducer=updateOrderStatusProducer;
        this.notificationProducer=notificationProducer;

    }

    @Override
    public Restaurant checkIfAuthenticated(UserRequest userRequest) throws RestaurantNotFoundException, UserNotFoundException {
        System.out.println("Got the userRequest here : "+userRequest);
        Restaurant restaurant = restaurantRepository.findByEmailAndPassword(userRequest.getEmail(), userRequest.getPassword());
        if(restaurant == null)
            throw new RestaurantNotFoundException();
        restObject=restaurant;
        System.out.println("restaurantDetails: "+restObject);
        return restaurant;
    }

    @Override
    public Dish addDishToMenu(Dish dish) throws DishAlreadyExistsException, IOException {
        List<Dish> dishList=restObject.getDishList();
        System.out.println("send dish id--"+dish.getDishId());
        if (dishList!= null) {
            for(Dish d:dishList) {
                System.out.println("dish idss---"+d.getDishId());
                System.out.println("send dish-----"+dish.getDishId());
                if (d.getDishId().equals(dish.getDishId())) {
                    throw new DishAlreadyExistsException();
                }
            }
            dish.setRestaurantName(restObject.getRestaurantName());
            System.out.println("image id "+RestaurantServiceImpl.ImageId);
            if(RestaurantServiceImpl.ImageId!=null && RestaurantServiceImpl.ImageId!="")
            {
                DishImage d=dishImageRepository.findById(RestaurantServiceImpl.ImageId).get();
                d.setTitle(dish.getDishId());
                dish.setDishImage(d);
            }
            else
            {
                RestaurantServiceImpl.ImageId="no-image.jpg";
                DishImage d=dishImageRepository.findById(RestaurantServiceImpl.ImageId).get();
                d.setTitle(dish.getDishId());
                dish.setDishImage(d);
            }
            dishList.add(dish);
            RestaurantServiceImpl.ImageId="";
            restObject.setDishList(dishList);
        }
        else {
            dishList = new ArrayList<>();
            dishList.add(dish);
            restObject.setDishList(dishList);
        }
        restaurantRepository.save(restObject);
        return dish;
    }

    @Override
    public Boolean deleteADishFromMenu(String dishId) throws DishNotFoundException {
        List<Dish> list=restObject.getDishList();
        int i,index=0;
        if (list!= null) {
            for (i = 0; i < list.size(); i++) {
                if (list.get(i).getDishId().equals(dishId)) {
                    index = i;
                    break;
                }
            }
            if(index!=-2 && i< list.size()){
                list.remove(index);
                restObject.setDishList(list);
                restaurantRepository.save(restObject);
                return true;
            }
        }
        return false;
    }

    @Override
    public Dish updateDishInMenu(String dishId,Dish dish) throws DishNotFoundException {
        //RestaurantServiceImpl.ImageId=dish.getDishImage().getImageId();
        System.out.println("entered into update"+RestaurantServiceImpl.ImageId);
        System.out.println("dish id is-----"+dishId);
        int i=0,index=-2;
        String dId="";
        Dish oldDish = null;
        List<Dish> list=restObject.getDishList();
        if(list!=null){
            System.out.println("list of dishes is not empty");
            for(i=0;i<list.size();i++){
                System.out.println("dish idssss"+list.get(i).getDishId());
                if(list.get(i).getDishId().equals(dishId)){
                    dId=list.get(i).getDishId();
                    index=i;
                    oldDish=list.get(i);
                    break;
                }
            }
        }
        if(index!=-2 && i< list.size()){
            System.out.println("old dish object"+oldDish);
            String imageId=oldDish.getDishImage().getImageId();
            DishImage oldImage=dishImageRepository.findById(imageId).get();
            list.remove(index);
            if(RestaurantServiceImpl.ImageId!=null && RestaurantServiceImpl.ImageId!="")
            {
                DishImage d=dishImageRepository.findById(RestaurantServiceImpl.ImageId).get();
                d.setTitle(dish.getDishId());
                dish.setDishImage(d);
            }
            else {
                System.out.println("setting the old image");
                oldImage.setTitle(dish.getDishId());
                dish.setDishImage(oldImage);
            }
            dish.setDishId(dId);
            dish.setRestaurantName(restObject.getRestaurantName());
            System.out.println("updateddd dishhhhh"+dish);
            list.add(dish);
        }
        restObject.setDishList(list);
        RestaurantServiceImpl.ImageId="";
        restaurantRepository.save(restObject);
        return dish;
    }



    @Override
    public List<Dish> getAllDishes() {
        List<Dish> dishList=restObject.getDishList();
        return dishList;
    }

    @Override
    public Dish getADishOfParticularId(String dishId) throws DishNotFoundException{
        List<Dish> dishList=restObject.getDishList();
        Dish dish=new Dish();
        if(dishList!=null)
        {
            for(Dish d:dishList)
            {
                if(d.getDishId().equals(dishId)) {
                    dish=d;
                    break;
                }
            }
        }
        if(dish==null)
            throw new DishNotFoundException();
        dish.getDishImage();
        return dish;
    }

    @Override
    public Object getAllOrdersOfRestaurant(String restaurantId) {
        return getOrdersProducer.sendMessageToRabbitMq(restaurantId);
    }

    @Override
    public Object deleteOrder(String orderId) {
        Object obj=deleteOrderProducer.sendMessageToOrderService(orderId);
        System.out.println("deleted order: "+obj);
        return returnOrderNotification(obj);
//       return obj;
    }

    @Override
    public Object updateOrderStatus(String orderId) {
        Object obj=updateOrderStatusProducer.sendMessageToOrderThroughRabbitMq(orderId);
//        System.out.println("updated order"+obj);
        return returnOrderNotification(obj);
    }
    public Object returnOrderNotification(Object obj)
    {
        return notificationProducer.sendNotificationToRabbitMq(obj);
    }
}