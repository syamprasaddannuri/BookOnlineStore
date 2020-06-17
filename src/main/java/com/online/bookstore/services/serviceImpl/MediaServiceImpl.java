package com.online.bookstore.services.serviceImpl;

import com.online.bookstore.client.MediaClient;
import com.online.bookstore.dto.MediaData;
import com.online.bookstore.exception.BookNotFoundException;
import com.online.bookstore.model.Book;
import com.online.bookstore.repositories.BookRepo;
import com.online.bookstore.services.MediaServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MediaServiceImpl implements MediaServiceInterface {

    private BookRepo bookRepo;
    private MediaClient mediaClient;

    @Autowired
    public MediaServiceImpl(BookRepo bookRepo,MediaClient mediaClient) {
        this.bookRepo = bookRepo;
        this.mediaClient = mediaClient;
    }

    @Override
    public List<MediaData> getPostsByISBN(String ISBN) {
        List<MediaData> result = new ArrayList<>();
        try {
            Book book = bookRepo.findByISBN(ISBN);
            if(book == null) {
                throw new BookNotFoundException("Book not found for given ISBN");
            }
            String title = book.getTitle();
            List<MediaData> mediaDataList = mediaClient.getPostsInMedia();
            mediaDataList.forEach(
                    mediaData -> {
                        if(mediaData.getTitle().contains(title) || mediaData.getBody().contains(title)) {
                            result.add(mediaData);
                        }
                    }
            );
        } catch (BookNotFoundException e) {
            e.printStackTrace();
        }
        return result;
    }
}
