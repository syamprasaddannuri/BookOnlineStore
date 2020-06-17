package com.online.bookstore.services.serviceImpl;

import com.online.bookstore.convertor.BookConvertor;
import com.online.bookstore.dto.response.BookResponseDto;
import com.online.bookstore.exception.BookNotFoundException;
import com.online.bookstore.model.Book;
import com.online.bookstore.repositories.BookRepo;
import com.online.bookstore.services.BookOrderingServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookOrderingServiceImpl implements BookOrderingServiceInterface {

    private BookRepo bookRepo;
    private BookConvertor bookConvertor;

    @Autowired
    public BookOrderingServiceImpl(BookRepo bookRepo, BookConvertor bookConvertor) {
        this.bookRepo = bookRepo;
        this.bookConvertor = bookConvertor;
    }

    @Override
    public BookResponseDto buyBook(String isbn) {
        Book book = null;
        try {
            book = bookRepo.findByISBN(isbn);
            if(book == null) {
                throw new BookNotFoundException("Book Not Found For Given ISBN");
            }
        } catch (BookNotFoundException e) {
            e.printStackTrace();
        }
        return bookConvertor.convertToBookResponseDto(book);
    }
}
