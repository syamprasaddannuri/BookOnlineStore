package com.online.bookstore.repositories;

import com.online.bookstore.model.Order;
import com.online.bookstore.repositories.interfaces.OrderRepoInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class OrderRepoImpl implements OrderRepoInterface {

    private MongoTemplate mongoTemplate;

    @Autowired
    public OrderRepoImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public Order save(Order order) {
        return mongoTemplate.save(order);
    }
}
