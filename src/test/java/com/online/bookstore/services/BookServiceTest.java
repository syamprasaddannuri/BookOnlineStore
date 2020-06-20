package com.online.bookstore.services;

import com.online.bookstore.dto.BookResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ContextConfiguration
@SpringBootTest
@RunWith(MockitoJUnitRunner.Silent.class)
public class BookServiceTest {
    @Mock
    private BookServiceInterface bookServiceInterface;

    @Test
    public void addBookTest() {
        when(bookServiceInterface.addBook(any())).thenReturn(BookResponse.getBookResponseData());
    }

}
