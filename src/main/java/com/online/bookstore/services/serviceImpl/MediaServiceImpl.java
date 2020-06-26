package com.online.bookstore.services.serviceImpl;

import com.online.bookstore.client.MediaClient;
import com.online.bookstore.dto.MediaData;
import com.online.bookstore.exception.BookNotFoundException;
import com.online.bookstore.model.Book;
import com.online.bookstore.repositories.interfaces.BookRepoInterface;
import com.online.bookstore.services.MediaServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class MediaServiceImpl implements MediaServiceInterface {

    private BookRepoInterface bookRepoInterface;
    private MediaClient mediaClient;

    @Autowired
    public MediaServiceImpl(BookRepoInterface bookRepoInterface,MediaClient mediaClient) {
        this.bookRepoInterface = bookRepoInterface;
        this.mediaClient = mediaClient;
    }

    @Override
    public List<MediaData> getPostsByISBN(String ISBN)throws BookNotFoundException {
        List<MediaData> result = new ArrayList<>();
        Book book = bookRepoInterface.findByISBN(ISBN);
        if(book == null) {
            throw new BookNotFoundException("Book not found for given ISBN");
        }
        String title = book.getTitle();
        Map<String, Set<MediaData>> map= mediaClient.getPostsInMedia();
        result.addAll(new ArrayList<>(map.get(title)));
        return result;
    }
}
