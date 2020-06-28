package com.online.bookstore.controller;

import com.online.bookstore.constants.UriEndpoints;
import com.online.bookstore.dto.request.BookInventoryRequest;
import com.online.bookstore.exception.InvalidRequestException;
import com.online.bookstore.exception.InventoryNotFoundException;
import com.online.bookstore.services.BookInventoryServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(UriEndpoints.INVENTORY_API)
public class BookInventoryController {

    private BookInventoryServiceInterface bookInventoryServiceInterface;

    @Autowired
    public BookInventoryController(BookInventoryServiceInterface bookInventoryServiceInterface) {
        this.bookInventoryServiceInterface = bookInventoryServiceInterface;
    }

    @PostMapping
    public ResponseEntity updateInventory(@RequestBody BookInventoryRequest bookInventoryRequest) throws InventoryNotFoundException, InvalidRequestException {
        return ResponseEntity.ok(bookInventoryServiceInterface.updateInventory(bookInventoryRequest));
    }

    @GetMapping(UriEndpoints.GET_INVENTORY)
    public ResponseEntity getInventory (@RequestParam ("ISBN") String ISBN) throws InventoryNotFoundException {
        return ResponseEntity.ok(bookInventoryServiceInterface.getInventory(ISBN));
    }

}
