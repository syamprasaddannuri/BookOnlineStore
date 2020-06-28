package com.online.bookstore.exception;

public class InvalidRequestException extends Throwable {
    public InvalidRequestException(String inventory_update_request_invalid) {
        super(inventory_update_request_invalid);
    }
}
