package com.online.bookstore.services;

import com.online.bookstore.dto.request.BookRequestDto;
import com.online.bookstore.dto.response.BookResponseDto;
import com.online.bookstore.exception.BookNotFoundException;
import com.online.bookstore.exception.UserNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BookServiceInterface {
    BookResponseDto addBook(BookRequestDto bookRequestDto);

    BookResponseDto deleteBook(String isbn) throws BookNotFoundException;

    List<BookResponseDto> searchBooks(String searchKey) throws BookNotFoundException, UserNotFoundException;
}
