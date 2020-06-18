package com.online.bookstore.services;

import com.online.bookstore.dto.PaginatedBooks;
import com.online.bookstore.dto.request.BookRequestDto;
import com.online.bookstore.dto.response.BookResponseDto;
import com.online.bookstore.exception.BookNotFoundException;
import com.online.bookstore.exception.UserNotFoundException;
import org.springframework.stereotype.Service;


@Service
public interface BookServiceInterface {
    BookResponseDto addBook(BookRequestDto bookRequestDto);

    BookResponseDto deleteBook(String isbn) throws BookNotFoundException;

    PaginatedBooks searchBooks(String searchKey, int pageNo, int pageSize) throws BookNotFoundException, UserNotFoundException;
}
