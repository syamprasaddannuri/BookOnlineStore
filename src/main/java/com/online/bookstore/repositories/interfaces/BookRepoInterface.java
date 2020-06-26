package com.online.bookstore.repositories.interfaces;

import com.online.bookstore.model.Book;
import com.online.bookstore.model.Pagination;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepoInterface {

    Book save (Book book);

    Book findByISBN(String isbn);

    void deleteBook(Book book);

    Book findByAuthorId(String id, Pagination pagination);

    List<Book> searchByTitleAndISBN(String searchKey, Pagination pagination);

}
