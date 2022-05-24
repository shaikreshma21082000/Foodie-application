package com.rabbitmq.domain;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class FeedbackDTO {
    private String feedbackId;
    private String email;
    private String message;
    private String currentDate;

}
