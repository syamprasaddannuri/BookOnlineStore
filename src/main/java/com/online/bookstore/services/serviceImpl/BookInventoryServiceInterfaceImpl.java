package com.online.bookstore.services.serviceImpl;

import com.online.bookstore.dto.request.BookInventoryRequest;
import com.online.bookstore.dto.response.BookInventoryResponse;
import com.online.bookstore.enums.BookStatus;
import com.online.bookstore.enums.BookInventoryRequestStatus;
import com.online.bookstore.exception.InventoryNotFoundException;
import com.online.bookstore.model.BookInventory;
import com.online.bookstore.repositories.interfaces.BookInventoryRepoInterface;
import com.online.bookstore.services.BookInventoryServiceInterface;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookInventoryServiceInterfaceImpl implements BookInventoryServiceInterface {

    private BookInventoryRepoInterface bookInventoryRepoInterface;

    static final Logger logger = LogManager.getLogger(BookInventoryServiceInterface.class.getName());

    @Autowired
    public BookInventoryServiceInterfaceImpl(BookInventoryRepoInterface bookInventoryRepoInterface) {
        this.bookInventoryRepoInterface = bookInventoryRepoInterface;
    }

    @Override
    public BookInventoryResponse getInventory(String isbn) throws InventoryNotFoundException {
        BookInventory bookInventory = bookInventoryRepoInterface.findByISBN(isbn);
        if(bookInventory == null) {
            logger.error("Inventory Not Found For Given ISBN");
            throw new InventoryNotFoundException("Inventory Not Found For Given ISBN");
        }
        BookInventoryResponse bookInventoryResponse = new BookInventoryResponse(bookInventory.getISBN(),bookInventory.getCount());
        return bookInventoryResponse;
    }

    @Override
    public BookInventory updateInventory(BookInventoryRequest bookInventoryRequest) throws InventoryNotFoundException {
        BookInventory bookInventory = bookInventoryRepoInterface.findByISBN(bookInventoryRequest.getIsbn());
        if(bookInventory == null) {
            throw new InventoryNotFoundException("inventory not found for given isbn");
        }
        if(bookInventoryRequest.getBookInventoryRequestStatus().equals(BookInventoryRequestStatus.Increment)) {
            if(bookInventory == null) {
                bookInventory.setCount(1);
                bookInventory.setISBN(bookInventoryRequest.getIsbn());
                bookInventoryRepoInterface.save(bookInventory);
            } else {
                bookInventory.setCount(bookInventory.getCount() + 1);
                bookInventoryRepoInterface.save(bookInventory);
            }
        } else if(bookInventoryRequest.getBookInventoryRequestStatus().equals(BookInventoryRequestStatus.Decrement)){
                bookInventory.setCount(bookInventory.getCount() - 1);
        }
        bookInventoryRepoInterface.save(bookInventory);
        return bookInventory;
    }
}
