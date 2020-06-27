package com.online.bookstore.controllers;

import com.online.bookstore.dto.response.BookInventoryResponse;
import com.online.bookstore.enums.BookStatus;
import com.online.bookstore.exception.InventoryNotFoundException;
import com.online.bookstore.model.BookInventory;
import com.online.bookstore.services.BookInventoryServiceInterface;
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
public class BookInventoryController {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookInventoryServiceInterface bookInventoryServiceInterface;

    private BookInventory bookInventory;
    private BookInventoryResponse bookInventoryResponse;

    @Before
    public void start() {
        bookInventory = new BookInventory("123",10, BookStatus.Available);
        bookInventoryResponse = new BookInventoryResponse("123",10);
    }

    @Test
    public void addInventory() throws Exception {
        when(bookInventoryServiceInterface.addInventory(anyString())).thenReturn(bookInventory);
        mockMvc.perform(post("/api/inventory/addInventory")
                .param("ISBN","123"))
                .andExpect(status().isOk());
    }

    @Test
    public void decrementInventory() throws Exception {
        when(bookInventoryServiceInterface.decrementInventory(anyString())).thenReturn(bookInventory);
        mockMvc.perform(post("/api/inventory/decrementInventory")
                .param("ISBN","123"))
                .andExpect(status().isOk());
    }

    @Test
    public void decrementInventoryShouldReturn() throws Exception {
        when(bookInventoryServiceInterface.decrementInventory(anyString())).thenThrow(new InventoryNotFoundException("Inventory not found for given isbn"));
        mockMvc.perform(post("/api/inventory/decrementInventory")
                .param("ISBN","123"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void getInventory() throws Exception {
        when(bookInventoryServiceInterface.getInventory(anyString())).thenReturn(bookInventoryResponse);
        mockMvc.perform(get("/api/inventory/getInventory")
        .param("ISBN","123"))
                .andExpect(status().isOk());
    }

    @Test
    public void getInventoryShouldReturnBadRequest() throws Exception {
        when(bookInventoryServiceInterface.getInventory(anyString())).thenThrow(new InventoryNotFoundException("Inventory not found for given isbn"));
        mockMvc.perform(get("/api/inventory/getInventory")
                .param("ISBN","123"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void deleteInventory() throws Exception {
        when(bookInventoryServiceInterface.deleteInventory(anyString())).thenReturn(bookInventory);
        mockMvc.perform(post("/api/inventory/deleteInventory")
                .param("ISBN","123"))
                .andExpect(status().isOk());
    }

}
