package com.online.bookstore.exception;

public class InsufficientInventory extends Throwable {
    public InsufficientInventory(String s) {
        super(s);
    }
}
