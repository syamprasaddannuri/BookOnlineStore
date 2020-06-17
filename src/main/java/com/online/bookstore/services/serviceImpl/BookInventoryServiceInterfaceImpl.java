package com.online.bookstore.services.serviceImpl;

import com.online.bookstore.exception.InventoryNotAvailableException;
import com.online.bookstore.exception.InventoryNotFoundException;
import com.online.bookstore.model.BookInventory;
import com.online.bookstore.repositories.BookInventoryRepo;
import com.online.bookstore.services.BookInventoryServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookInventoryServiceInterfaceImpl implements BookInventoryServiceInterface {

    private BookInventoryRepo bookInventoryRepo;

    @Autowired
    public BookInventoryServiceInterfaceImpl(BookInventoryRepo bookInventoryRepo) {
        this.bookInventoryRepo = bookInventoryRepo;
    }

    @Override
    public BookInventory addInventory(String isbn) {
        BookInventory bookInventory = bookInventoryRepo.findByISBN(isbn);;
        if(bookInventory == null) {
            bookInventory.setCount(1);
            bookInventory.setISBN(isbn);
            bookInventory.setStatus(true);
            bookInventoryRepo.save(bookInventory);
        } else {
            bookInventory.setCount(bookInventory.getCount() + 1);
            bookInventoryRepo.save(bookInventory);
        }
        return bookInventory;
    }

    @Override
    public BookInventory getInventory(String isbn) {
        BookInventory bookInventory = null;
        try {
            bookInventory = bookInventoryRepo.findByISBN(isbn);
            if(bookInventory == null) {
                throw new InventoryNotFoundException("Inventory Not Found For Given ISBN");
            }
            if(bookInventory.isStatus() == false) {
                throw new InventoryNotAvailableException("Inventory not available for given ISBN");
            }
        } catch (InventoryNotFoundException e) {
            e.printStackTrace();
        } catch (InventoryNotAvailableException e) {
            e.printStackTrace();
        }
        return bookInventory;
    }

    @Override
    public BookInventory deleteInventory(String isbn) {
        BookInventory bookInventory = null;
        try {
            bookInventory = bookInventoryRepo.findByISBN(isbn);
            if(bookInventory == null) {
                throw new InventoryNotFoundException("Inventory Not Found For Given ISBN");
            }
            bookInventory.setStatus(false);
            bookInventoryRepo.save(bookInventory);
        } catch (InventoryNotFoundException e) {
            e.printStackTrace();
        }
        return bookInventory;
    }

    @Override
    public BookInventory decrementInventory(String isbn) throws InventoryNotFoundException, InventoryNotAvailableException {
        BookInventory bookInventory = null;
        try {
            bookInventory = bookInventoryRepo.findByISBN(isbn);
            if(bookInventory == null) {
                throw new InventoryNotFoundException("Inventory Not Found For Given ISBN");
            }
            if(bookInventory.isStatus() == false) {
                throw new InventoryNotAvailableException("Inventory not available for given ISBN");
            }
            bookInventory.setCount(bookInventory.getCount() - 1);
            bookInventoryRepo.save(bookInventory);
        } catch (InventoryNotFoundException e) {
            e.printStackTrace();
        } catch (InventoryNotAvailableException e) {
            e.printStackTrace();
        }
        return bookInventory;
    }
}
