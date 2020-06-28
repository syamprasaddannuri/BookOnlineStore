package com.online.bookstore.services.serviceImpl;

import com.online.bookstore.enums.BookStatus;
import com.online.bookstore.enums.OrderStatus;
import com.online.bookstore.exception.BookNotAvailableException;
import com.online.bookstore.exception.BookNotFoundException;
import com.online.bookstore.exception.InsufficientInventory;
import com.online.bookstore.exception.InventoryNotFoundException;
import com.online.bookstore.model.Book;
import com.online.bookstore.model.BookInventory;
import com.online.bookstore.model.Order;
import com.online.bookstore.repositories.interfaces.OrderRepoInterface;
import com.online.bookstore.services.BookInventoryServiceInterface;
import com.online.bookstore.services.BookOrderingServiceInterface;
import com.online.bookstore.services.BookServiceInterface;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookOrderingServiceImpl implements BookOrderingServiceInterface {

    private BookInventoryServiceInterface bookInventoryServiceInterface;
    private OrderRepoInterface orderRepoInterface;
    private BookServiceInterface bookServiceInterface;

    static final Logger logger = LogManager.getLogger(BookOrderingServiceImpl.class.getName());

    @Autowired
    public BookOrderingServiceImpl( BookInventoryServiceInterface bookInventoryServiceInterface, OrderRepoInterface orderRepoInterface, BookServiceInterface bookServiceInterface) {
        this.bookInventoryServiceInterface = bookInventoryServiceInterface;
        this.orderRepoInterface = orderRepoInterface;
        this.bookServiceInterface = bookServiceInterface;
    }

    @Override
    public Order buyBook(String isbn, int quantity) throws InventoryNotFoundException, BookNotFoundException, BookNotAvailableException, InsufficientInventory {
        Book book = bookServiceInterface.getBook(isbn);
        if(book.getBookStatus().equals(BookStatus.NotAvailable)) {
            logger.info("Book Inventory not found for the given isbn");
            throw new BookNotAvailableException("Book is Not Available");
        }
        BookInventory bookInventory = bookInventoryServiceInterface.getInventory(book.getISBN());
        if(bookInventory == null) {
            logger.info("Book Inventory not found for the given isbn");
            throw new InventoryNotFoundException("Book Inventory not found for the given isbn");
        }
        if (bookInventory.getCount() < quantity) {
            logger.info("Order can't be fulfilled Insufficient inventory");
            throw new InsufficientInventory("Order can't be fulfilled Insufficient inventory");
        }
        bookInventory.setCount(bookInventory.getCount() - quantity);
        bookInventoryServiceInterface.updateInventory(bookInventory);
        Order order = new Order();
        order.setISBN(book.getISBN());
        order.setPrice(book.getPrice());
        order.setOrderStatus(OrderStatus.Completed);
        orderRepoInterface.save(order);
        return order;
    }
}
