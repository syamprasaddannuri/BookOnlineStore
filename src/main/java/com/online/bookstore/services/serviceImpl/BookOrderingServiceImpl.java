package com.online.bookstore.services.serviceImpl;

import com.online.bookstore.dto.response.OrderResponse;
import com.online.bookstore.dto.response.PriceResponseDto;
import com.online.bookstore.enums.OrderStatus;
import com.online.bookstore.exception.BookNotAvailableException;
import com.online.bookstore.exception.BookNotFoundException;
import com.online.bookstore.exception.InventoryNotAvailableException;
import com.online.bookstore.exception.InventoryNotFoundException;
import com.online.bookstore.model.Book;
import com.online.bookstore.model.BookInventory;
import com.online.bookstore.model.Pricing;
import com.online.bookstore.repositories.interfaces.BookRepoInterface;
import com.online.bookstore.services.BookInventoryServiceInterface;
import com.online.bookstore.services.BookOrderingServiceInterface;
import com.online.bookstore.services.PricingServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookOrderingServiceImpl implements BookOrderingServiceInterface {

    private PricingServiceInterface pricingServiceInterface;
    private BookInventoryServiceInterface bookInventoryServiceInterface;
    private BookRepoInterface bookRepoInterface;

    @Autowired
    public BookOrderingServiceImpl( BookInventoryServiceInterface bookInventoryServiceInterface, PricingServiceInterface pricingServiceInterface, BookRepoInterface bookRepoInterface) {
        this.bookInventoryServiceInterface = bookInventoryServiceInterface;
        this.pricingServiceInterface = pricingServiceInterface;
        this.bookRepoInterface = bookRepoInterface;
    }

    @Override
    public OrderResponse buyBook(String isbn) throws BookNotAvailableException, InventoryNotFoundException, BookNotFoundException, InventoryNotAvailableException {
        BookInventory bookInventory = bookInventoryServiceInterface.getInventory(isbn);
        if(bookInventory == null) {
            throw new InventoryNotFoundException("Book Inventory not found for the given book");
        }
        if(bookInventory.getBookStatus().getType().equals("Not Available")) {
            throw new BookNotAvailableException("Book is not available for given isbn");
        }
        PriceResponseDto priceResponseDto = pricingServiceInterface.getPriceForBook(isbn);
        if(priceResponseDto == null) {
            throw new BookNotFoundException("Book is not found");
        }
        Book book = bookRepoInterface.findByISBN(bookInventory.getISBN());
        Pricing pricing = new Pricing(priceResponseDto.getIsbn(),priceResponseDto.getPrice(),priceResponseDto.getDate());
        OrderResponse orderResponse = new OrderResponse();
        orderResponse.setISBN(book.getISBN());
        orderResponse.setPricing(pricing);
        orderResponse.setOrderStatus(OrderStatus.Created);
        return orderResponse;
    }
}
