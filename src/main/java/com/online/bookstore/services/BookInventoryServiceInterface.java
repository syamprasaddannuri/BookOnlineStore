package com.online.bookstore.services;

import com.online.bookstore.exception.InventoryNotAvailableException;
import com.online.bookstore.exception.InventoryNotFoundException;
import com.online.bookstore.model.BookInventory;
import org.springframework.stereotype.Service;

@Service
public interface BookInventoryServiceInterface {
    BookInventory addInventory(String isbn);

    BookInventory getInventory(String isbn) throws InventoryNotFoundException, InventoryNotAvailableException;

    BookInventory deleteInventory(String isbn) throws InventoryNotFoundException, InventoryNotAvailableException;

    BookInventory decrementInventory(String isbn) throws InventoryNotFoundException, InventoryNotAvailableException;
}
