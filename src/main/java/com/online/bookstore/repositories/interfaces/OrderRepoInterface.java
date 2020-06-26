package com.online.bookstore.repositories.interfaces;

import com.online.bookstore.model.Order;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepoInterface {
    Order save(Order order);
}
