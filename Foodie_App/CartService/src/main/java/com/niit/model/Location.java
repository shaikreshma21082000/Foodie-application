package com.niit.model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Location {
    private String area;
    private String city;
    private String state;
    private String pincode;
}
