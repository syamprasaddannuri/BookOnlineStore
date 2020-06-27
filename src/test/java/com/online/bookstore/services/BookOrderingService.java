package com.online.bookstore.services;

import com.online.bookstore.dto.request.OrderRequest;
import com.online.bookstore.dto.response.BookInventoryResponse;
import com.online.bookstore.enums.BookStatus;
import com.online.bookstore.enums.OrderStatus;
import com.online.bookstore.exception.BookNotAvailableException;
import com.online.bookstore.exception.BookNotFoundException;
import com.online.bookstore.exception.InventoryNotFoundException;
import com.online.bookstore.model.Book;
import com.online.bookstore.model.BookInventory;
import com.online.bookstore.model.Order;
import com.online.bookstore.repositories.interfaces.BookInventoryRepoInterface;
import com.online.bookstore.repositories.interfaces.BookRepoInterface;
import com.online.bookstore.repositories.interfaces.OrderRepoInterface;
import com.online.bookstore.services.serviceImpl.BookOrderingServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(SpringRunner.class)
public class BookOrderingService {

    private BookOrderingServiceInterface bookOrderingServiceInterface;

    @MockBean
    private OrderRepoInterface orderRepoInterface;

    @MockBean
    private BookInventoryServiceInterface bookInventoryServiceInterface;

    @MockBean
    private BookInventoryRepoInterface bookInventoryRepoInterface;

    @MockBean
    private BookServiceInterface bookServiceInterface;

    @MockBean
    private BookRepoInterface bookRepoInterface;

    private Order order;
    private OrderRequest orderRequest;
    private BookInventoryResponse bookInventoryResponse;
    private Book book;
    private BookInventory bookInventory;

    @Before
    public void start() {
        bookOrderingServiceInterface = new BookOrderingServiceImpl(bookInventoryServiceInterface,bookRepoInterface,orderRepoInterface,bookServiceInterface);
        order = new Order("1","123",10.5, OrderStatus.Created);
        orderRequest = new OrderRequest("1","123",10.5, OrderStatus.Created);
        bookInventoryResponse = new BookInventoryResponse("123",1);
        book = new Book("123","Maths","1","Book To Learn Maths",10.5,BookStatus.Available);
        bookInventory = new BookInventory("123",10);
    }

    @Test
    public void buyBook() throws InventoryNotFoundException, BookNotAvailableException, BookNotFoundException {
        String status = "Available";
        when(bookServiceInterface.getStatusOfBook(anyString())).thenReturn(status);
        when(bookRepoInterface.findByISBN(anyString())).thenReturn(book);
        when(bookInventoryServiceInterface.getInventory(anyString())).thenReturn(bookInventoryResponse);
        when(bookInventoryRepoInterface.findByISBN(anyString())).thenReturn(bookInventory);
        bookOrderingServiceInterface.buyBook(anyString());
    }

    @Test(expected = InventoryNotFoundException.class)
    public void buyBookShouldReturnBadRequest() throws InventoryNotFoundException, BookNotAvailableException, BookNotFoundException {
        String status = "Available";
        when(bookServiceInterface.getStatusOfBook(anyString())).thenReturn(status);
        when(bookInventoryServiceInterface.getInventory(anyString())).thenThrow(new InventoryNotFoundException("Inventory Not Found"));
        when(bookInventoryRepoInterface.findByISBN(anyString())).thenReturn(bookInventory);
        when(bookRepoInterface.findByISBN(anyString())).thenReturn(book);
        bookOrderingServiceInterface.buyBook(anyString());
    }

    @Test(expected = BookNotAvailableException.class)
    public void buyBookShouldReturnBookNotAvailable() throws InventoryNotFoundException, BookNotAvailableException, BookNotFoundException {
        String status = "NotAvailable";
        when(bookServiceInterface.getStatusOfBook(anyString())).thenReturn(status).thenThrow(new BookNotAvailableException("Book is not available"));
        when(bookRepoInterface.findByISBN(anyString())).thenReturn(book);
        when(bookInventoryServiceInterface.getInventory(anyString())).thenReturn(bookInventoryResponse);
        when(bookInventoryRepoInterface.findByISBN(anyString())).thenReturn(bookInventory);
        bookOrderingServiceInterface.buyBook(anyString());
    }

    @Test
    public void addResponse() {
        when(orderRepoInterface.save(any())).thenReturn(order);
        bookOrderingServiceInterface.storeOrderResponse(orderRequest);
    }
}
