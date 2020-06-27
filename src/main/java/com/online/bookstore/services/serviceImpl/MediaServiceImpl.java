package com.online.bookstore.services.serviceImpl;

import com.online.bookstore.dto.MediaPost;
import com.online.bookstore.exception.BookNotFoundException;
import com.online.bookstore.model.Book;
import com.online.bookstore.repositories.interfaces.BookRepoInterface;
import com.online.bookstore.services.MediaServiceInterface;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class MediaServiceImpl implements MediaServiceInterface {

    private BookRepoInterface bookRepoInterface;
    private MediaPostsCacheService mediaPostsCacheService;

    static final Logger logger = LogManager.getLogger(MediaServiceImpl.class.getName());

    @Autowired
    public MediaServiceImpl(BookRepoInterface bookRepoInterface, MediaPostsCacheService mediaPostsCacheService) {
        this.bookRepoInterface = bookRepoInterface;
        this.mediaPostsCacheService = mediaPostsCacheService;
    }

    @Override
    public List<MediaPost> getPostsByISBN(String ISBN)throws BookNotFoundException {
        Book book = bookRepoInterface.findByISBN(ISBN);
        if(book == null) {
            logger.error("Book not found for given ISBN");
            throw new BookNotFoundException("Book not found for given ISBN");
        }
        String title = book.getTitle();
        Set<MediaPost> mediaCoverage= mediaPostsCacheService.getMediaPosts(title);
        return new ArrayList<>(mediaCoverage);
    }
}
