package com.online.bookstore.services;

import com.online.bookstore.convertor.UserConvertor;
import com.online.bookstore.dto.request.UserRequestDto;
import com.online.bookstore.dto.response.UserResponseDto;
import com.online.bookstore.exception.UserNotFoundException;
import com.online.bookstore.model.User;
import com.online.bookstore.repositories.interfaces.UserRepoInterface;
import com.online.bookstore.services.serviceImpl.UserServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserServiceTest {

    private UserServiceInterface userServiceInterface;

    @MockBean
    private UserConvertor userConvertor;

    @Mock
    private UserRepoInterface userRepoInterface;

    private UserRequestDto userRequestDto;
    private User user;
    private UserResponseDto userResponseDto;


    @Before
    public void start() {
        userServiceInterface = new UserServiceImpl(userConvertor,userRepoInterface);
        userRequestDto = new UserRequestDto("1","satheesh",32,"9908422660","satheesh@gmail.com");
        user = new User("1","satheesh",32,"9908422660","satheesh@gmail.com");
        userResponseDto = new UserResponseDto("1","satheesh",32,"9908422660","satheesh@gmail.com");
    }

    @Test
    public void addUser() {
        when(userConvertor.convertToUserDto(any())).thenReturn(userResponseDto);
        userServiceInterface.addUser(userRequestDto);
    }

    @Test(expected = UserNotFoundException.class)
    public void deleteUserIfNull() throws UserNotFoundException {
        given(userRepoInterface.getUserById(any())).willAnswer(invocationOnMock -> { return null; });
        userServiceInterface.deleteUser("1");
    }
}
