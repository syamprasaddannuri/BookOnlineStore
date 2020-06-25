package com.online.bookstore.services;

import com.online.bookstore.convertor.UserConvertor;
import com.online.bookstore.dto.request.UserRequestDto;
import com.online.bookstore.dto.response.UserResponseDto;
import com.online.bookstore.model.User;
import com.online.bookstore.repositories.UserRepo;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserServiceTest {

    @Autowired
    private UserServiceInterface userServiceInterface;

    @MockBean
    private UserConvertor userConvertor;

    @Mock
    private UserRepo userRepo;

    @Test
    public void addUser() {
        UserRequestDto userRequestDto = new UserRequestDto("1","virat",32,"9898989898","virat@gmail.com");
        User user = new User("1","virat",32,"9898989898","virat@gmail.com");
        UserResponseDto userResponseDto = new UserResponseDto("1","virat",32,"9898989898","virat@gmail.com");
        when(userRepo.save(any())).thenReturn(user);
        when(userConvertor.convertToUserDto(any())).thenReturn(userResponseDto);
        userServiceInterface.addUser(userRequestDto);
        Assert.assertEquals(user.getName(),userResponseDto.getName());
    }
}
