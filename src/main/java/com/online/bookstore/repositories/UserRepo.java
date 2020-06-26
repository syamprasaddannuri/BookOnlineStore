package com.online.bookstore.repositories;

import com.online.bookstore.model.User;
import com.online.bookstore.repositories.interfaces.UserRepoInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRepo implements UserRepoInterface {

    private MongoTemplate mongoTemplate;

    @Autowired
    public UserRepo(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public User save(User user) { return mongoTemplate.save(user); }

    @Override
    public User getByUserName(String name) {
        Query query = new Query();
        query.addCriteria(Criteria.where("name").is(name));
        return mongoTemplate.findOne(query,User.class);
    }

    @Override
    public User getUserById(String authorId) {
        return mongoTemplate.findById(authorId,User.class);
    }

    @Override
    public void deleteUser(User user) { mongoTemplate.remove(user); }

    @Override
    public List<User> searchByAuthor(String searchKey) {
        Query query = new Query();
        query.addCriteria(Criteria.where("name").regex(searchKey));
        return mongoTemplate.find(query,User.class);
    }
}
