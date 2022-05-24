package com.niit.model;
import lombok.*;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class User {
    private String email;
    private String firstName;
    private String lastName;
    private String password;
    private String dateOfBirth;
    private String gender;
    private Address address;
}
