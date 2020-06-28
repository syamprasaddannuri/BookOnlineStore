package com.online.bookstore.services;

import com.online.bookstore.exception.BookNotAvailableException;
import com.online.bookstore.exception.BookNotFoundException;
import com.online.bookstore.exception.InsufficientInventory;
import com.online.bookstore.exception.InventoryNotFoundException;
import com.online.bookstore.model.Order;
import org.springframework.stereotype.Service;

@Service
public interface BookOrderingServiceInterface {
    Order buyBook(String isbn, int quantity) throws BookNotAvailableException, InventoryNotFoundException, BookNotFoundException, InsufficientInventory;
}

