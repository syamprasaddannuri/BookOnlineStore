package com.online.bookstore.services;

import com.online.bookstore.dto.MediaPost;
import com.online.bookstore.exception.BookNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MediaServiceInterface {
    List<MediaPost> getPostsByISBN(String ISBN) throws BookNotFoundException;
}
