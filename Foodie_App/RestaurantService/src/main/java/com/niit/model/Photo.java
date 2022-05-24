package com.niit.model;

import lombok.*;
import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Document
public class Photo {
    @Id
    private String id;
    private String title;
    private Binary image;

    public Photo(String title) {
        this.title = title;
    }
}
