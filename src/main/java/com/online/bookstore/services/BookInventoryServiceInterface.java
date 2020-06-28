package com.online.bookstore.services;

import com.online.bookstore.dto.request.BookInventoryRequest;
import com.online.bookstore.exception.InvalidRequestException;
import com.online.bookstore.exception.InventoryNotFoundException;
import com.online.bookstore.model.BookInventory;
import org.springframework.stereotype.Service;

@Service
public interface BookInventoryServiceInterface {

    BookInventory getInventory(String isbn) throws InventoryNotFoundException;

    BookInventory updateInventory(BookInventoryRequest bookInventoryRequest) throws InventoryNotFoundException, InvalidRequestException;

    BookInventory updateInventory(BookInventory bookInventory) throws InventoryNotFoundException;
}
