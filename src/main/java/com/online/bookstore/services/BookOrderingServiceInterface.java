package com.online.bookstore.services;

import com.online.bookstore.dto.request.OrderRequest;
import com.online.bookstore.dto.response.OrderResponse;
import com.online.bookstore.exception.BookNotAvailableException;
import com.online.bookstore.exception.BookNotFoundException;
import com.online.bookstore.exception.InventoryNotAvailableException;
import com.online.bookstore.exception.InventoryNotFoundException;
import com.online.bookstore.model.Order;
import org.springframework.stereotype.Service;

@Service
public interface BookOrderingServiceInterface {
    OrderResponse buyBook(String isbn) throws BookNotAvailableException, InventoryNotFoundException, BookNotFoundException, InventoryNotAvailableException;

    Order storeOrderResponse(OrderRequest orderRequest);
}

