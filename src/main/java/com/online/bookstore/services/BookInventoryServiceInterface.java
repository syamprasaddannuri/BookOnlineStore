package com.online.bookstore.services;

import com.online.bookstore.dto.request.BookInventoryRequest;
import com.online.bookstore.dto.response.BookInventoryResponse;
import com.online.bookstore.exception.InventoryNotFoundException;
import com.online.bookstore.model.BookInventory;
import org.springframework.stereotype.Service;

@Service
public interface BookInventoryServiceInterface {

    BookInventoryResponse getInventory(String isbn) throws InventoryNotFoundException;

    BookInventory updateInventory(BookInventoryRequest bookInventoryRequest) throws InventoryNotFoundException;
}
