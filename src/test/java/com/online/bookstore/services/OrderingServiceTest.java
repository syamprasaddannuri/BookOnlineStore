package com.online.bookstore.services;

import com.online.bookstore.dto.request.OrderRequest;
import com.online.bookstore.enums.BookStatus;
import com.online.bookstore.enums.OrderStatus;
import com.online.bookstore.exception.BookNotAvailableException;
import com.online.bookstore.exception.BookNotFoundException;
import com.online.bookstore.exception.InventoryNotAvailableException;
import com.online.bookstore.exception.InventoryNotFoundException;
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
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(SpringRunner.class)
public class OrderingServiceTest {

    private BookOrderingServiceInterface bookOrderingServiceInterface;

    @Mock
    private OrderRepoInterface orderRepoInterface;

    @Mock
    private BookInventoryRepoInterface bookInventoryRepoInterface;

    @Mock
    private BookInventoryServiceInterface bookInventoryServiceInterface;

    @Mock
    private BookRepoInterface bookRepoInterface;

    private Order order;
    private OrderRequest orderRequest;
    private BookInventory bookInventory;

    @Before
    public void start() {
        bookOrderingServiceInterface = new BookOrderingServiceImpl(bookInventoryServiceInterface,bookRepoInterface,orderRepoInterface);
        order = new Order("1","123",10.5, OrderStatus.Created);
        orderRequest = new OrderRequest("1","123",10.5, OrderStatus.Created);
        bookInventory = new BookInventory("11","123",1, BookStatus.Available);
    }

    @Test
    public void buyBook() throws InventoryNotFoundException, BookNotAvailableException, InventoryNotAvailableException, BookNotFoundException {
        when(bookInventoryRepoInterface.save(any())).thenReturn(bookInventory);
        when(bookInventoryRepoInterface.findByISBN(any())).thenReturn(bookInventory);
        bookOrderingServiceInterface.buyBook("123");
    }

    @Test
    public void addResponse() {
        when(orderRepoInterface.save(any())).thenReturn(order);
        bookOrderingServiceInterface.storeOrderResponse(orderRequest);
    }
}
