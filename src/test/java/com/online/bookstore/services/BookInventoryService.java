package com.online.bookstore.services;

import com.online.bookstore.dto.request.BookInventoryRequest;
import com.online.bookstore.enums.BookInventoryRequestStatus;
import com.online.bookstore.exception.InvalidRequestException;
import com.online.bookstore.exception.InventoryNotFoundException;
import com.online.bookstore.model.BookInventory;
import com.online.bookstore.repositories.interfaces.BookInventoryRepoInterface;
import com.online.bookstore.services.serviceImpl.BookInventoryServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(SpringRunner.class)
public class BookInventoryService {

    private BookInventoryServiceInterface bookInventoryServiceInterface;

    @Mock
    private BookInventoryRepoInterface bookInventoryRepoInterface;

    private BookInventory bookInventory;
    private BookInventoryRequest bookInventoryRequest;

    @Before
    public void start() {
        bookInventoryServiceInterface = new BookInventoryServiceImpl(bookInventoryRepoInterface);
        bookInventory = new BookInventory();
        bookInventory.setISBN("123");
        bookInventory.setCount(10);
    }

    @Test
    public void getInventory() throws InventoryNotFoundException {
        when(bookInventoryRepoInterface.findByISBN(any())).thenReturn(bookInventory);
        bookInventoryServiceInterface.getInventory("123");
    }

    @Test
    public void incrementBookInventory() throws InventoryNotFoundException, InvalidRequestException {
        bookInventoryRequest = new BookInventoryRequest("123", 10, BookInventoryRequestStatus.Increment);
        when(bookInventoryRepoInterface.findByISBN(anyString())).thenReturn(bookInventory);
        bookInventoryServiceInterface.updateInventory(bookInventoryRequest);
    }

    @Test
    public void decrementBookInventory() throws InventoryNotFoundException, InvalidRequestException {
        bookInventoryRequest = new BookInventoryRequest("123",10, BookInventoryRequestStatus.Decrement);
        when(bookInventoryRepoInterface.findByISBN(anyString())).thenReturn(bookInventory);
        bookInventoryServiceInterface.updateInventory(bookInventoryRequest);
    }

    @Test(expected = InventoryNotFoundException.class)
    public void addInventoryIfNull() throws InventoryNotFoundException {
        when(bookInventoryRepoInterface.save(any())).thenReturn(bookInventory);
        given(bookInventoryRepoInterface.findByISBN(any())).willAnswer(invocationOnMock -> { return null; });
        bookInventoryServiceInterface.getInventory("123");
    }

}
