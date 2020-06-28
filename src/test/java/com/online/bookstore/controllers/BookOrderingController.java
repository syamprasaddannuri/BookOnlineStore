package com.online.bookstore.controllers;

import com.online.bookstore.enums.OrderStatus;
import com.online.bookstore.exception.InsufficientInventory;
import com.online.bookstore.model.Order;
import com.online.bookstore.exception.InventoryNotFoundException;
import com.online.bookstore.services.BookOrderingServiceInterface;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class BookOrderingController {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookOrderingServiceInterface bookOrderingServiceInterface;

    private com.online.bookstore.model.Order order;

    @Before
    public void start() {
        order = new Order("1","123",10.5, 10, OrderStatus.Created);

    }

    @Test
    public void buyBook() throws Exception, InsufficientInventory {
        when(bookOrderingServiceInterface.buyBook(anyString(), anyInt())).thenReturn(order);
        mockMvc.perform(get("/api/ordering")
        .param("ISBN","123")
        .param("quantity","10"))
                .andExpect(status().isOk());
    }

    @Test
    public void buyBookShouldReturnBadRequest() throws Exception, InsufficientInventory {
        when(bookOrderingServiceInterface.buyBook(anyString(), anyInt())).thenThrow(new InventoryNotFoundException("Inventory Not Found For Given ISBN"));
        mockMvc.perform(get("/api/ordering")
                .param("ISBN","123")
                .param("quantity","10"))
                .andExpect(status().is4xxClientError());
    }
}
