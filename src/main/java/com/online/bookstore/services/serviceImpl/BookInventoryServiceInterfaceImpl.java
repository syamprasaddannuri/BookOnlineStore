package com.online.bookstore.services.serviceImpl;

import com.online.bookstore.enums.BookStatus;
import com.online.bookstore.exception.InventoryNotAvailableException;
import com.online.bookstore.exception.InventoryNotFoundException;
import com.online.bookstore.model.BookInventory;
import com.online.bookstore.repositories.interfaces.BookInventoryRepoInterface;
import com.online.bookstore.services.BookInventoryServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookInventoryServiceInterfaceImpl implements BookInventoryServiceInterface {

    private BookInventoryRepoInterface bookInventoryRepoInterface;

    @Autowired
    public BookInventoryServiceInterfaceImpl(BookInventoryRepoInterface bookInventoryRepoInterface) {
        this.bookInventoryRepoInterface = bookInventoryRepoInterface;
    }

    @Override
    public BookInventory addInventory(String isbn) {
        BookInventory bookInventory = bookInventoryRepoInterface.findByISBN(isbn);
        if(bookInventory == null) {
            bookInventory.setCount(1);
            bookInventory.setISBN(isbn);
            bookInventory.setBookStatus(BookStatus.Available);
            bookInventoryRepoInterface.save(bookInventory);
        } else {
            bookInventory.setCount(bookInventory.getCount() + 1);
            bookInventoryRepoInterface.save(bookInventory);
        }
        return bookInventory;
    }

    @Override
    public BookInventory getInventory(String isbn) throws InventoryNotFoundException, InventoryNotAvailableException{
        BookInventory bookInventory = bookInventoryRepoInterface.findByISBN(isbn);
        if(bookInventory == null) {
            throw new InventoryNotFoundException("Inventory Not Found For Given ISBN");
        }
        if(bookInventory.getBookStatus().getType().equals("Not available")) {
            throw new InventoryNotAvailableException("Inventory not available for given ISBN");
        }
        return bookInventory;
    }

    @Override
    public BookInventory deleteInventory(String isbn) throws InventoryNotFoundException, InventoryNotAvailableException{
        BookInventory bookInventory = bookInventoryRepoInterface.findByISBN(isbn);
        if(bookInventory == null) {
            throw new InventoryNotFoundException("Inventory Not Found For Given ISBN");
        }
        bookInventory.setBookStatus(BookStatus.NotAvailable);
        bookInventoryRepoInterface.save(bookInventory);
        return bookInventory;
    }

    @Override
    public BookInventory decrementInventory(String isbn) throws InventoryNotFoundException, InventoryNotAvailableException{
        BookInventory bookInventory = bookInventoryRepoInterface.findByISBN(isbn);
        if(bookInventory == null) {
            throw new InventoryNotFoundException("Inventory Not Found For Given ISBN");
        }
        if(bookInventory.getBookStatus().getType().equals("Not available")) {
            throw new InventoryNotAvailableException("Inventory not available for given ISBN");
        }
        bookInventory.setCount(bookInventory.getCount() - 1);
        bookInventoryRepoInterface.save(bookInventory);
        return bookInventory;
    }
}
