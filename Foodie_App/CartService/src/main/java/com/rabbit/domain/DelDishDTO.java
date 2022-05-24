package com.rabbitmq.domain;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class DelDishDTO {
    private String dishId;
    private String email;

}
