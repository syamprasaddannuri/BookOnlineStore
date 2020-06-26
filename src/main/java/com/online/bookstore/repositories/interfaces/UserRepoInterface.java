package com.online.bookstore.repositories.interfaces;

import com.online.bookstore.model.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepoInterface {

    User save(User user);

    User getByUserName(String name);

    User getUserById(String authorId);

    void deleteUser(User user);

    List<User> searchByAuthor(String searchKey);
}
