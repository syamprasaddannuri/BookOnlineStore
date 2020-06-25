package com.online.bookstore.services;

import com.online.bookstore.convertor.BookConvertor;
import com.online.bookstore.dto.PaginatedBooks;
import com.online.bookstore.dto.request.BookRequestDto;
import com.online.bookstore.dto.response.BookResponseDto;
import com.online.bookstore.exception.BookNotFoundException;
import com.online.bookstore.model.Book;
import com.online.bookstore.model.Pagination;
import com.online.bookstore.model.User;
import com.online.bookstore.repositories.BookRepo;
import com.online.bookstore.repositories.UserRepo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class BookServiceTest {

    @Autowired
    private BookServiceInterface bookServiceInterface;

    @MockBean
    private BookConvertor bookConvertor;

    @Mock
    private BookRepo bookRepo;

    @Mock
    private UserRepo userRepo;


    @Test
    public void addBook() {
        BookRequestDto bookRequestDto = new BookRequestDto("101","Algorithms","CLRS","It's Algorithms Book");
        Book book = new Book("1","CLRS","1","It's Algorithms Book");
        BookResponseDto bookResponseDto = new BookResponseDto("1","CLRS","1","It's Algorithms Book");
        when(bookRepo.save(any())).thenReturn(book);
        when(bookConvertor.convertToBook(any())).thenReturn(book);
        when(bookConvertor.convertToBookResponseDto(book)).thenReturn(bookResponseDto);
        bookServiceInterface.addBook(bookRequestDto);
        Assert.assertEquals(bookRequestDto.getDescription(),book.getDescription());
        Assert.assertEquals(book.getTitle(),bookResponseDto.getTitle());
    }

    @Test
    public void deleteBook() throws BookNotFoundException {
        Book book = new Book("1","CLRS","1","It's Algorithms Book");
        when(bookRepo.findByISBN(any())).thenReturn(book);
        doNothing().when(bookRepo).deleteBook(book);
        bookServiceInterface.deleteBook("1");
        Assert.assertNotNull(book);
        Assert.assertEquals(book.getTitle(),"CLRS");
        Assert.assertEquals(book.getISBN(),"1");


        System.out.println("shyam");
    }

    @Test(expected = BookNotFoundException.class)
    public void deleteBookIfNull() throws BookNotFoundException {
        given(bookRepo.findByISBN(any())).willAnswer(invocation ->{ throw new BookNotFoundException("Book not found");});
        bookServiceInterface.deleteBook("1");
    }

    @Test
    public void searchBook() throws BookNotFoundException {
        BookResponseDto bookResponseDto = new BookResponseDto("1","CLRS","1","It's Algorithms Book");
        Book book = new Book("1","CLRS","1","It's Algorithms Book");
        List<BookResponseDto> bookResponseDtoList = new ArrayList<>();
        bookResponseDtoList.add(bookResponseDto);
        User user = new User("1","CLRS",50,"9999999999","CLRS@gamil.com");
        PaginatedBooks paginatedBooks = new PaginatedBooks(bookResponseDtoList, (long) 1,new Pagination(0,10));
        List<Book> bookList = new ArrayList<>();
        List<User> userList = new ArrayList<>();
        bookList.add(book);
        userList.add(user);
        when(bookRepo.save(any())).thenReturn(book);
        when(bookRepo.searchByTitleAndISBN("CL",new Pagination(0,10))).thenReturn(bookList);
        when(userRepo.searchByAuthor("CL")).thenReturn(userList);
        bookServiceInterface.searchBooks("CL",0,10);
        Assert.assertEquals(paginatedBooks.getBookList().get(0).getTitle(),"CLRS");
        Assert.assertEquals(paginatedBooks.getCount(),"1");
    }

}
