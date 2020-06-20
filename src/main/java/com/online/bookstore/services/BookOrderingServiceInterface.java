package com.online.bookstore.services;

import com.online.bookstore.dto.response.OrderResponse;
import com.online.bookstore.exception.BookNotAvailableException;
import org.springframework.stereotype.Service;

@Service
public interface BookOrderingServiceInterface {
    OrderResponse buyBook(String isbn) throws BookNotAvailableException;
}
