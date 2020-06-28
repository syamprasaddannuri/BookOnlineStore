package com.online.bookstore.repositories;

import com.online.bookstore.model.BookInventory;
import com.online.bookstore.repositories.interfaces.BookInventoryRepoInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class BookInventoryRepoImpl implements BookInventoryRepoInterface {

    private MongoTemplate mongoTemplate;

    @Autowired
    public BookInventoryRepoImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public BookInventory save(BookInventory bookInventory) {
        return mongoTemplate.save(bookInventory);
    }

    @Override
    public BookInventory findByISBN(String isbn) {
        Query query = new Query();
        query.addCriteria(Criteria.where("ISBN").is(isbn));
        return mongoTemplate.findOne(query,BookInventory.class);
    }

}
