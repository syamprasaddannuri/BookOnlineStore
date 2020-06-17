package com.online.bookstore.repositories;

import com.online.bookstore.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRepo {

    private MongoTemplate mongoTemplate;

    @Autowired
    public UserRepo(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public User save(User user) { return mongoTemplate.save(user); }

    public User getByUserName(String name) {
        Query query = new Query();
        query.addCriteria(Criteria.where("name").is(name));
        return mongoTemplate.findOne(query,User.class);
    }

    public User getUserById(String authorId) {
        return mongoTemplate.findById(authorId,User.class);
    }

    public void deleteUser(User user) { mongoTemplate.remove(user); }

    public List<User> searchByAuthor(String searchKey) {
        Query query = new Query();
        query.addCriteria(Criteria.where("name").regex(searchKey));
        return mongoTemplate.find(query,User.class);
    }
}
