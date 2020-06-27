package com.online.bookstore.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.online.bookstore.dto.PaginatedBooks;
import com.online.bookstore.dto.request.BookRequestDto;
import com.online.bookstore.dto.request.BookStatusRequestDto;
import com.online.bookstore.dto.response.BookResponseDto;
import com.online.bookstore.enums.BookStatus;
import com.online.bookstore.exception.BookNotFoundException;
import com.online.bookstore.model.Book;
import com.online.bookstore.model.Pagination;
import com.online.bookstore.services.BookServiceInterface;
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
import org.springframework.web.client.HttpClientErrorException;

import java.util.ArrayList;
import java.util.List;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;



@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class BookController {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private BookServiceInterface bookServiceInterface;


    private static final ObjectMapper objectMapper = new ObjectMapper();


    private Book book;
    private BookRequestDto bookRequestDto;
    private BookResponseDto bookResponseDto;
    private PaginatedBooks paginatedBooks;
    private BookStatusRequestDto bookStatusRequestDto;
    List<BookResponseDto> bookResponseDtoList = new ArrayList<>();

    @Before
    public void start() {
        book = new Book("123","Maths","1","book to learn maths",10.5,BookStatus.Available);
        bookRequestDto = new BookRequestDto("123","Maths","shyam","book to learn maths",10.5,BookStatus.Available);
        bookResponseDto = new BookResponseDto("123","Maths","shyam","book to learn maths",10.5,BookStatus.Available);
        bookStatusRequestDto = new BookStatusRequestDto("123",BookStatus.Available);
        bookResponseDtoList.add(bookResponseDto);
        Pagination pagination = new Pagination(0,10);
        paginatedBooks = new PaginatedBooks(bookResponseDtoList, (long) 1,pagination);
    }


    @Test
    public void addBook() throws Exception {
        when(bookServiceInterface.addBook(any(BookRequestDto.class))).thenReturn(bookResponseDto);
        mvc.perform(post("/api/book")
                .content(objectMapper.writeValueAsString(bookRequestDto))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void addBookShouldGiveBadRequest() throws Exception {
        bookRequestDto = new BookRequestDto(null,"Maths","shyam","it's maths book",null, BookStatus.Available);
        when(bookServiceInterface.addBook(any(BookRequestDto.class))).thenThrow(HttpClientErrorException.BadRequest.class);
        mvc.perform(post("/api/book")
                .content(objectMapper.writeValueAsString(bookRequestDto))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void deleteBook() throws Exception {
        doNothing().when(bookServiceInterface).deleteBook(anyString());
        mvc.perform(delete("/api/book/remove")
                .param("ISBN","123"))
                .andExpect(status().isOk());
    }

    @Test
    public void searchBook() throws Exception {
        when(bookServiceInterface.searchBooks("Mat",0,10)).thenReturn(paginatedBooks);
        mvc.perform(get("/api/book/")
                .param("searchKey","Mat")
                .param("pageNo","0")
                .param("pageSize","10"))
                .andExpect(status().isOk());
    }

    @Test
    public void searchBookShouldReturnBadRequest() throws Exception {
        when(bookServiceInterface.searchBooks("Mat",0,10)).thenThrow(new BookNotFoundException("Book Not Found For Given ISBN"));
        mvc.perform(get("/api/book/")
                .param("searchKey","Mat")
                .param("pageNo","0")
                .param("pageSize","10"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void getStatusOfBook() throws Exception {
        String status = "Available";
        when(bookServiceInterface.getStatusOfBook(anyString())).thenReturn(status);
        mvc.perform(get("/api/book/status")
                .param("ISBN","123"))
                .andExpect(status().isOk());
    }

    @Test
    public void updateStatusOfBook() throws Exception {
        when(bookServiceInterface.updateBookStatus(any(BookStatusRequestDto.class))).thenReturn(book);
        mvc.perform(post("/api/book/update/status")
                .content(objectMapper.writeValueAsString(bookStatusRequestDto))
                .header(HttpHeaders.CONTENT_TYPE,MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

}
