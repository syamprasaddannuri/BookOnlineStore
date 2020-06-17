package com.online.bookstore.services;

import com.online.bookstore.dto.response.BookResponseDto;
import com.online.bookstore.exception.BookNotAvailableException;
import com.online.bookstore.exception.BookNotFoundException;
import org.springframework.stereotype.Service;

@Service
public interface BookOrderingServiceInterface {
    BookResponseDto buyBook(String isbn) throws BookNotFoundException, BookNotAvailableException;
}
