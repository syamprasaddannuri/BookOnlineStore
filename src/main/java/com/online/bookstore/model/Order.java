package com.online.bookstore.model;

import com.online.bookstore.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "Order")
public class Order {
    @Id
    private String id;
    private String ISBN;
    private double price;
    private int quantity;
    private OrderStatus orderStatus;
}
