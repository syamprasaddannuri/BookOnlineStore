package com.online.bookstore.repositories;

import com.online.bookstore.model.Pricing;
import com.online.bookstore.repositories.interfaces.PricingRepoInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;



@Repository
public class PricingRepoImpl implements PricingRepoInterface {

    private MongoTemplate mongoTemplate;

    @Autowired
    public PricingRepoImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }


    @Override
    public void updatePriceForGivenBook(Pricing pricing) {
        mongoTemplate.save(pricing);
    }

    @Override
    public Pricing findByIsbn(String isbn) {
        Query query = new Query();
        query.addCriteria(Criteria.where("ISBN").is(isbn));
        return mongoTemplate.findOne(query, Pricing.class);
    }
}
