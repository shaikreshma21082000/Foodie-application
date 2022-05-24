package com.niit.model;
import lombok.*;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Address {
    private String houseNo;
    private String street;
    private String city;
    private String state;
    private String pincode;
}
