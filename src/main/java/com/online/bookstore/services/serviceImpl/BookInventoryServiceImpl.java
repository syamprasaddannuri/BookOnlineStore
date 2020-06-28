package com.online.bookstore.services.serviceImpl;

import com.online.bookstore.dto.request.BookInventoryRequest;
import com.online.bookstore.enums.BookInventoryRequestStatus;
import com.online.bookstore.exception.InvalidRequestException;
import com.online.bookstore.exception.InventoryNotFoundException;
import com.online.bookstore.model.BookInventory;
import com.online.bookstore.repositories.interfaces.BookInventoryRepoInterface;
import com.online.bookstore.services.BookInventoryServiceInterface;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookInventoryServiceImpl implements BookInventoryServiceInterface {

    private BookInventoryRepoInterface bookInventoryRepoInterface;

    static final Logger logger = LogManager.getLogger(BookInventoryServiceInterface.class.getName());

    @Autowired
    public BookInventoryServiceImpl(BookInventoryRepoInterface bookInventoryRepoInterface) {
        this.bookInventoryRepoInterface = bookInventoryRepoInterface;
    }

    @Override
    public BookInventory getInventory(String isbn) throws InventoryNotFoundException {
        BookInventory bookInventory = bookInventoryRepoInterface.findByISBN(isbn);
        if(bookInventory == null) {
            logger.error("Inventory Not Found For Given ISBN");
            throw new InventoryNotFoundException("Inventory Not Found For Given ISBN");
        }
        return bookInventory;
    }

    @Override
    public BookInventory updateInventory(BookInventoryRequest bookInventoryRequest) throws InventoryNotFoundException, InvalidRequestException {
        BookInventory bookInventory = bookInventoryRepoInterface.findByISBN(bookInventoryRequest.getIsbn());
        if (bookInventory == null) {
            bookInventory = createBookInventory(bookInventoryRequest.getIsbn());
        }
        if(bookInventoryRequest.getBookInventoryRequestStatus().equals(BookInventoryRequestStatus.Increment)) {
            bookInventory.setCount(bookInventory.getCount() + bookInventoryRequest.getQuantity());
        } else {
            if(bookInventory.getCount() - bookInventoryRequest.getQuantity() < 0) {
                throw new InvalidRequestException("Inventory update request invalid");
            }
            bookInventory.setCount(bookInventory.getCount() - bookInventoryRequest.getQuantity());
        }
        bookInventoryRepoInterface.save(bookInventory);
        return bookInventory;
    }

    @Override
    public BookInventory updateInventory(BookInventory bookInventory) throws InventoryNotFoundException {
        bookInventoryRepoInterface.save(bookInventory);
        return bookInventory;
    }

    private BookInventory createBookInventory(String isbn) {
        BookInventory bookInventory = new BookInventory();
        bookInventory.setISBN(isbn);
        bookInventoryRepoInterface.save(bookInventory);
        return bookInventory;
    }
}
