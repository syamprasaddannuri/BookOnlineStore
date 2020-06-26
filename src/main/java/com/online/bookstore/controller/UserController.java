package com.online.bookstore.controller;

import com.online.bookstore.constants.URIEndpoints;
import com.online.bookstore.dto.request.UserRequestDto;
import com.online.bookstore.exception.UserNotFoundException;
import com.online.bookstore.services.UserServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(URIEndpoints.USER_INFORMATION_API)
public class UserController {
    private UserServiceInterface userServiceInterface;

    @Autowired
    public UserController(UserServiceInterface userServiceInterface) {
        this.userServiceInterface = userServiceInterface;
    }

    @PostMapping
    public ResponseEntity addUser(@RequestBody UserRequestDto userRequestDto) {
        return ResponseEntity.ok(userServiceInterface.addUser(userRequestDto));
    }

    @DeleteMapping(URIEndpoints.DELETE_USER)
    public ResponseEntity deleteUser(@RequestParam ("postId") String id) throws UserNotFoundException {
        return ResponseEntity.ok(userServiceInterface.deleteUser(id));
    }
}
