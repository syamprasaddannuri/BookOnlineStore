package com.online.bookstore.repositories;

import com.online.bookstore.model.Book;
import com.online.bookstore.model.Pagination;
import com.online.bookstore.repositories.interfaces.BookRepoInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BookRepoImpl implements BookRepoInterface {

    private MongoTemplate mongoTemplate;

    @Autowired
    public BookRepoImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public Book save(Book book) {
        return mongoTemplate.save(book);
    }

    @Override
    public Book findByISBN(String isbn) {
        Query query = new Query();
        query.addCriteria(Criteria.where("ISBN").is(isbn));
        return mongoTemplate.findOne(query,Book.class);
    }

    @Override
    public void deleteBook(Book book) {
        mongoTemplate.remove(book);
    }

    @Override
    public List<Book> searchInISBNAuthorTitle(String searchKey, Pagination pagination) {
        Query query = new Query();
        Criteria c1 = new Criteria().where("ISBN").regex(searchKey);
        Criteria c2 = new Criteria().where("title").regex(searchKey);
        Criteria c3 = new Criteria().where("author").regex(searchKey);
        query.addCriteria(new Criteria().orOperator(c1,c2, c3));
        if(pagination.getPageNo() >= 0) {
            query.with(PageRequest.of(pagination.getPageNo(), pagination.getPageSize()));
        }
        return mongoTemplate.find(query,Book.class);
    }
}
