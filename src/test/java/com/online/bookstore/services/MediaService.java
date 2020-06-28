package com.online.bookstore.services;

import com.online.bookstore.dto.MediaPost;
import com.online.bookstore.enums.BookStatus;
import com.online.bookstore.exception.BookNotFoundException;
import com.online.bookstore.model.Book;
import com.online.bookstore.repositories.interfaces.BookRepoInterface;
import com.online.bookstore.services.serviceImpl.MediaPostsCacheService;
import com.online.bookstore.services.serviceImpl.MediaServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashSet;
import java.util.Set;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(SpringRunner.class)
public class MediaService {

    private MediaServiceInterface mediaServiceInterface;

    @MockBean
    private BookRepoInterface bookRepoInterface;

    @MockBean
    private MediaPostsCacheService mediaPostsCacheService;

    private Book book;
    private MediaPost mediaPost;
    Set<MediaPost> mediaPosts = new HashSet<>();

    @Before
    public void start() {
        mediaServiceInterface = new MediaServiceImpl(bookRepoInterface, mediaPostsCacheService);
        book = new Book("123","Maths","1","It's a Maths Book",10.5, BookStatus.Available);
        mediaPost = new MediaPost(1,100,"Post On Maths","Book to learn maths in easy way");
        mediaPosts.add(mediaPost);
    }

    @Test
    public void getPosts() throws BookNotFoundException {
        when(bookRepoInterface.findByISBN(anyString())).thenReturn(book);
        when(mediaPostsCacheService.getMediaPosts(anyString())).thenReturn(mediaPosts);
        mediaServiceInterface.getPostsByISBN(anyString());
    }

}
