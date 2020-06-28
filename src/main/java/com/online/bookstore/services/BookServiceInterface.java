package com.online.bookstore.services;

import com.online.bookstore.dto.PaginatedBooks;
import com.online.bookstore.dto.request.BookUpdateRequest;
import com.online.bookstore.exception.BookNotFoundException;
import com.online.bookstore.model.Book;
import org.springframework.stereotype.Service;


@Service
public interface BookServiceInterface {

    Book addBook(Book book);

    void deleteBook(String isbn) throws BookNotFoundException;

    PaginatedBooks searchBooks(String searchKey, int pageNo, int pageSize) throws BookNotFoundException;

    Book getBook(String isbn) throws BookNotFoundException;

    Book updateBook(BookUpdateRequest bookUpdateRequest) throws BookNotFoundException;
}
