package com.online.bookstore.exceptionHandlers;

import com.mongodb.DuplicateKeyException;
import com.online.bookstore.exception.BookNotFoundException;
import com.online.bookstore.exception.InsufficientInventory;
import com.online.bookstore.exception.InventoryNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({InventoryNotFoundException.class, BookNotFoundException.class, DuplicateKeyException.class,
            InsufficientInventory.class
    })
    public ResponseEntity<Object> handleBadRequest(
             Exception e, WebRequest request) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

}
