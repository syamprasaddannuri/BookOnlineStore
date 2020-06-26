package com.online.bookstore.services.serviceImpl;

import com.online.bookstore.convertor.BookConvertor;
import com.online.bookstore.dto.response.OrderResponse;
import com.online.bookstore.exception.BookNotAvailableException;
import com.online.bookstore.model.Book;
import com.online.bookstore.model.BookInventory;
import com.online.bookstore.repositories.interfaces.BookInventoryRepoInterface;
import com.online.bookstore.repositories.interfaces.BookRepoInterface;
import com.online.bookstore.services.BookOrderingServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookOrderingServiceImpl implements BookOrderingServiceInterface {

    private BookRepoInterface bookRepoInterface;
    private BookConvertor bookConvertor;
    private BookInventoryRepoInterface bookInventoryRepoInterface;

    @Autowired
    public BookOrderingServiceImpl(BookRepoInterface bookRepoInterface, BookConvertor bookConvertor, BookInventoryRepoInterface bookInventoryRepoInterface) {
        this.bookRepoInterface = bookRepoInterface;
        this.bookConvertor = bookConvertor;
        this.bookInventoryRepoInterface = bookInventoryRepoInterface;
    }

    @Override
    public OrderResponse buyBook(String isbn) throws BookNotAvailableException{
        BookInventory bookInventory = bookInventoryRepoInterface.findByISBN(isbn);
        OrderResponse orderResponse = null;
        orderResponse.setStatus(false);
        if(bookInventory.isStatus() == false) {
            throw new BookNotAvailableException("Book is not available for given isbn");
        }
        Book book = bookRepoInterface.findByISBN(isbn);
        orderResponse = new OrderResponse(bookConvertor.convertToBookResponseDto(book),true);
        return orderResponse;
    }
}
