package com.online.bookstore.controller;

import com.online.bookstore.constants.URIEndpoints;
import com.online.bookstore.exception.InventoryNotAvailableException;
import com.online.bookstore.exception.InventoryNotFoundException;
import com.online.bookstore.services.BookInventoryServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(URIEndpoints.INVENTORY_API)
public class InventoryController {

    private BookInventoryServiceInterface bookInventoryServiceInterface;

    @Autowired
    public InventoryController(BookInventoryServiceInterface bookInventoryServiceInterface) {
        this.bookInventoryServiceInterface = bookInventoryServiceInterface;
    }

    @PostMapping(URIEndpoints.ADD_INVENTORY)
    public ResponseEntity addInventory (@RequestParam ("ISBN") String ISBN) {
        return ResponseEntity.ok(bookInventoryServiceInterface.addInventory(ISBN));
    }

    @PostMapping(URIEndpoints.DECREMENT_INVENTORY)
    public ResponseEntity decrementInventory (@RequestParam ("ISBN") String ISBN) throws InventoryNotFoundException, InventoryNotAvailableException {
        return ResponseEntity.ok(bookInventoryServiceInterface.decrementInventory(ISBN));
    }

    @GetMapping(URIEndpoints.GET_INVENTORY)
    public ResponseEntity getInventory (@RequestParam ("ISBN") String ISBN) throws InventoryNotFoundException, InventoryNotAvailableException {
        return ResponseEntity.ok(bookInventoryServiceInterface.getInventory(ISBN));
    }

    @PostMapping(URIEndpoints.DELETE_INVENTORY)
    public ResponseEntity deleteInventory(@RequestParam ("ISBN") String ISBN) throws InventoryNotFoundException, InventoryNotAvailableException {
        return ResponseEntity.ok(bookInventoryServiceInterface.deleteInventory(ISBN));
    }
}
