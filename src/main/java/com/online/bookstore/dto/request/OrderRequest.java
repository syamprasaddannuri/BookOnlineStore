package com.online.bookstore.dto.request;

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
public class OrderRequest {
    private String id;
    private String ISBN;
    private Pricing pricing;
    private OrderStatus orderStatus;
}
