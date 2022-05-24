package com.rabbitmq.domain;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AddressDTO {
    private String houseNo;
    private String street;
    private String city;
    private String state;
    private String pincode;

}
