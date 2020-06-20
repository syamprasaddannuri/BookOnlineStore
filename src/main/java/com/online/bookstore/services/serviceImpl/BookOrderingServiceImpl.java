package com.online.bookstore.services.serviceImpl;

import com.online.bookstore.convertor.BookConvertor;
import com.online.bookstore.dto.response.BookResponseDto;
import com.online.bookstore.dto.response.OrderResponse;
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
    public OrderResponse buyBook(String isbn) throws BookNotAvailableException{
        BookInventory bookInventory = bookInventoryRepo.findByISBN(isbn);
        OrderResponse orderResponse = null;
        orderResponse.setStatus(false);
        if(bookInventory.isStatus() == false) {
            throw new BookNotAvailableException("Book is not available for given isbn");
        }
        Book book = bookRepo.findByISBN(isbn);
        orderResponse = new OrderResponse(bookConvertor.convertToBookResponseDto(book),true);
        return orderResponse;
    }
}
