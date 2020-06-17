package com.online.bookstore.services.serviceImpl;

import com.online.bookstore.convertor.BookConvertor;
import com.online.bookstore.dto.request.BookRequestDto;
import com.online.bookstore.dto.response.BookResponseDto;
import com.online.bookstore.exception.BookNotFoundException;
import com.online.bookstore.model.Book;
import com.online.bookstore.model.User;
import com.online.bookstore.repositories.BookRepo;
import com.online.bookstore.repositories.UserRepo;
import com.online.bookstore.services.BookServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class BookServiceImpl implements BookServiceInterface {

    private BookRepo bookRepo;
    private BookConvertor bookConvertor;
    private UserRepo userRepo;

    @Autowired
    public BookServiceImpl(BookRepo bookRepo, BookConvertor bookConvertor, UserRepo userRepo) {
        this.bookRepo = bookRepo;
        this.bookConvertor = bookConvertor;
        this.userRepo = userRepo;
    }

    @Override
    public BookResponseDto addBook(BookRequestDto bookRequestDto) {
        Book book = bookConvertor.convertToBook(bookRequestDto);
        bookRepo.save(book);
        return bookConvertor.convertToBookResponseDto(book);
    }

    @Override
    public BookResponseDto deleteBook(String isbn) {
        Book book = null;
        try {
            book = bookRepo.findByISBN(isbn);
            if(book == null) {
                throw new BookNotFoundException("Book not found for given ISBN");
            }
            book.setStatus(false);
            bookRepo.save(book);
        } catch (BookNotFoundException e) {
            e.printStackTrace();
        }
        return bookConvertor.convertToBookResponseDto(book);
    }

    @Override
    public List<BookResponseDto> searchBooks(String searchKey) {
        List<BookResponseDto> finalResult = new ArrayList<>();
        try {
            List<Book> listByTitleAndISBN = bookRepo.searchByTitleAndISBN(searchKey);
            List<User> userList = userRepo.searchByAuthor(searchKey);
            List<Book> listByAuthor = new ArrayList<>();
            userList.forEach(
                    user -> {
                        listByAuthor.add(bookRepo.findByAuthorId(user.getId()));
                    }
            );
            Set<Book> bookSet = new HashSet<>(listByTitleAndISBN);
            bookSet.addAll(listByAuthor);
            if(bookSet.size() == 0) {
                throw new BookNotFoundException("Book Not Found For The Given Search Key");
            }
            List<Book> result = new ArrayList<>(bookSet);
            result.forEach(
                    book -> {
                        finalResult.add(bookConvertor.convertToBookResponseDto(book));
                    }
            );
        } catch (BookNotFoundException e) {
            e.printStackTrace();
        }
        return finalResult;
    }
}
