package com.online.bookstore.controller;

import com.online.bookstore.constants.UriEndpoints;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(UriEndpoints.PING)
public class PingController {

    @GetMapping
    public ResponseEntity ping() {
        return ResponseEntity.ok().build();
    }
}
