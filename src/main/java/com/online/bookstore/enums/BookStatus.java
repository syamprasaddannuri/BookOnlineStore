package com.online.bookstore.enums;

import lombok.Getter;

@Getter
public enum BookStatus {
    Available ("available"),
    NotAvailable ("Not available");

    final String type;

    BookStatus(String type) {
        this.type = type;
    }
}
