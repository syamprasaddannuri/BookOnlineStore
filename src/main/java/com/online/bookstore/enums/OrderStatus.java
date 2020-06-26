package com.online.bookstore.enums;

import lombok.Getter;

@Getter
public enum OrderStatus {
    Created ("created"),
    Completed("completed");

    final String type;

    OrderStatus(String type) {
        this.type = type;
    }
}
