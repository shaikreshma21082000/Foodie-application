package com.rabbit.domain;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserRequestDTO {
// User part--------------------------------
    private String email;
    private String firstName;
    private String lastName;
    private String password;
    private String dateOfBirth;
    private String gender;
    private String path;
    private String role;

// Address part------------------------------
    private String houseNo;
    private String street;
    private String city;
    private String state;
    private String pincode;
}
