package com.online.bookstore.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.online.bookstore.dto.request.UserRequestDto;
import com.online.bookstore.dto.response.UserResponseDto;
import com.online.bookstore.exception.UserNotFoundException;
import com.online.bookstore.model.User;
import com.online.bookstore.services.UserServiceInterface;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class UserController {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserServiceInterface userServiceInterface;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    private User user;
    private UserRequestDto userRequestDto;
    private UserResponseDto userResponseDto;

    @Before
    public void start() {
        user = new User("1","shyam",25,"9502436232","syam.dhannuri@gmail.com");
        userRequestDto = new UserRequestDto("1","shyam",25,"9502436232","syam.dhannuri@gmail.com");
        userResponseDto = new UserResponseDto("1","shyam",25,"9502436232","syam.dhannuri@gmail.com");
    }

    @Test
    public void addUser() throws Exception {
        when(userServiceInterface.addUser(userRequestDto)).thenReturn(userResponseDto);
        mockMvc.perform(post("/api/user")
        .content(objectMapper.writeValueAsString(userRequestDto))
        .header(HttpHeaders.CONTENT_TYPE,MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteUser() throws Exception {
        doNothing().when(userServiceInterface).deleteUser(anyString());
        mockMvc.perform(delete("/api/user")
        .param("Id","1"))
                .andExpect(status().isOk());
    }

}
