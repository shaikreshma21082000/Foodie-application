package com.niit.repository;

import com.niit.model.DishImage;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DishImageRepository extends MongoRepository<DishImage,String> {
}
