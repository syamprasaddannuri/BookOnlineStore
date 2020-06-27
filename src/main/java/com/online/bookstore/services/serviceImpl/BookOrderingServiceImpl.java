package com.online.bookstore.services.serviceImpl;

import com.online.bookstore.dto.request.OrderRequest;
import com.online.bookstore.dto.response.BookInventoryResponse;
import com.online.bookstore.dto.response.OrderResponse;
import com.online.bookstore.enums.OrderStatus;
import com.online.bookstore.exception.BookNotAvailableException;
import com.online.bookstore.exception.InventoryNotFoundException;
import com.online.bookstore.model.Book;
import com.online.bookstore.model.Order;
import com.online.bookstore.repositories.interfaces.BookRepoInterface;
import com.online.bookstore.repositories.interfaces.OrderRepoInterface;
import com.online.bookstore.services.BookInventoryServiceInterface;
import com.online.bookstore.services.BookOrderingServiceInterface;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookOrderingServiceImpl implements BookOrderingServiceInterface {

    private BookInventoryServiceInterface bookInventoryServiceInterface;
    private BookRepoInterface bookRepoInterface;
    private OrderRepoInterface orderRepoInterface;

    static final Logger logger = LogManager.getLogger(BookOrderingServiceImpl.class.getName());

    @Autowired
    public BookOrderingServiceImpl( BookInventoryServiceInterface bookInventoryServiceInterface, BookRepoInterface bookRepoInterface, OrderRepoInterface orderRepoInterface) {
        this.bookInventoryServiceInterface = bookInventoryServiceInterface;
        this.bookRepoInterface = bookRepoInterface;
        this.orderRepoInterface = orderRepoInterface;
    }

    @Override
    public OrderResponse buyBook(String isbn) throws InventoryNotFoundException,BookNotAvailableException {
        BookInventoryResponse bookInventoryResponse = bookInventoryServiceInterface.getInventory(isbn);
        if(bookInventoryResponse == null) {
            logger.error("Book Inventory not found for the given isbn");
            throw new InventoryNotFoundException("Book Inventory not found for the given isbn");
        }
        if(bookInventoryResponse.getCount() == 0) {
            logger.error("Book is Not Available");
            throw new BookNotAvailableException("Book is Not Available");
        }
        Book book = bookRepoInterface.findByISBN(bookInventoryResponse.getISBN());
        OrderResponse orderResponse = new OrderResponse();
        orderResponse.setISBN(book.getISBN());
        orderResponse.setPrice(book.getPrice());
        orderResponse.setOrderStatus(OrderStatus.Created);
        return orderResponse;
    }

    @Override
    public Order storeOrderResponse(OrderRequest orderRequest) {
        Order order = new Order();
        order.setISBN(orderRequest.getISBN());
        order.setOrderStatus(OrderStatus.Completed);
        order.setPrice(orderRequest.getPrice());
        orderRepoInterface.save(order);
        return order;
    }
}
