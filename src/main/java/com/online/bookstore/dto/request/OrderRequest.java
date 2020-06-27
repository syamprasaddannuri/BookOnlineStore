package com.online.bookstore.dto.request;

import com.online.bookstore.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest {
    private String id;
    private String ISBN;
    private double price;
    private OrderStatus orderStatus;
}
