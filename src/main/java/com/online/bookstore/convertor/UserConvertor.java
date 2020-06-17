package com.online.bookstore.convertor;

import com.online.bookstore.dto.request.UserRequestDto;
import com.online.bookstore.dto.response.UserResponseDto;
import com.online.bookstore.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserConvertor {

    public User convertToUser(UserRequestDto userRequestDto) {
        User user = new User(
                userRequestDto.getId(),
                userRequestDto.getName(),
                userRequestDto.getAge(),
                userRequestDto.getMobileNumber(),
                userRequestDto.getEmailId()
        );
        return user;
    }

    public UserResponseDto convertToUserDto(User user) {
        UserResponseDto userResponseDto = null;
        if(user != null) {
            userResponseDto = new UserResponseDto(
                    user.getId(),
                    user.getName(),
                    user.getAge(),
                    user.getMobileNumber(),
                    user.getEmailId()
            );
        }
        return userResponseDto;
    }
}
