package com.online.bookstore.controller;

import com.online.bookstore.constants.UriEndpoints;
import com.online.bookstore.dto.request.BookRequestDto;
import com.online.bookstore.dto.request.BookStatusRequestDto;
import com.online.bookstore.exception.BookNotFoundException;
import com.online.bookstore.services.BookServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping(UriEndpoints.ONLINE_BOOK_STORE_API)
public class BookController {

    private BookServiceInterface bookServiceInterface;

    @Autowired
    public BookController(BookServiceInterface bookServiceInterface) {
        this.bookServiceInterface = bookServiceInterface;
    }

    @PostMapping
    public ResponseEntity addBook(@Valid @RequestBody BookRequestDto bookRequestDto) {
        return ResponseEntity.ok(bookServiceInterface.addBook(bookRequestDto));
    }

    @DeleteMapping
    public void deleteBook(@RequestParam("ISBN") String ISBN) throws BookNotFoundException {
        bookServiceInterface.deleteBook(ISBN);
    }

    @GetMapping
    public ResponseEntity searchBook(@RequestParam ("searchKey") String searchKey, @RequestParam ("pageNo") int pageNo, @RequestParam ("pageSize") int pageSize) throws BookNotFoundException {
        return ResponseEntity.ok(bookServiceInterface.searchBooks(searchKey,pageNo,pageSize));
    }

    @GetMapping(UriEndpoints.GET_STATUS)
    public ResponseEntity getBookStatus(@RequestParam("ISBN") String isbn) throws BookNotFoundException {
        return ResponseEntity.ok(bookServiceInterface.getStatusOfBook(isbn));
    }

    @PostMapping(UriEndpoints.UPDATE_BOOK_STATUS)
    public ResponseEntity updateBookStatus(@RequestBody BookStatusRequestDto bookStatusRequestDto) throws BookNotFoundException {
        return ResponseEntity.ok(bookServiceInterface.updateBookStatus(bookStatusRequestDto));
    }
}
