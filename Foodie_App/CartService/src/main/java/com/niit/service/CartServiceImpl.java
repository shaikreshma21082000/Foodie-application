package com.niit.service;

import com.niit.config.Producer;
import com.niit.model.Cart;
import com.niit.model.Dish;
import com.niit.model.Restaurant;
import com.niit.model.User;
import com.niit.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class CartServiceImpl implements CartService {
    CartRepository cartRepository;
    Producer producer;
    @Autowired
    public CartServiceImpl(CartRepository cr,Producer producer)
    {
        this.cartRepository=cr;
        this.producer=producer;
    }
    @Override
    public Cart addDish(Dish dish,Restaurant restaurant,String email) {
        System.out.println("entered into cart's impl ");
        Cart cart= cartRepository.findByEmailId(email);
        System.out.println("cart object : "+cart);
        List<Dish> list = cart.getDishList();
        if (list != null) {
            for(Dish d:list) {
                if (d.getDishId().equals(dish.getDishId())) {
                    System.out.println("inside if "+d.getDishId());
                    return null;
                }
            }
            if(cart.getRestaurant().getRestaurantName().equalsIgnoreCase(dish.getRestaurantName())&& cart.getRestaurant().getLocation().getState().equalsIgnoreCase(cart.getUser().getAddress().getState())&& restaurant.getLocation().getState().equalsIgnoreCase(cart.getRestaurant().getLocation().getState())&& restaurant.getRestaurantId().equalsIgnoreCase(cart.getRestaurant().getRestaurantId())) {
                list.add(dish);
                cart.setDishList(list);
            }
        }
        else {
            if(restaurant.getLocation().getState().equalsIgnoreCase(cart.getUser().getAddress().getState())) {
                list = new ArrayList<>();
                list.add(dish);
                cart.setRestaurant(restaurant);
                cart.setDishList(list);
            }
        }
        cart.setTotal();
        return cartRepository.save(cart);
    }

    @Override
    public Cart emptyCart(String emailId) {
        Cart cart=cartRepository.findByEmailId(emailId);
        System.out.println("cart service"+cart);
//        List<Dish> dishList=new ArrayList<>();
        cart.setDishList(null);
        cart.setRestaurant(null);
        cart.setTotal(0);
        cartRepository.save(cart);
        return cart;
    }

    @Override
    public Cart deleteDish(String dishId, String emailId) {
        int i,index=-2;
        Cart cart=cartRepository.findByEmailId(emailId);
        List<Dish> list=cart.getDishList();
        if (list!= null) {
            for (i = 0; i < list.size(); i++) {
                if (list.get(i).getDishId().equals(dishId)) {
                    index = i;
                    break;
                }
            }
            if(index!=-2 && i< list.size()){
                list.remove(index);
                cart.setDishList(list);
                cart.setTotal();
                return cartRepository.save(cart);

            }
        }
        return null;
    }

    @Override
    public Cart createCartObj(String emailId, User user) {
        Cart cart=new Cart();
        cart.setEmailId(emailId);
        cart.setUser(user);
        //cart.setRestaurant(new Restaurant());
        cart.setTotal(0);
        //cart.setDishList(new ArrayList<>());
        return cartRepository.save(cart);
    }

    @Override
    public Cart getCartDetails(String emailId) {
        return cartRepository.findByEmailId(emailId);
    }
    @Override
    public Object placeOrder(String emailId){
        System.out.println("entered into the service of cart");
        Cart c=this.getCartDetails(emailId);
        c.setTotal();
        System.out.println("retrieved cart============="+c);
        Object obj= producer.sendMessageToRabbitMq(c);
        this.emptyCart(c.getEmailId());
        return obj;
    }

}