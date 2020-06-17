package com.online.bookstore.repositories;

import com.online.bookstore.model.BookInventory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class BookInventoryRepo {

    private MongoTemplate mongoTemplate;

    @Autowired
    public BookInventoryRepo(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public BookInventory save(BookInventory bookInventory) {
        return mongoTemplate.save(bookInventory);
    }

    public BookInventory findByISBN(String isbn) {
        Query query = new Query();
        query.addCriteria(Criteria.where("ISBN").is(isbn));
        return mongoTemplate.findOne(query,BookInventory.class);
    }

    public void deleteBookInventory(BookInventory bookInventory) {
        mongoTemplate.remove(bookInventory);
    }

}
