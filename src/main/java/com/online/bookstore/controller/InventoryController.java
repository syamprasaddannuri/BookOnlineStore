package com.online.bookstore.controller;

import com.online.bookstore.constants.URIEndpoints;
import com.online.bookstore.exception.InventoryNotFoundException;
import com.online.bookstore.services.BookInventoryServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(URIEndpoints.INVENTORY_API)
public class InventoryController {

    private BookInventoryServiceInterface bookInventoryServiceInterface;

    @Autowired
    public InventoryController(BookInventoryServiceInterface bookInventoryServiceInterface) {
        this.bookInventoryServiceInterface = bookInventoryServiceInterface;
    }

    @PostMapping
    public ResponseEntity addInventory (@RequestParam ("ISBN") String ISBN) throws InventoryNotFoundException {
        return ResponseEntity.ok(bookInventoryServiceInterface.addInventory(ISBN));
    }

    @PostMapping
    public ResponseEntity getInventory (@RequestParam ("ISBN") String ISBN) throws InventoryNotFoundException {
        return ResponseEntity.ok(bookInventoryServiceInterface.getInventory(ISBN));
    }
}
