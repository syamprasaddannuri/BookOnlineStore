package com.online.bookstore.services.serviceImpl;

import com.online.bookstore.convertor.UserConvertor;
import com.online.bookstore.dto.request.UserRequestDto;
import com.online.bookstore.dto.response.UserResponseDto;
import com.online.bookstore.exception.UserNotFoundException;
import com.online.bookstore.model.User;
import com.online.bookstore.repositories.UserRepo;
import com.online.bookstore.services.UserServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserServiceInterface {

    private UserConvertor userConvertor;
    private UserRepo userRepo;

    @Autowired
    public UserServiceImpl(UserConvertor userConvertor, UserRepo userRepo) {
        this.userConvertor = userConvertor;
        this.userRepo = userRepo;
    }

    @Override
    public UserResponseDto addUser(UserRequestDto userRequestDto) {
        User user = userConvertor.convertToUser(userRequestDto);
        userRepo.save(user);
        return userConvertor.convertToUserDto(user);
    }

    @Override
    public UserResponseDto deleteUser(String id) {
        User user = null;
        try {
            user = userRepo.getUserById(id);
            if(user == null) {
                throw new UserNotFoundException("user not found for given id");
            }
            userRepo.deleteUser(user);
        } catch (UserNotFoundException e) {
            e.printStackTrace();
        }
        return userConvertor.convertToUserDto(user);
    }
}
