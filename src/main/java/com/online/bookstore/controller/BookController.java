package com.online.bookstore.controller;

import com.online.bookstore.constants.URIEndpoints;
import com.online.bookstore.dto.request.BookRequestDto;
import com.online.bookstore.exception.BookNotFoundException;
import com.online.bookstore.exception.UserNotFoundException;
import com.online.bookstore.services.BookServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(URIEndpoints.ONLINE_BOOK_STORE_API)
public class BookController {

    private BookServiceInterface bookServiceInterface;

    @Autowired
    public BookController(BookServiceInterface bookServiceInterface) {
        this.bookServiceInterface = bookServiceInterface;
    }

    @PostMapping
    public ResponseEntity addBook(@RequestBody BookRequestDto bookRequestDto) {
        return ResponseEntity.ok(bookServiceInterface.addBook(bookRequestDto));
    }

    @DeleteMapping (URIEndpoints.DELETE_BOOK)
    public ResponseEntity deleteBook(@RequestParam("ISBN") String ISBN) throws BookNotFoundException {
        return ResponseEntity.ok(bookServiceInterface.deleteBook(ISBN));
    }

    @GetMapping
    public ResponseEntity searchBook(@RequestParam ("searchKey") String searchKey) throws BookNotFoundException, UserNotFoundException {
        return ResponseEntity.ok(bookServiceInterface.searchBooks(searchKey));
    }
}
