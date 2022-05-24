package com.niit.model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Restaurant {
    private String restaurantId;
    private String restaurantName;
    private Location location;
    private String restaurantRatings;
    private String restaurantCuisineType;
}
