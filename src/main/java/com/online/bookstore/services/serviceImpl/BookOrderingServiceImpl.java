package com.online.bookstore.services.serviceImpl;

import com.online.bookstore.dto.request.OrderRequest;
import com.online.bookstore.dto.response.OrderResponse;
import com.online.bookstore.enums.OrderStatus;
import com.online.bookstore.exception.BookNotAvailableException;
import com.online.bookstore.exception.BookNotFoundException;
import com.online.bookstore.exception.InventoryNotAvailableException;
import com.online.bookstore.exception.InventoryNotFoundException;
import com.online.bookstore.model.Book;
import com.online.bookstore.model.BookInventory;
import com.online.bookstore.model.Order;
import com.online.bookstore.repositories.interfaces.BookRepoInterface;
import com.online.bookstore.repositories.interfaces.OrderRepoInterface;
import com.online.bookstore.services.BookInventoryServiceInterface;
import com.online.bookstore.services.BookOrderingServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookOrderingServiceImpl implements BookOrderingServiceInterface {

    private BookInventoryServiceInterface bookInventoryServiceInterface;
    private BookRepoInterface bookRepoInterface;
    private OrderRepoInterface orderRepoInterface;

    @Autowired
    public BookOrderingServiceImpl( BookInventoryServiceInterface bookInventoryServiceInterface, BookRepoInterface bookRepoInterface, OrderRepoInterface orderRepoInterface) {
        this.bookInventoryServiceInterface = bookInventoryServiceInterface;
        this.bookRepoInterface = bookRepoInterface;
        this.orderRepoInterface = orderRepoInterface;
    }

    @Override
    public OrderResponse buyBook(String isbn) throws BookNotAvailableException, InventoryNotFoundException, BookNotFoundException, InventoryNotAvailableException {
        BookInventory bookInventory = bookInventoryServiceInterface.getInventory(isbn);
        if(bookInventory == null) {
            throw new InventoryNotFoundException("Book Inventory not found for the given book");
        }
        if(bookInventory.getBookStatus().getType().equals("Not Available")) {
            throw new BookNotAvailableException("Book is not available for given isbn");
        }

        Book book = bookRepoInterface.findByISBN(bookInventory.getISBN());
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
