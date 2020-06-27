package com.online.bookstore.services;

import com.online.bookstore.dto.request.UserRequestDto;
import com.online.bookstore.dto.response.UserResponseDto;
import com.online.bookstore.exception.UserNotFoundException;
import org.springframework.stereotype.Service;

@Service
public interface UserServiceInterface {
    UserResponseDto addUser(UserRequestDto userRequestDto);

    void deleteUser(String id) throws UserNotFoundException;
}
