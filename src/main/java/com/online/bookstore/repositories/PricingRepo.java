package com.online.bookstore.repositories;

import com.online.bookstore.model.Pricing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;



@Repository
public class PricingRepo {

    private MongoTemplate mongoTemplate;

    @Autowired
    public PricingRepo(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }


    public void updatePriceForGivenBook(Pricing pricing) {
        mongoTemplate.save(pricing);
    }

    public Pricing findByIsbn(String isbn) {
        Query query = new Query();
        query.addCriteria(Criteria.where("ISBN").is(isbn));
        return mongoTemplate.findOne(query, Pricing.class);
    }
}
