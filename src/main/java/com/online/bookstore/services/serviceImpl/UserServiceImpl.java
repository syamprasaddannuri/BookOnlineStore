package com.online.bookstore.services.serviceImpl;

import com.online.bookstore.convertor.UserConvertor;
import com.online.bookstore.dto.request.UserRequestDto;
import com.online.bookstore.dto.response.UserResponseDto;
import com.online.bookstore.exception.UserNotFoundException;
import com.online.bookstore.model.User;
import com.online.bookstore.repositories.interfaces.UserRepoInterface;
import com.online.bookstore.services.UserServiceInterface;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserServiceInterface {

    private UserConvertor userConvertor;
    private UserRepoInterface userRepoInterface;

    static final Logger logger = LogManager.getLogger(UserServiceImpl.class.getName());

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
    public void deleteUser(String id) throws UserNotFoundException{
        User user = userRepoInterface.getUserById(id);
        if(user == null) {
            logger.error("user not found for given postId");
            throw new UserNotFoundException("user not found for given postId");
        }
        userRepoInterface.deleteUser(user);
    }
}
