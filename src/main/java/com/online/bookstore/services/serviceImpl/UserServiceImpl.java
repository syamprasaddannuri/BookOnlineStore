package com.online.bookstore.services.serviceImpl;

import com.online.bookstore.convertor.UserConvertor;
import com.online.bookstore.dto.request.UserRequestDto;
import com.online.bookstore.dto.response.UserResponseDto;
import com.online.bookstore.exception.UserNotFoundException;
import com.online.bookstore.model.User;
import com.online.bookstore.repositories.interfaces.UserRepoInterface;
import com.online.bookstore.services.UserServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserServiceInterface {

    private UserConvertor userConvertor;
    private UserRepoInterface userRepoInterface;

    @Autowired
    public UserServiceImpl(UserConvertor userConvertor, UserRepoInterface userRepoInterface) {
        this.userConvertor = userConvertor;
        this.userRepoInterface = userRepoInterface;
    }

    @Override
    public UserResponseDto addUser(UserRequestDto userRequestDto) {
        User user = userConvertor.convertToUser(userRequestDto);
        userRepoInterface.save(user);
        return userConvertor.convertToUserDto(user);
    }

    @Override
    public UserResponseDto deleteUser(String id){
        User user = null;
        try {
            user = userRepoInterface.getUserById(id);
            if(user == null) {
                throw new UserNotFoundException("user not found for given postId");
            }
            userRepoInterface.deleteUser(user);
        } catch (UserNotFoundException e) {
            e.printStackTrace();
        }
        return userConvertor.convertToUserDto(user);
    }
}
