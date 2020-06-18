package com.online.bookstore.services.serviceImpl;

import com.online.bookstore.convertor.BookConvertor;
import com.online.bookstore.dto.response.BookResponseDto;
import com.online.bookstore.exception.BookNotAvailableException;
import com.online.bookstore.exception.BookNotFoundException;
import com.online.bookstore.model.Book;
import com.online.bookstore.model.BookInventory;
import com.online.bookstore.repositories.BookInventoryRepo;
import com.online.bookstore.repositories.BookRepo;
import com.online.bookstore.services.BookOrderingServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookOrderingServiceImpl implements BookOrderingServiceInterface {

    private BookRepo bookRepo;
    private BookConvertor bookConvertor;
    private BookInventoryRepo bookInventoryRepo;

    @Autowired
    public BookOrderingServiceImpl(BookRepo bookRepo, BookConvertor bookConvertor, BookInventoryRepo bookInventoryRepo) {
        this.bookRepo = bookRepo;
        this.bookConvertor = bookConvertor;
        this.bookInventoryRepo = bookInventoryRepo;
    }

    @Override
    public BookResponseDto buyBook(String isbn) throws BookNotFoundException, BookNotAvailableException{
        BookInventory bookInventory = bookInventoryRepo.findByISBN(isbn);
        if(bookInventory.isStatus() == false) {
            throw new BookNotAvailableException("Book is not available for given isbn");
        }
        Book book = bookRepo.findByISBN(isbn);
        if(book == null) {
            throw new BookNotFoundException("Book Not Found For Given ISBN");
        }
        return bookConvertor.convertToBookResponseDto(book);
    }
}
