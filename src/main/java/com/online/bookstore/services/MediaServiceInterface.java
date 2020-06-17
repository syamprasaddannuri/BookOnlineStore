package com.online.bookstore.services;

import com.online.bookstore.dto.MediaData;
import com.online.bookstore.exception.BookNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MediaServiceInterface {
    List<MediaData> getPostsByISBN(String ISBN) throws BookNotFoundException;
}
