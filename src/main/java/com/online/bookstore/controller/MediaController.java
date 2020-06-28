package com.online.bookstore.controller;

import com.online.bookstore.constants.UriEndpoints;
import com.online.bookstore.exception.BookNotFoundException;
import com.online.bookstore.services.MediaServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(UriEndpoints.MEDIA_INFORMATION)
public class MediaController {
    private MediaServiceInterface mediaServiceInterface;

    @Autowired
    public MediaController(MediaServiceInterface mediaServiceInterface) {
        this.mediaServiceInterface = mediaServiceInterface;
    }

    @GetMapping
    public ResponseEntity getPostsByISBN(@RequestParam ("ISBN") String ISBN) throws BookNotFoundException {
        return ResponseEntity.ok(mediaServiceInterface.getPostsByISBN(ISBN));
    }
}
