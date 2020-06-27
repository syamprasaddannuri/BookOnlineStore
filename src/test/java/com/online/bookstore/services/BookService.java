package com.online.bookstore.services;

import com.online.bookstore.convertor.BookConvertor;
import com.online.bookstore.dto.request.BookRequestDto;
import com.online.bookstore.dto.response.BookResponseDto;
import com.online.bookstore.enums.BookStatus;
import com.online.bookstore.exception.BookNotFoundException;
import com.online.bookstore.model.Book;
import com.online.bookstore.model.Pagination;
import com.online.bookstore.model.User;
import com.online.bookstore.repositories.interfaces.BookRepoInterface;
import com.online.bookstore.repositories.interfaces.UserRepoInterface;
import com.online.bookstore.services.serviceImpl.BookServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class BookService {

    private BookServiceInterface bookServiceInterface;

    @MockBean
    private BookConvertor bookConvertor;

    @Mock
    private BookRepoInterface bookRepoInterface;

    @Mock
    private UserRepoInterface userRepoInterface;

    private BookRequestDto bookRequestDto;
    private Book book;
    private BookResponseDto bookResponseDto;
    private User user;
    List<BookResponseDto> bookResponseDtoList = new ArrayList<>();
    List<Book> bookList = new ArrayList<>();
    List<User> userList = new ArrayList<>();

    @Before
    public void start() {
        bookServiceInterface = new BookServiceImpl(bookRepoInterface, bookConvertor, userRepoInterface);
        bookRequestDto = new BookRequestDto("123","Algorithms","CLRS","It's Algorithms Book",10.5, BookStatus.Available);
        book = new Book("1","CLRS","1","It's Algorithms Book",10.5,BookStatus.Available);
        bookResponseDto = new BookResponseDto("1","CLRS","1","It's Algorithms Book",10.5,BookStatus.Available);
        user = new User("1","shyam",25,"9502436232","shyam@gamil.com");
        bookResponseDtoList.add(bookResponseDto);
        bookList.add(book);
        userList.add(user);
    }

    @Test
    public void addBook() {
        when(bookRepoInterface.save(any())).thenReturn(book);
        when(bookConvertor.convertToBook(any())).thenReturn(book);
        when(bookConvertor.convertToBookResponseDto(book)).thenReturn(bookResponseDto);
        bookServiceInterface.addBook(bookRequestDto);
    }

    @Test
    public void deleteBook() throws BookNotFoundException {
        when(bookRepoInterface.findByISBN(anyString())).thenReturn(book);
        doNothing().when(bookRepoInterface).deleteBook(book);
        bookServiceInterface.deleteBook(anyString());
    }

    @Test
    public void searchBook() throws BookNotFoundException {
        when(bookRepoInterface.searchByTitleAndISBN("CL",new Pagination(0,10))).thenReturn(bookList);
        when(userRepoInterface.searchByAuthor("CL")).thenReturn(userList);
        bookServiceInterface.searchBooks("CL",0,10);
    }

}
