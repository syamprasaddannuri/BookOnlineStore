package com.online.bookstore.services.serviceImpl;

import com.mongodb.DuplicateKeyException;
import com.online.bookstore.dto.PaginatedBooks;
import com.online.bookstore.dto.request.BookUpdateRequest;
import com.online.bookstore.enums.BookStatus;
import com.online.bookstore.exception.BookNotFoundException;
import com.online.bookstore.model.Book;
import com.online.bookstore.model.Pagination;
import com.online.bookstore.repositories.interfaces.BookRepoInterface;
import com.online.bookstore.services.BookServiceInterface;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookServiceInterface {

    private BookRepoInterface bookRepoInterface;

    static final Logger logger = LogManager.getLogger(BookServiceImpl.class.getName());

    @Autowired
    public BookServiceImpl(BookRepoInterface bookRepoInterface) {
        this.bookRepoInterface = bookRepoInterface;
    }

    @Override
    public Book addBook(Book book) throws DuplicateKeyException {
        if (book.getBookStatus() == null) {
            book.setBookStatus(BookStatus.Available);
        }
        bookRepoInterface.save(book);
        return book;
    }

    @Override
    public void deleteBook(String isbn) throws BookNotFoundException {
        Book book = bookRepoInterface.findByISBN(isbn);
        if(book == null) {
            logger.error("Book not found for given ISBN");
            throw new BookNotFoundException("Book not found for given ISBN");
        }
        bookRepoInterface.deleteBook(book);
    }

    @Override
    public PaginatedBooks searchBooks(String searchKey, int pageNo, int pageSize) {
        List<Book> resultBookSet = bookRepoInterface.searchInISBNAuthorTitle(searchKey, new Pagination(pageNo,pageSize));
        return new PaginatedBooks(resultBookSet, (long) resultBookSet.size(),new Pagination(pageNo,pageSize));
    }

    @Override
    public Book getBook(String isbn) throws BookNotFoundException {
        Book book = bookRepoInterface.findByISBN(isbn);
        if(book == null) {
            logger.error("Book not found for given ISBN");
            throw new BookNotFoundException("Book not found for given ISBN");
        }
        return book;
    }

    @Override
    public Book updateBook(BookUpdateRequest bookUpdateRequest) throws BookNotFoundException {
        Book book = getBook(bookUpdateRequest.getIsbn());
        book.setTitle(bookUpdateRequest.getTitle());
        book.setDescription(bookUpdateRequest.getDescription());
        book.setPrice(bookUpdateRequest.getPrice());
        book.setAuthor(bookUpdateRequest.getAuthor());
        book.setBookStatus(bookUpdateRequest.getBookStatus());
        bookRepoInterface.save(book);
        return book;
    }
}
