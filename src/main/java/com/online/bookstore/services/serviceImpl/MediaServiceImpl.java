package com.online.bookstore.services.serviceImpl;

import com.online.bookstore.dto.MediaPost;
import com.online.bookstore.exception.BookNotFoundException;
import com.online.bookstore.model.Book;
import com.online.bookstore.repositories.interfaces.BookRepoInterface;
import com.online.bookstore.services.MediaPostsCacheService;
import com.online.bookstore.services.MediaServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class MediaServiceImpl implements MediaServiceInterface {

    private BookRepoInterface bookRepoInterface;
    private MediaPostsCacheService mediaPostsCacheService;

    @Autowired
    public MediaServiceImpl(BookRepoInterface bookRepoInterface, MediaPostsCacheService mediaPostsCacheService) {
        this.bookRepoInterface = bookRepoInterface;
        this.mediaPostsCacheService = mediaPostsCacheService;
    }

    @Override
    public List<MediaPost> getPostsByISBN(String ISBN)throws BookNotFoundException {
        List<MediaPost> result = new ArrayList<>();
        Book book = bookRepoInterface.findByISBN(ISBN);
        if(book == null) {
            throw new BookNotFoundException("Book not found for given ISBN");
        }
        String title = book.getTitle();
        Set<MediaPost> mediaCoverage= mediaPostsCacheService.getMediaPosts(title);
        return new ArrayList<>(mediaCoverage);
    }
}
