package com.online.bookstore.controller;

import com.online.bookstore.constants.UriEndpoints;
import com.online.bookstore.exception.BookNotAvailableException;
import com.online.bookstore.exception.BookNotFoundException;
import com.online.bookstore.exception.InsufficientInventory;
import com.online.bookstore.exception.InventoryNotFoundException;
import com.online.bookstore.services.BookOrderingServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(UriEndpoints.BOOK_ORDERING)
public class BookOrderingController {

    private BookOrderingServiceInterface bookOrderingServiceInterface;

    @Autowired
    public BookOrderingController(BookOrderingServiceInterface bookOrderingServiceInterface) {
        this.bookOrderingServiceInterface = bookOrderingServiceInterface;
    }

    @GetMapping
    public ResponseEntity buyBook(@RequestParam ("ISBN") String ISBN, @RequestParam ("quantity") int quantity) throws BookNotAvailableException, InventoryNotFoundException, BookNotFoundException, InsufficientInventory {
        return ResponseEntity.ok(bookOrderingServiceInterface.buyBook(ISBN,quantity));
    }
}
