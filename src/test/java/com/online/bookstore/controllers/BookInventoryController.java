package com.online.bookstore.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.online.bookstore.dto.request.BookInventoryRequest;
import com.online.bookstore.enums.BookInventoryRequestStatus;
import com.online.bookstore.exception.InvalidRequestException;
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
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
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

    private static final ObjectMapper objectMapper = new ObjectMapper();

    private BookInventory bookInventory;
    private BookInventoryRequest bookInventoryRequest;

    @Before
    public void start() {
        bookInventory = new BookInventory();
        bookInventory.setISBN("isbn1");
        bookInventory.setCount(10);
    }

    @Test
    public void IncrementInventory() throws Exception, InvalidRequestException {
        bookInventoryRequest = new BookInventoryRequest("123", 10, BookInventoryRequestStatus.Increment);
        when(bookInventoryServiceInterface.updateInventory(any(BookInventoryRequest.class))).thenReturn(bookInventory);
        mockMvc.perform(post("/api/inventory")
                .content(objectMapper.writeValueAsString(bookInventoryRequest))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void decrementInventory() throws Exception, InvalidRequestException {
        bookInventoryRequest = new BookInventoryRequest("123", 1, BookInventoryRequestStatus.Decrement);
        when(bookInventoryServiceInterface.updateInventory(any(BookInventoryRequest.class))).thenReturn(bookInventory);
        mockMvc.perform(post("/api/inventory")
                .content(objectMapper.writeValueAsString(bookInventoryRequest))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void getInventory() throws Exception {
        when(bookInventoryServiceInterface.getInventory(anyString())).thenReturn(bookInventory);
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


}
