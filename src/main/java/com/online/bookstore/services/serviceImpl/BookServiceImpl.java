package com.online.bookstore.services.serviceImpl;

import com.online.bookstore.convertor.BookConvertor;
import com.online.bookstore.dto.PaginatedBooks;
import com.online.bookstore.dto.request.BookRequestDto;
import com.online.bookstore.dto.request.BookStatusRequestDto;
import com.online.bookstore.dto.response.BookResponseDto;
import com.online.bookstore.exception.BookNotFoundException;
import com.online.bookstore.model.Book;
import com.online.bookstore.model.Pagination;
import com.online.bookstore.model.User;
import com.online.bookstore.repositories.interfaces.BookRepoInterface;
import com.online.bookstore.repositories.interfaces.UserRepoInterface;
import com.online.bookstore.services.BookServiceInterface;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class BookServiceImpl implements BookServiceInterface {

    private BookRepoInterface bookRepoInterface;
    private BookConvertor bookConvertor;
    private UserRepoInterface userRepoInterface;

    static final Logger logger = LogManager.getLogger(BookServiceImpl.class.getName());

    @Autowired
    public BookServiceImpl(BookRepoInterface bookRepoInterface, BookConvertor bookConvertor, UserRepoInterface userRepoInterface) {
        this.bookRepoInterface = bookRepoInterface;
        this.bookConvertor = bookConvertor;
        this.userRepoInterface = userRepoInterface;
    }

    @Override
    public BookResponseDto addBook(BookRequestDto bookRequestDto) {
        Book book = bookConvertor.convertToBook(bookRequestDto);
        bookRepoInterface.save(book);
        return bookConvertor.convertToBookResponseDto(book);
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
    public PaginatedBooks searchBooks(String searchKey, int pageNo, int pageSize) throws BookNotFoundException {
        List<BookResponseDto> finalResult = new ArrayList<>();
        List<Book> listByTitleAndISBN = bookRepoInterface.searchByTitleAndISBN(searchKey, new Pagination(pageNo,pageSize));
        List<User> userList = userRepoInterface.searchByAuthor(searchKey);
        List<Book> listByAuthor = new ArrayList<>();
        userList.forEach(
                user -> {
                    listByAuthor.add(bookRepoInterface.findByAuthorId(user.getId(),new Pagination(pageNo,pageSize)));
                }
                );
        Set<Book> bookSet = new HashSet<>(listByTitleAndISBN);
        bookSet.addAll(listByAuthor);
        if(bookSet.size() == 0) {
            logger.error("Book Not Found For The Given Search Key");
            throw new BookNotFoundException("Book Not Found For The Given Search Key");
        }
        List<Book> result = new ArrayList<>(bookSet);
        result.forEach(
                book -> {
                    finalResult.add(bookConvertor.convertToBookResponseDto(book));
                }
            );
        return new PaginatedBooks(finalResult, (long) finalResult.size(),new Pagination(pageNo,pageSize));
    }

    @Override
    public String getStatusOfBook(String isbn) throws BookNotFoundException {
        Book book = bookRepoInterface.findByISBN(isbn);
        if(book == null) {
            logger.error("Book not found for given ISBN");
            throw new BookNotFoundException("Book not found for given ISBN");
        }
        return book.getBookStatus().toString();
    }

    @Override
    public Book updateBookStatus(BookStatusRequestDto bookStatusRequestDto) throws BookNotFoundException {
        Book book = bookRepoInterface.findByISBN(bookStatusRequestDto.getIsbn());
        if(book == null) {
            logger.error("Book not found for given ISBN");
            throw new BookNotFoundException("Book not found for given ISBN");
        }
        book.setBookStatus(bookStatusRequestDto.getBookStatus());
        return book;
    }
}
