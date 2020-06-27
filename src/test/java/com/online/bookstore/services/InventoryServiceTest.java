package com.online.bookstore.services;

import com.online.bookstore.enums.BookStatus;
import com.online.bookstore.exception.InventoryNotFoundException;
import com.online.bookstore.model.BookInventory;
import com.online.bookstore.repositories.interfaces.BookInventoryRepoInterface;
import com.online.bookstore.services.serviceImpl.BookInventoryServiceInterfaceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(SpringRunner.class)
public class InventoryServiceTest {

    private BookInventoryServiceInterface bookInventoryServiceInterface;

    @Mock
    private BookInventoryRepoInterface bookInventoryRepoInterface;

    private BookInventory bookInventory;

    @Before
    public void start() {
        bookInventoryServiceInterface = new BookInventoryServiceInterfaceImpl(bookInventoryRepoInterface);
        bookInventory = new BookInventory("123",1, BookStatus.Available);
    }

    @Test
    public void addInventory() {
        when(bookInventoryRepoInterface.save(any())).thenReturn(bookInventory);
        when(bookInventoryRepoInterface.findByISBN(any())).thenReturn(bookInventory);
        bookInventoryServiceInterface.addInventory("123");
    }

    @Test
    public void getInventory() throws InventoryNotFoundException {
        bookInventoryServiceInterface = new BookInventoryServiceInterfaceImpl(bookInventoryRepoInterface);
        when(bookInventoryRepoInterface.findByISBN(any())).thenReturn(bookInventory);
        bookInventoryServiceInterface.getInventory("123");
    }

    @Test(expected = InventoryNotFoundException.class)
    public void addInventoryIfNull() throws InventoryNotFoundException {
        when(bookInventoryRepoInterface.save(any())).thenReturn(bookInventory);
        given(bookInventoryRepoInterface.findByISBN(any())).willAnswer(invocationOnMock -> { return null; });
        bookInventoryServiceInterface.getInventory("123");
    }

    @Test
    public void decrementInventory() throws InventoryNotFoundException {
        when(bookInventoryRepoInterface.findByISBN(any())).thenReturn(bookInventory);
        bookInventoryServiceInterface.decrementInventory("123");
    }
}
