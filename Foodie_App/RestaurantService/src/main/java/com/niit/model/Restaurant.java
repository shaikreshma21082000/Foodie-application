package com.niit.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Document
public class Restaurant{
    @Id
    private String restaurantId;
    private String restaurantName;
    private String email;
    private String password;
    private Location location;
    private String restaurantRatings;
    private String restaurantCuisineType;
    private List<Dish> dishList;
    private Photo photo;
}
