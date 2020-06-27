package com.online.bookstore.services;

import com.online.bookstore.dto.response.BookInventoryResponse;
import com.online.bookstore.exception.InventoryNotFoundException;
import com.online.bookstore.model.BookInventory;
import org.springframework.stereotype.Service;

@Service
public interface BookInventoryServiceInterface {
    BookInventory addInventory(String isbn);

    BookInventoryResponse getInventory(String isbn) throws InventoryNotFoundException;

    BookInventory deleteInventory(String isbn) throws InventoryNotFoundException;

    BookInventory decrementInventory(String isbn) throws InventoryNotFoundException;
}
