package com.niit.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Document
public class Cart {
    @Id
    private String cartId;
    private String emailId;
    private List<Dish> dishList;
    private double total;
    private User user;
    private Restaurant restaurant;

    public void setTotal() {
        AtomicReference<Double> sum= new AtomicReference<>((double) 0);
        dishList.forEach(item->{
            sum.set(item.getDishPrice()*item.getDishQuantity() + sum.get());
        });
        this.total = sum.get();
    }
}
