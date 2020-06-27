package com.online.bookstore.controller;

import com.online.bookstore.constants.URIEndpoints;
import com.online.bookstore.dto.request.OrderRequest;
import com.online.bookstore.exception.BookNotAvailableException;
import com.online.bookstore.exception.InventoryNotFoundException;
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
    public ResponseEntity buyBook(@RequestParam ("ISBN") String ISBN) throws BookNotAvailableException, InventoryNotFoundException {
        return ResponseEntity.ok(bookOrderingServiceInterface.buyBook(ISBN));
    }

    @PostMapping
    public ResponseEntity storeOrderResponse(@RequestBody OrderRequest orderRequest) {
        return ResponseEntity.ok(bookOrderingServiceInterface.storeOrderResponse(orderRequest));
    }
}
