package com.online.bookstore.repositories;

import com.online.bookstore.model.Book;
import com.online.bookstore.model.Pagination;
import com.online.bookstore.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BookRepo {

    private MongoTemplate mongoTemplate;

    @Autowired
    public BookRepo(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public Book save(Book book) {
        return mongoTemplate.save(book);
    }

    public Book findByISBN(String isbn) {
        Query query = new Query();
        query.addCriteria(Criteria.where("ISBN").is(isbn));
        return mongoTemplate.findOne(query,Book.class);
    }

    public void deleteBook(Book book) {
        mongoTemplate.remove(book);
    }

    public Book findByAuthorId(String id, Pagination pagination) {
        Query query = new Query();
        query.addCriteria(Criteria.where("authorId").is(id));
        if(pagination.getPageNo() >= 0) {
            query.with(PageRequest.of(pagination.getPageNo(),pagination.getPageSize()));
        }
        return mongoTemplate.findOne(query,Book.class);
    }

    public List<Book> searchByTitleAndISBN(String searchKey, Pagination pagination) {
        Query query = new Query();
        Criteria c1 = new Criteria().where("ISBN").regex(searchKey);
        Criteria c2 = new Criteria().where("title").regex(searchKey);
        query.addCriteria(new Criteria().orOperator(c1,c2));
        if(pagination.getPageNo() >= 0) {
            query.with(PageRequest.of(pagination.getPageNo(), pagination.getPageSize()));
        }
        return mongoTemplate.find(query,Book.class);
    }
}
