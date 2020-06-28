package com.online.bookstore.services;

import com.online.bookstore.enums.BookStatus;
import com.online.bookstore.exception.BookNotAvailableException;
import com.online.bookstore.exception.BookNotFoundException;
import com.online.bookstore.exception.InsufficientInventory;
import com.online.bookstore.exception.InventoryNotFoundException;
import com.online.bookstore.model.Book;
import com.online.bookstore.model.BookInventory;
import com.online.bookstore.model.Order;
import com.online.bookstore.repositories.interfaces.BookInventoryRepoInterface;
import com.online.bookstore.repositories.interfaces.BookRepoInterface;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(SpringRunner.class)
public class BookOrderingService {

    @Autowired
    private BookOrderingServiceInterface bookOrderingServiceInterface;

    @MockBean
    private BookInventoryServiceInterface bookInventoryServiceInterface;

    @MockBean
    private BookInventoryRepoInterface bookInventoryRepoInterface;

    @MockBean
    private BookServiceInterface bookServiceInterface;

    @MockBean
    private BookRepoInterface bookRepoInterface;

    private Book book;
    private BookInventory bookInventory;

    @Before
    public void start() {
        book = new Book("123","Maths","1","Book To Learn Maths",10.5,BookStatus.Available);
        bookInventory = new BookInventory("123",4, 10);
    }

    @Test
    public void buyBook() throws InventoryNotFoundException, BookNotAvailableException, BookNotFoundException, InsufficientInventory {
        when(bookServiceInterface.getBook(anyString())).thenReturn(book);
        when(bookInventoryServiceInterface.getInventory(anyString())).thenReturn(bookInventory);
        when(bookInventoryRepoInterface.findByISBN(anyString())).thenReturn(bookInventory);
        bookOrderingServiceInterface.buyBook("123", 2);
    }

    @Test(expected = InsufficientInventory.class)
    public void buyBookShouldFailWithInsufficientInventory() throws InventoryNotFoundException, BookNotAvailableException, BookNotFoundException, InsufficientInventory {
        bookInventory.setCount(1);
        when(bookServiceInterface.getBook(anyString())).thenReturn(book);
        when(bookInventoryServiceInterface.getInventory(anyString())).thenReturn(bookInventory);
        when(bookInventoryRepoInterface.findByISBN(anyString())).thenReturn(bookInventory);
        bookOrderingServiceInterface.buyBook("123", 2);
    }


    @Test(expected = InventoryNotFoundException.class)
    public void buyBookShouldReturnInventoryNotFound() throws InventoryNotFoundException, BookNotAvailableException, BookNotFoundException, InsufficientInventory {
        when(bookServiceInterface.getBook(anyString())).thenReturn(book);
        when(bookInventoryServiceInterface.getInventory(anyString())).thenThrow(new InventoryNotFoundException("Inventory not found"));
        bookOrderingServiceInterface.buyBook("123", 2);
    }

    @Test(expected = BookNotFoundException.class)
    public void buyBookShouldReturnBookNotAvailable() throws BookNotFoundException, InventoryNotFoundException, InsufficientInventory, BookNotAvailableException {
        when(bookServiceInterface.getBook(anyString())).thenThrow(new BookNotFoundException("Book Not Found"));
        when(bookRepoInterface.findByISBN(anyString())).thenReturn(book);
        bookOrderingServiceInterface.buyBook("123", 2);
    }

}
