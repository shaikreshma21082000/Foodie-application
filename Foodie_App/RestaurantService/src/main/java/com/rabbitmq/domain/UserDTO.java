package com.rabbitmq.domain;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserDTO {
    private String email;
    private String firstName;
    private String lastName;
    private String password;
    private String dateOfBirth;
    private String gender;
    private AddressDTO address;

}
