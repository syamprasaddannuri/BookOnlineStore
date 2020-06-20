package com.online.bookstore.controller;

import com.online.bookstore.constants.URIEndpoints;
import com.online.bookstore.exception.BookNotAvailableException;
import com.online.bookstore.exception.BookNotFoundException;
import com.online.bookstore.services.BookOrderingServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(URIEndpoints.BOOK_ORDERING)
public class BookOrderingController {

    private BookOrderingServiceInterface bookOrderingServiceInterface;

    @Autowired
    public BookOrderingController(BookOrderingServiceInterface bookOrderingServiceInterface) {
        this.bookOrderingServiceInterface = bookOrderingServiceInterface;
    }

    @GetMapping
    public ResponseEntity buyBook(@RequestParam ("ISBN") String ISBN) throws BookNotAvailableException {
        return ResponseEntity.ok(bookOrderingServiceInterface.buyBook(ISBN));
    }
}
