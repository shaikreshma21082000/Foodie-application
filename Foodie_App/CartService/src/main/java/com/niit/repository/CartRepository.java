package com.niit.repository;

import com.niit.model.Cart;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends MongoRepository<Cart,String> {
    @Query("{'emailId':{$in:[?0]}}")
    Cart findByEmailId(String emailId);
}
