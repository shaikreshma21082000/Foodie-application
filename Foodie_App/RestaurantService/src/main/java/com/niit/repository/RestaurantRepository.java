package com.niit.repository;

import com.niit.model.Restaurant;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface RestaurantRepository extends MongoRepository<Restaurant,String> {
    @Query("{$and:[{email:?0},{password:?1}]}")
    Restaurant findByEmailAndPassword(String email, String password);
}
