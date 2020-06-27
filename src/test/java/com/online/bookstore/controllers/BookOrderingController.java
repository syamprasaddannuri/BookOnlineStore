package com.online.bookstore.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.online.bookstore.dto.request.OrderRequest;
import com.online.bookstore.dto.response.OrderResponse;
import com.online.bookstore.enums.OrderStatus;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import com.online.bookstore.exception.InventoryNotFoundException;
import com.online.bookstore.model.Order;
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

import static org.mockito.ArgumentMatchers.any;
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

    private static final ObjectMapper objectMapper = new ObjectMapper();

    private Order order;
    private OrderResponse orderResponse;
    private OrderRequest orderRequest;

    @Before
    public void start() {
        order = new Order("1","123",10.5, OrderStatus.Created);
        orderResponse = new OrderResponse("1","123",10.5,OrderStatus.Completed);
        orderRequest = new OrderRequest("1","123",10.5,OrderStatus.Created);
    }

    @Test
    public void buyBook() throws Exception {
        when(bookOrderingServiceInterface.buyBook(anyString())).thenReturn(orderResponse);
        mockMvc.perform(get("/api/ordering")
        .param("ISBN","123"))
                .andExpect(status().isOk());
    }

    @Test
    public void buyBookShouldReturnBadRequest() throws Exception {
        when(bookOrderingServiceInterface.buyBook(anyString())).thenThrow(new InventoryNotFoundException("Inventory Not Found For Given ISBN"));
        mockMvc.perform(get("/api/ordering")
                .param("ISBN","123"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void storeOrderResponse() throws Exception {
        when(bookOrderingServiceInterface.storeOrderResponse(any(OrderRequest.class))).thenReturn(order);
        mockMvc.perform(post("/api/ordering")
                .content(objectMapper.writeValueAsString(orderRequest))
                .header(HttpHeaders.CONTENT_TYPE,MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
