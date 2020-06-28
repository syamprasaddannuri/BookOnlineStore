package com.online.bookstore.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "BookInventory")
public class BookInventory {
    @Id
    @Indexed(unique = true)
    private String ISBN;
    private int count;

    @Version
    private int version;
}
