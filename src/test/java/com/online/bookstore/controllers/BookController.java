package com.online.bookstore.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.online.bookstore.dto.PaginatedBooks;
import com.online.bookstore.dto.request.BookUpdateRequest;
import com.online.bookstore.enums.BookStatus;
import com.online.bookstore.exception.BookNotFoundException;
import com.online.bookstore.model.Book;
import com.online.bookstore.services.BookServiceInterface;
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

import java.util.Arrays;

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


    @Test
    public void addBook() throws Exception {
        book = new Book("123","Maths","1","book to learn maths",10.5,BookStatus.Available);
        when(bookServiceInterface.addBook(any(Book.class))).thenReturn(book);
        mvc.perform(post("/api/book")
                .content(objectMapper.writeValueAsString(book))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void addBookShouldGiveBadRequest() throws Exception {
        book = new Book();
        mvc.perform(post("/api/book")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        book = new Book();
        book.setISBN("ISBN1");
        book.setTitle("Title1");
        book.setAuthor("Author1");
        mvc.perform(post("/api/book")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        book = new Book();
        book.setPrice(100.0);
        book.setTitle("Title1");
        book.setAuthor("Author1");
        mvc.perform(post("/api/book")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        book = new Book();
        book.setISBN("ISBN2");
        book.setPrice(100.0);
        book.setAuthor("Author1");
        mvc.perform(post("/api/book")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        book = new Book();
        book.setISBN("ISBN2");
        book.setPrice(100.0);
        book.setTitle("Title1");
        mvc.perform(post("/api/book")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void deleteBook() throws Exception {
        doNothing().when(bookServiceInterface).deleteBook(anyString());
        mvc.perform(delete("/api/book")
                .param("ISBN","123"))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteShouldReturnBadRequest() throws Exception {
        doThrow(new BookNotFoundException("Book not found")).when(bookServiceInterface).deleteBook(anyString());
        mvc.perform(delete("/api/book")
                .param("ISBN","123"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void searchBook() throws Exception {
        PaginatedBooks paginatedBooks = new PaginatedBooks();
        paginatedBooks.setBookList(Arrays.asList(book));
        when(bookServiceInterface.searchBooks("Mat",0,10)).thenReturn(paginatedBooks);
        mvc.perform(get("/api/book")
                .param("searchKey","Mat")
                .param("pageNo","0")
                .param("pageSize","10"))
                .andExpect(status().isOk());
    }

    @Test
    public void searchBookShouldReturnBadRequest() throws Exception {
           mvc.perform(get("/api/book")
                .param("pageNo","0")
                .param("pageSize","10"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void getBookShouldWork() throws Exception {
        when(bookServiceInterface.getBook("123")).thenReturn(book);
        mvc.perform(get("/api/book/getBook")
                .param("ISBN","123"))
                .andExpect(status().isOk());
    }

    @Test
    public void updateStatusOfBook() throws Exception {
        BookUpdateRequest bookUpdateRequest = new BookUpdateRequest("123","Maths","srinivasan","Book to learn easy math",100.5,BookStatus.Available);
        book = new Book("123","Maths","srinivasn","Book to learn easy math",100.5,BookStatus.Available);
        when(bookServiceInterface.updateBook(any(BookUpdateRequest.class))).thenReturn(book);
        mvc.perform(post("/api/book/update")
                .content(objectMapper.writeValueAsString(bookUpdateRequest))
                .header(HttpHeaders.CONTENT_TYPE,MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

}
