package com.online.bookstore.services;

import com.online.bookstore.enums.BookStatus;
import com.online.bookstore.exception.BookNotFoundException;
import com.online.bookstore.model.Book;
import com.online.bookstore.model.Pagination;
import com.online.bookstore.repositories.interfaces.BookRepoInterface;
import com.online.bookstore.services.serviceImpl.BookServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class BookService {

    private BookServiceInterface bookServiceInterface;

    @MockBean
    private BookRepoInterface bookRepoInterface;

    private Book book;
    List<Book> bookList = new ArrayList<>();

    @Before
    public void start() {
        bookServiceInterface = new BookServiceImpl(bookRepoInterface);
        book = new Book("1","CLRS","1","It's Algorithms Book",10.5,BookStatus.Available);
        bookList.add(book);
    }

    @Test
    public void addBook() {
        when(bookRepoInterface.save(book)).thenReturn(book);
        bookServiceInterface.addBook(book);
    }

    @Test
    public void deleteBook() throws BookNotFoundException {
        when(bookRepoInterface.findByISBN(anyString())).thenReturn(book);
        doNothing().when(bookRepoInterface).deleteBook(book);
        bookServiceInterface.deleteBook("123");
    }

    @Test(expected = BookNotFoundException.class)
    public void deleteBookShouldReturnBookNotFound() throws BookNotFoundException {
        when(bookRepoInterface.findByISBN(anyString())).thenReturn(book);
        doNothing().when(bookRepoInterface).deleteBook(book);
        doThrow(new BookNotFoundException("")).when(bookServiceInterface).deleteBook("123");
    }

    @Test
    public void searchBook() throws BookNotFoundException {
        when(bookRepoInterface.searchInISBNAuthorTitle("CL",new Pagination(0,10))).thenReturn(bookList);
        bookServiceInterface.searchBooks("CL",0,10);
    }

}
