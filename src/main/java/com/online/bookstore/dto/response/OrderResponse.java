package com.online.bookstore.dto.response;

import com.online.bookstore.enums.OrderStatus;
import com.online.bookstore.model.Pricing;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponse {
    private String id;
    private String ISBN;
    private Pricing pricing;
    private OrderStatus orderStatus;
}
